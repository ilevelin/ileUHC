package ilevelin.ileuhc.controllers;

import ilevelin.ileuhc.utils.Messenger;
import ilevelin.ileuhc.utils.NumberFormatter;
import ilevelin.ileuhc.utils.enums.ChatColor;
import ilevelin.ileuhc.utils.enums.GameStartCode;
import ilevelin.ileuhc.utils.enums.TeamFormat;
import ilevelin.ileuhc.utils.textFormatting.FormattedTextBlock;
import ilevelin.ileuhc.utils.textFormatting.TextFormatterBlock;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameController {

    private static GameController instance;
    public static GameController getInstance() {
        if (instance == null) instance = new GameController();
        return instance;
    }
    private GameController() {
        teamColors.add(org.bukkit.ChatColor.DARK_BLUE);
        teamColors.add(org.bukkit.ChatColor.DARK_GREEN);
        teamColors.add(org.bukkit.ChatColor.DARK_AQUA);
        teamColors.add(org.bukkit.ChatColor.DARK_RED);
        teamColors.add(org.bukkit.ChatColor.DARK_PURPLE);
        teamColors.add(org.bukkit.ChatColor.GOLD);
        teamColors.add(org.bukkit.ChatColor.BLUE);
        teamColors.add(org.bukkit.ChatColor.GREEN);
        teamColors.add(org.bukkit.ChatColor.AQUA);
        teamColors.add(org.bukkit.ChatColor.RED);
        teamColors.add(org.bukkit.ChatColor.LIGHT_PURPLE);
        teamColors.add(org.bukkit.ChatColor.YELLOW);
    }

    private JavaPlugin plugin;
    private boolean gameRunning = false;
    private List<String> alivePlayers = new ArrayList<>();
    private List<String> deadPlayers = new ArrayList<>();
    private long timeRemaining = 0, treatyTimeRemaining = 0, gameTime = 0;
    private boolean timeLimitEnded = false, treatyTimeEnded = false;
    private BukkitScheduler updater;

    private List<org.bukkit.ChatColor> teamColors;
    
    GameSetupController gameSetupController = GameSetupController.getInstance();

    public GameController includePlugin(JavaPlugin plugin) {
        this.plugin = plugin;
        updater = Bukkit.getScheduler();
        return this;
    }

    public boolean isGameRunning() { return gameRunning; }

    public GameStartCode prepareGame() {
        /* Checking some safeguards before starting */
        // At least 2 players
        if (gameSetupController.getParticipatingPlayers().size() < 2)
            return GameStartCode.NOT_ENOUGH_PLAYERS;

        // All participants must be connected
        for (String participant : gameSetupController.getParticipatingPlayers()) {
            if(Bukkit.getServer().getPlayer(participant) == null)
                return GameStartCode.PLAYERS_NOT_CONNECTED;
        }

        if (
                gameSetupController.getTeamFormat() != TeamFormat.SOLO
                && gameSetupController.getParticipatingPlayers().size() % gameSetupController.getTeamSize() != 0
        ) {
            return GameStartCode.UNEVEN_TEAM_SIZES;
        }

        try {
            World auxWorld = Bukkit.getServer().getWorlds().get(0);
            for (World world : Bukkit.getServer().getWorlds()) {
                if (world.getEnvironment() == World.Environment.NORMAL)
                    auxWorld = world;
            }
            final World overworld = auxWorld;

            /* Team setup */
            switch (gameSetupController.getTeamFormat()) {
                case SOLO:
                case FOUND_SQUAD:
                    // Nothing to do
                    break;
                case RANDOM_SQUAD:
                    TeamsController.getInstance().generateRandomTeams(
                            gameSetupController.getTeamSize(),
                            gameSetupController.getParticipatingPlayers()
                    );
                    Collections.shuffle(teamColors);
                    for (int i = 0; i < TeamsController.getInstance().getTeamList().size(); i++) {
                        org.bukkit.scoreboard.Team newTeam = Bukkit.getScoreboardManager().getMainScoreboard().registerNewTeam(TeamsController.getInstance().getTeamList().get(i).teamName);
                        newTeam.setColor(teamColors.get(i));
                        newTeam.setAllowFriendlyFire(true);
                        TeamsController.getInstance().getTeamList().get(i).players.forEach(player -> newTeam.addEntry(player));
                    }
                    break;
                case PREMADE_SQUAD:
                case DRAFTED_SQUAD:
                    // Not implemented yet
                    break;
            }

            /* Preparing world */
            overworld.getWorldBorder().setSize(gameSetupController.getMapSize() + 1);
            Bukkit.getServer().getWorlds().forEach((world) -> world.setPVP(false));

            /* Sending all players to Spectator */
            // Participants will be forced into survival later
            for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                player.setGameMode(GameMode.SPECTATOR);
            }

            /* Setting up spawn positions */
            List<Location> spawnPositions = new ArrayList<>();
            int spawnsPerSide = (int) (Math.floorDiv(gameSetupController.getMapSize(), 300L)) + 1;
            long coordinateIncrement = gameSetupController.getMapSize() / spawnsPerSide;
            long borderCoordinate = gameSetupController.getMapSize() / 2L;
            // X+
            for (int i = 0; i < spawnsPerSide; i++)
                spawnPositions.add(new Location(overworld,
                        borderCoordinate,
                        256,
                        (coordinateIncrement * i) - borderCoordinate
                        ));
            // X-
            for (int i = 0; i < spawnsPerSide; i++)
                spawnPositions.add(new Location(overworld,
                        -borderCoordinate,
                        256,
                        (coordinateIncrement * i) - borderCoordinate
                ));
            // Z+
            for (int i = 1; i + 1 < spawnsPerSide; i++)
                spawnPositions.add(new Location(overworld,
                        (coordinateIncrement * i) - borderCoordinate,
                        256,
                        borderCoordinate
                ));
            // Z-
            for (int i = 1; i + 1 < spawnsPerSide; i++)
                spawnPositions.add(new Location(overworld,
                        (coordinateIncrement * i) - borderCoordinate,
                        256,
                        -borderCoordinate
                ));
            Collections.shuffle(spawnPositions);

            /* Teleporting players */
            switch (gameSetupController.getTeamFormat()) {
                case SOLO:
                case FOUND_SQUAD:
                    for (String participantName : gameSetupController.getParticipatingPlayers()) {
                        Player participant = Bukkit.getPlayer(participantName);
                        boolean validSpawn = false;
                        while (!validSpawn) {
                            participant.teleport(spawnPositions.remove(0));
                            if (!overworld.getBlockAt(
                                    participant.getLocation().getBlockX(),
                                    overworld.getHighestBlockYAt(participant.getLocation()),
                                    participant.getLocation().getBlockZ())
                                            .getType().equals(Material.WATER)
                            ) {
                                participant.getInventory().addItem(new ItemStack(Material.OAK_BOAT));
                                participant.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 60 * 20, 10));
                                participant.setGameMode(GameMode.SURVIVAL);
                                validSpawn = true;
                            } else if (spawnPositions.size() == 0)
                                return GameStartCode.NOT_ENOUGH_VALID_SPAWNS;
                        }
                    }
                    break;
                case RANDOM_SQUAD:
                    for (Team team : TeamsController.getInstance().getTeamList()) {
                        List<Player> teamPlayers = new ArrayList<>();
                        team.players.forEach(playerName -> teamPlayers.add(Bukkit.getPlayer(playerName)));
                        boolean validSpawn = false;
                        while (!validSpawn) {
                            Location spawnLocation = spawnPositions.remove(0);
                            teamPlayers.get(0).teleport(spawnLocation);
                            if (!overworld.getBlockAt(
                                            teamPlayers.get(0).getLocation().getBlockX(),
                                            overworld.getHighestBlockYAt(teamPlayers.get(0).getLocation()),
                                            teamPlayers.get(0).getLocation().getBlockZ())
                                    .getType().equals(Material.WATER)
                            ) {
                                for (Player participant : teamPlayers) {
                                    participant.teleport(spawnLocation);
                                    participant.getInventory().addItem(new ItemStack(Material.OAK_BOAT));
                                    participant.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 60 * 20, 10));
                                    participant.setGameMode(GameMode.SURVIVAL);
                                }
                                validSpawn = true;
                            } else if (spawnPositions.size() == 0)
                                return GameStartCode.NOT_ENOUGH_VALID_SPAWNS;
                        }
                    }
                    break;
                case PREMADE_SQUAD:
                case DRAFTED_SQUAD:
                    // Not implemented yet
                    break;
            }

            /* Countdown */
            updater.scheduleSyncDelayedTask(plugin, () ->
                    Bukkit.getOnlinePlayers().forEach(player -> {
                        player.sendTitle(new FormattedTextBlock("10").setColor(ChatColor.RED).toStringWithoutResetEnd(),"");
                        player.playSound(player, Sound.BLOCK_NOTE_BLOCK_BANJO, 0.5f, 0.75f);
                    }),
                    0);
            updater.scheduleSyncDelayedTask(plugin, () ->
                    Bukkit.getOnlinePlayers().forEach(player -> {
                        player.sendTitle(new FormattedTextBlock("9").setColor(ChatColor.RED).toStringWithoutResetEnd(),"");
                        player.playSound(player, Sound.BLOCK_NOTE_BLOCK_BANJO, 0.5f, 0.75f);
                    }),
                    20);
            updater.scheduleSyncDelayedTask(plugin, () ->
                    Bukkit.getOnlinePlayers().forEach(player -> {
                        player.sendTitle(new FormattedTextBlock("8").setColor(ChatColor.RED).toStringWithoutResetEnd(),"");
                        player.playSound(player, Sound.BLOCK_NOTE_BLOCK_BANJO, 0.5f, 0.75f);
                    }),
                    40);
            updater.scheduleSyncDelayedTask(plugin, () ->
                    Bukkit.getOnlinePlayers().forEach(player -> {
                        player.sendTitle(new FormattedTextBlock("7").setColor(ChatColor.RED).toStringWithoutResetEnd(),"");
                        player.playSound(player, Sound.BLOCK_NOTE_BLOCK_BANJO, 0.5f, 0.75f);
                    }),
                    60);
            updater.scheduleSyncDelayedTask(plugin, () ->
                    Bukkit.getOnlinePlayers().forEach(player -> {
                        player.sendTitle(new FormattedTextBlock("6").setColor(ChatColor.YELLOW).toStringWithoutResetEnd(),"");
                        player.playSound(player, Sound.BLOCK_NOTE_BLOCK_BANJO, 0.5f, 0.75f);
                    }),
                    80);
            updater.scheduleSyncDelayedTask(plugin, () ->
                    Bukkit.getOnlinePlayers().forEach(player -> {
                        player.sendTitle(new FormattedTextBlock("5").setColor(ChatColor.YELLOW).toStringWithoutResetEnd(),"");
                        player.playSound(player, Sound.BLOCK_NOTE_BLOCK_BANJO, 0.5f, 0.75f);
                    }),
                    100);
            updater.scheduleSyncDelayedTask(plugin, () ->
                    Bukkit.getOnlinePlayers().forEach(player -> {
                        player.sendTitle(new FormattedTextBlock("4").setColor(ChatColor.YELLOW).toStringWithoutResetEnd(),"");
                        player.playSound(player, Sound.BLOCK_NOTE_BLOCK_BANJO, 0.5f, 0.75f);
                    }),
                    120);
            updater.scheduleSyncDelayedTask(plugin, () ->
                    Bukkit.getOnlinePlayers().forEach(player -> {
                        player.sendTitle(new FormattedTextBlock("3").setColor(ChatColor.GREEN).toStringWithoutResetEnd(),"");
                        player.playSound(player, Sound.BLOCK_NOTE_BLOCK_BANJO, 0.5f, 0.75f);
                    }),
                    140);
            updater.scheduleSyncDelayedTask(plugin, () ->
                    Bukkit.getOnlinePlayers().forEach(player -> {
                        player.sendTitle(new FormattedTextBlock("2").setColor(ChatColor.GREEN).toStringWithoutResetEnd(),"");
                        player.playSound(player, Sound.BLOCK_NOTE_BLOCK_BANJO, 0.5f, 0.75f);
                    }),
                    160);
            updater.scheduleSyncDelayedTask(plugin, () ->
                    Bukkit.getOnlinePlayers().forEach(player -> {
                        player.sendTitle(new FormattedTextBlock("1").setColor(ChatColor.GREEN).toStringWithoutResetEnd(),"");
                        player.playSound(player, Sound.BLOCK_NOTE_BLOCK_BANJO, 0.5f, 0.75f);
                    }),
                    180);
            updater.scheduleSyncDelayedTask(plugin, () -> {
                    startGame();
                    Bukkit.getOnlinePlayers().forEach(player -> {
                        player.sendTitle(new FormattedTextBlock("GO!").setColor(ChatColor.BLUE).toStringWithoutResetEnd(),"");
                        player.playSound(player, Sound.BLOCK_NOTE_BLOCK_BANJO, 0.5f, 1.25f);
                    });},
                    200);

        } catch (Exception e) {
            System.out.println(e);
            return GameStartCode.UNKERR;
        }

        return GameStartCode.OK;
    }

    private void startGame() {
        World auxWorld = Bukkit.getServer().getWorlds().get(0);
        for (World world : Bukkit.getServer().getWorlds()) {
            world.setGameRule(GameRule.NATURAL_REGENERATION, false);
            if (world.getEnvironment() == World.Environment.NORMAL)
                auxWorld = world;
        }
        final World overworld = auxWorld;

        this.treatyTimeRemaining = gameSetupController.getTreatyTime();
        this.timeRemaining = gameSetupController.getTimeLimit();
        this.gameTime = 0;

        if (treatyTimeRemaining == 0) treatyTimeEnded = true;

        for (World world : Bukkit.getServer().getWorlds()) {
            world.setPVP(treatyTimeEnded);
            world.setGameRule(GameRule.NATURAL_REGENERATION, false);
        }

        deadPlayers.clear();
        alivePlayers.clear();
        alivePlayers.addAll(gameSetupController.getParticipatingPlayers());

        gameRunning = true;
        updater.scheduleSyncRepeatingTask(plugin, this::updateScoreboard, 0, 5);
        updater.scheduleSyncRepeatingTask(plugin, () -> {
                if (!treatyTimeEnded) treatyTimeRemaining--;
                if (!timeLimitEnded) timeRemaining--;
                gameTime++;

                if (treatyTimeRemaining == 0 && !treatyTimeEnded) {
                    for (Player player : Bukkit.getServer().getOnlinePlayers()) player.playSound(player, Sound.ENTITY_ENDERMAN_SCREAM, 0.25f, 1f);
                    Messenger.MessageBroadcastTranslated(false, "Game.Info.TreatyEnded");
                    treatyTimeEnded = true;
                    Bukkit.getServer().getWorlds().forEach((world) -> world.setPVP(true));
                }

                if (timeRemaining == 600) {
                    for (Player player : Bukkit.getServer().getOnlinePlayers())
                        player.playSound(player, Sound.BLOCK_ANVIL_USE, 0.5f, 1f);
                    Messenger.MessageBroadcastTranslated(false, "Game.Info.10MinutesRemain");
                }

                if (timeRemaining == 0 && !timeLimitEnded) {

                    for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                        if (player.getWorld().getEnvironment() != World.Environment.NORMAL) {
                            player.teleport(new Location(overworld,
                                    player.getLocation().getX() * 8,
                                    overworld.getHighestBlockYAt(player.getLocation().getBlockX(), player.getLocation().getBlockZ()),
                                    player.getLocation().getZ() * 8
                            ));
                            player.damage(4);
                        }
                        player.playSound(player, Sound.BLOCK_ANVIL_LAND, 0.5f, 1f);
                    }

                    overworld.getWorldBorder().setSize(gameSetupController.getDeathmatchMapSize(), 600L);

                    Messenger.MessageBroadcastTranslated(false, "Game.Info.GameEnded");
                    timeLimitEnded = true;
                }
            }
        , 0L, 20);

        StatsController.getInstance().initialize(alivePlayers);

        Messenger.MessageBroadcastTranslated(false, "Game.Info.GameStarts"); // debug, beautify later
    }

    public void killPlayer(Player player) { killPlayer(player.getDisplayName()); }
    public void killPlayer(String player) {

        Messenger.MessageBroadcastTranslated(false,"Game.Info.PlayerEliminated", player);

        for (Player onlinePlayer : Bukkit.getServer().getOnlinePlayers()) {
            onlinePlayer.playSound(onlinePlayer, Sound.ITEM_TRIDENT_THUNDER, 1f, 1.5f);
        }

        switch (gameSetupController.getTeamFormat()) {
            case SOLO:
                for (String alivePlayer : alivePlayers)
                    if (alivePlayer.equals(player)) {
                        StatsController.getInstance().setPlace(alivePlayer, alivePlayers.size(), alivePlayers.size()+deadPlayers.size());
                        alivePlayers.remove(alivePlayer);
                        deadPlayers.add(alivePlayer);
                        break;
                    }

                if (alivePlayers.size() == 1)
                    endGame(alivePlayers.get(0));
                break;
            case RANDOM_SQUAD:
                for (String alivePlayer : alivePlayers)
                    if (alivePlayer.equals(player)) {
                        alivePlayers.remove(alivePlayer);
                        deadPlayers.add(alivePlayer);
                        break;
                    }

                Team eliminatedTeam = TeamsController.getInstance().killPlayer(player);
                if (eliminatedTeam != null) {
                    Messenger.MessageBroadcastTranslated(false,"Game.Info.TeamEliminated", eliminatedTeam.teamName);
                    for (String eliminatedTeamPlayer : eliminatedTeam.players)
                        StatsController.getInstance().setPlace(
                                eliminatedTeamPlayer,
                                TeamsController.getInstance().getAliveTeamList().size() + 1,
                                TeamsController.getInstance().getTeamList().size()
                        );
                }

                Team winnerTeam = TeamsController.getInstance().checkForWinner();
                if (winnerTeam != null)
                    endGame(winnerTeam.teamName, winnerTeam);
                break;
            case FOUND_SQUAD:
            case DRAFTED_SQUAD:
            case PREMADE_SQUAD:
                // Not implemented yet
                break;
        }

        updateScoreboard();
    }

    public void endGame(String winner) {
        endGame(winner, null);
    }

    public void endGame(String winner, Team winnerTeam) {
        if (!gameRunning) return;
        if (winner.equals(""))
            Messenger.MessageBroadcastTranslated(false, "Game.Info.GameAborted");
        else {
            if (gameSetupController.getTeamFormat() == TeamFormat.SOLO) {
                StatsController.getInstance().setPlace(winner, 1, alivePlayers.size() + deadPlayers.size());

                Messenger.MessageBroadcastTranslated(false, "Game.Info.WinnerPlayer", winner);

                for (Player onlinePlayer : Bukkit.getServer().getOnlinePlayers()) {
                    if (onlinePlayer.getDisplayName().equals(winner)) {
                        Messenger.SendTitleTranslated(
                                onlinePlayer,
                                "Game.Info.YouWinTitle.Main",
                                new String[]{},
                                "Game.Info.YouWinTitle.Secondary",
                                new String[]{},
                                0, 40, 400
                        );
                    } else {
                        Messenger.SendTitleTranslated(
                                onlinePlayer,
                                "Game.Info.WinnerPlayerTitle.Main",
                                new String[]{winner},
                                "Game.Info.WinnerPlayerTitle.Secondary",
                                new String[]{},
                                0, 40, 400
                        );
                    }
                    onlinePlayer.playSound(
                            onlinePlayer,
                            Sound.ENTITY_ENDER_DRAGON_DEATH,
                            0.5f,
                            0.5f
                    );
                }
            } else {
                for (String player : winnerTeam.players)
                    StatsController.getInstance().setPlace(player, 1, TeamsController.getInstance().getTeamList().size());

                Messenger.MessageBroadcastTranslated(false, "Game.Info.WinnerTeam", winner);

                for (Player onlinePlayer : Bukkit.getServer().getOnlinePlayers()) {
                    if (winnerTeam.players.contains(onlinePlayer.getDisplayName())) {
                        Messenger.SendTitleTranslated(
                                onlinePlayer,
                                "Game.Info.YouWinTitle.Main",
                                new String[]{},
                                "Game.Info.YouWinTitle.Secondary",
                                new String[]{},
                                0, 40, 400
                        );
                    } else {
                        Messenger.SendTitleTranslated(
                                onlinePlayer,
                                "Game.Info.WinnerTeamTitle.Main",
                                new String[]{winner},
                                "Game.Info.WinnerTeamTitle.Secondary",
                                new String[]{},
                                0, 40, 400
                        );
                    }
                }
            }
        }

        StatsController.getInstance().writeStatsToFile();

        this.treatyTimeRemaining = 0;
        this.timeRemaining = 0;

        updater.cancelTasks(plugin);

        gameRunning = false;
        gameSetupController.updateScoreboard();
    }

    public String getFormattedTimeRemaining() {
        long h, m, s, aux;
        h = Math.floorDiv(timeRemaining, 60l*60l);
        aux = timeRemaining % (60l*60l);
        m = Math.floorDiv(aux, 60l);
        s = aux % 60l;

        StringBuilder string = new StringBuilder();
        if (h > 0) {
            string.append(h).append(":");
            if (m < 10) string.append("0");
            string.append(m);
        } else {
            string.append(m);
        }
        string.append(":");
        if (s < 10) string.append("0");
        string.append(s);

        return string.toString();
    }

    public String getFormattedTreatyTimeRemaining() {
        long h, m, s, aux;
        h = Math.floorDiv(treatyTimeRemaining, 60l*60l);
        aux = treatyTimeRemaining % (60l*60l);
        m = Math.floorDiv(aux, 60l);
        s = aux % 60l;

        StringBuilder string = new StringBuilder();
        if (h > 0) {
            string.append(h).append(":");
            if (m < 10) string.append("0");
            string.append(m);
        } else {
            string.append(m);
        }
        string.append(":");
        if (s < 10) string.append("0");
        string.append(s);

        return string.toString();
    }

    public String getFormattedGameTime() {
        long h, m, s, aux;
        h = Math.floorDiv(gameTime, 60l*60l);
        aux = gameTime % (60l*60l);
        m = Math.floorDiv(aux, 60l);
        s = aux % 60l;

        StringBuilder string = new StringBuilder();
        if (h > 0) {
            string.append(h).append(":");
            if (m < 10) string.append("0");
            string.append(m);
        } else {
            string.append(m);
        }
        string.append(":");
        if (s < 10) string.append("0");
        string.append(s);

        return string.toString();
    }

    private void updateScoreboard() {
        List<String> scoreboardContent = new ArrayList<>();

        scoreboardContent.add(new FormattedTextBlock().setText("═╡ INFO ╞═════").setColor(ChatColor.TEAL).setFormatterBlock(new TextFormatterBlock().setBold(true)).toString());
        scoreboardContent.add(new FormattedTextBlock("Treaty Time > ").setColor(ChatColor.TEAL).toString()
                + (treatyTimeRemaining == 0 ? new FormattedTextBlock("OVER").setColor(ChatColor.DARK_RED).toString() : getFormattedTreatyTimeRemaining()));
        scoreboardContent.add(new FormattedTextBlock("Time Remaining > ").setColor(ChatColor.TEAL).toString()
                + (timeRemaining == 0 ? new FormattedTextBlock("OVER").setColor(ChatColor.DARK_RED).toString() : getFormattedTimeRemaining()));

        scoreboardContent.add(new FormattedTextBlock().setText("═╡ PLAYERS ╞═══").setColor(ChatColor.ORANGE).setFormatterBlock(new TextFormatterBlock().setBold(true)).toString());
        alivePlayers.forEach(playerName -> {
            if (alivePlayers.size() == 1)
                scoreboardContent.add(new FormattedTextBlock(playerName + " -").setColor(ChatColor.GREEN).setFormatterBlock(new TextFormatterBlock().setBold(true))
                        + new FormattedTextBlock(Bukkit.getServer().getPlayer(playerName).getHealth()+"❤").setColor(ChatColor.RED).toString());
            else
                scoreboardContent.add(playerName + " - " + new FormattedTextBlock(
                        NumberFormatter.formatter.format(Bukkit.getServer().getPlayer(playerName).getHealth())+"❤")
                        .setColor(ChatColor.RED)
                        .toString());
        });
        deadPlayers.forEach(playerName -> {
            scoreboardContent.add(new FormattedTextBlock(playerName).setColor(ChatColor.GRAY).setFormatterBlock(new TextFormatterBlock().setStrikethrough(true).setItalic(true)).toString());
        });

        ScoreboardController.getInstance().setScoreboardContent(scoreboardContent);
    }

    public long getGameTime() { return gameTime; }
    public long getTimeRemaining() { return timeRemaining; }

    public boolean isPlayerAlive(Player player) { return isPlayerAlive(player.getDisplayName()); }
    public boolean isPlayerAlive(String player) { return alivePlayers.contains(player); }
}
