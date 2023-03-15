package ilevelin.ileuhc.controllers;

import com.google.common.math.Stats;
import ilevelin.ileuhc.utils.Messenger;
import ilevelin.ileuhc.utils.enums.AppleType;
import ilevelin.ileuhc.utils.enums.ChatColor;
import ilevelin.ileuhc.utils.enums.DeathSource;
import ilevelin.ileuhc.utils.gameStats.PlayerStats;
import ilevelin.ileuhc.utils.textFormatting.FormattedTextBlock;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class StatsController {

    private static StatsController instance = null;
    public static StatsController getInstance() {
        if (instance == null) instance = new StatsController();
        return instance;
    }
    private StatsController() {}

    List<PlayerStats> playerStatsList = new ArrayList<>();

    public StatsController initialize(List<String> playerList) {
        playerStatsList.clear();
        for (String player : playerList)
            playerStatsList.add(new PlayerStats(player));
        return this;
    }

    public StatsController addConsumedApple(String player, AppleType apple) { getStatsFromPlayer(player).addConsumedApple(apple); return this; }
    public StatsController addLifeHealed(String player, float healed) { getStatsFromPlayer(player).addLifeHealed(healed); return this; }
    public StatsController addDamageToPlayers(String player, float damage) { getStatsFromPlayer(player).addDamageToPlayers(damage); return this; }
    public StatsController addReceivedFromPlayers(String player, float damage) { getStatsFromPlayer(player).addReceivedFromPlayers(damage); return this; }
    public StatsController addReceivedFromOther(String player, float damage) { getStatsFromPlayer(player).addReceivedFromOther(damage); return this; }
    public StatsController addKilledPlayer(String player) { getStatsFromPlayer(player).addKilledPlayer(); return this; }
    public StatsController setDeathSource(String player, DeathSource deathSource) { getStatsFromPlayer(player).setDeathSource(deathSource); return this; }
    public StatsController setPlace(String player, int place, int participants) { getStatsFromPlayer(player).setPlace(place, participants); return this; }

    public PlayerStats getStatsFromPlayer(String player) {
        for (PlayerStats playerStats : playerStatsList)
            if (playerStats.getPlayerName().equals(player))
                return playerStats;
        return null;
    }

    public void writeStatsToFile() {
        String dateAsString = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"));
        try {
            Files.createDirectories(Paths.get("./plugins/ileUHC/gameHistory/"));
            File file = new File("./plugins/ileUHC/gameHistory/"+dateAsString+".txt");
            FileWriter statsFile = new FileWriter(file);

            statsFile.write("Game Stats - Paste into \"GameHistory\"\n");
            statsFile.write("ID\t" + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE) + "\t" + GameSetupController.getInstance().getPluginVersion() + "\t" + GameSetupController.getInstance().getTeamFormat() + "\t" +
                    playerStatsList.size() + "\t" + GameController.getInstance().getFormattedGameTime() + "\t" + GameSetupController.getInstance().getFormattedTimeLimit() + "\t" + GameSetupController.getInstance().getFormattedTreatyTime() + '\n');
            statsFile.write("Player Stats - Create a new tab named \"GameX\" where X is the game ID and paste\n");
            for (PlayerStats playerStats : playerStatsList)
                statsFile.write(playerStats.toString()+'\n');
            statsFile.close();
            Messenger.ConsoleLog("Stats successfully printed.");
        } catch (Exception e) {
            Messenger.MessageBroadcast(true, new FormattedTextBlock("An error occurred while trying to create the stats file. Check the server console for more details.").setColor(ChatColor.RED));
            Messenger.ConsoleLog(e.toString());
        }
    }

}
