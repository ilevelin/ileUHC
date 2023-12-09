package ilevelin.ileuhc.controllers;

import ilevelin.ileuhc.utils.enums.ChatColor;
import ilevelin.ileuhc.utils.enums.TeamFormat;
import ilevelin.ileuhc.utils.textFormatting.FormattedTextBlock;
import ilevelin.ileuhc.utils.textFormatting.TextFormatterBlock;

import java.util.ArrayList;
import java.util.List;

public class GameSetupController {

    private static GameSetupController instance;
    public static GameSetupController getInstance() {
        if (instance == null) instance = new GameSetupController();
        return instance;
    }
    private GameSetupController() {
        this.teamFormat = TeamFormat.SOLO;
        this.setTimeLimit(3600);
        this.setTreatyTime(600);
        this.setMapSize(5000);
        this.setDeathmatchMapSize(100);
        this.updateScoreboard();
    }

    private String pluginVersion;
    public String getPluginVersion() { return pluginVersion; }
    public GameSetupController setPluginVersion(String pluginVersion) { this.pluginVersion = pluginVersion; return this; }

    private List<String> participatingPlayers = new ArrayList<>();
    private TeamFormat teamFormat;
    private long timeLimit, treatyTime, mapSize, deathmatchMapSize;
    private int teamSize;

    public GameSetupController setTeamFormat(TeamFormat teamFormat) { this.teamFormat = teamFormat; this.updateScoreboard(); return this; }
    public TeamFormat getTeamFormat() { return this.teamFormat; }

    public boolean addParticipant(String playerName) {
        boolean addPlayer = true;
        for (String player : participatingPlayers)
            if (player.equals(playerName)) addPlayer = false;
        if (addPlayer) participatingPlayers.add(playerName);

        updateScoreboard();
        return addPlayer;
    }
    public boolean removeParticipant(String playerName) {
        boolean removePlayer = false;
        for (String player : participatingPlayers)
            if (player.equals(playerName)) removePlayer = true;
        if (removePlayer) participatingPlayers.remove(playerName);

        updateScoreboard();
        return removePlayer;
    }
    public List<String> getParticipatingPlayers() {
        return participatingPlayers;
    }

    public GameSetupController setTimeLimit(int timeLimit) { return setTimeLimit((long) timeLimit); }
    public GameSetupController setTimeLimit(long timeLimit) { this.timeLimit = timeLimit; this.updateScoreboard(); return this; }
    public long getTimeLimit() { return timeLimit; }

    public GameSetupController setTreatyTime(int treatyTime) { return setTreatyTime((long) treatyTime); }
    public GameSetupController setTreatyTime(long treatyTime) { this.treatyTime = treatyTime; this.updateScoreboard(); return this; }
    public long getTreatyTime() {return treatyTime; }

    public GameSetupController setMapSize(int mapSize) { return setMapSize((long) mapSize); }
    public GameSetupController setMapSize(long mapSize) { this.mapSize = mapSize; this.updateScoreboard(); return this; }
    public long getMapSize() { return mapSize; }

    public GameSetupController setDeathmatchMapSize(int deathmatchMapSize) { return setDeathmatchMapSize((long) deathmatchMapSize); }
    public GameSetupController setDeathmatchMapSize(long deathmatchMapSize) { this.deathmatchMapSize = deathmatchMapSize; this.updateScoreboard(); return this; }
    public long getDeathmatchMapSize() { return deathmatchMapSize; }

    public GameSetupController setTeamSize(int teamSize) { this.teamSize = teamSize; this.updateScoreboard(); return this; }
    public int getTeamSize() { return teamSize; }


    public void updateScoreboard(){
        List<String> scoreboardContent = new ArrayList<>();

        scoreboardContent.add(new FormattedTextBlock().setText("═╡ CONFIG ╞════").setColor(ChatColor.TEAL).setFormatterBlock(new TextFormatterBlock().setBold(true)).toString());
        switch (teamFormat) {
            case SOLO:
                scoreboardContent.add(new FormattedTextBlock().setText("TeamMode>").setColor(ChatColor.TEAL).toString() + "Solo");
                break;
            case FOUND_SQUAD:
                scoreboardContent.add(new FormattedTextBlock().setText("TeamMode>").setColor(ChatColor.TEAL).toString() + "TeamSearch");
                break;
            case RANDOM_SQUAD:
                scoreboardContent.add(new FormattedTextBlock().setText("TeamMode>").setColor(ChatColor.TEAL).toString() + "RandomTeams");
                break;
            case DRAFTED_SQUAD:
                scoreboardContent.add(new FormattedTextBlock().setText("TeamMode>").setColor(ChatColor.TEAL).toString() + "TeamDraft");
                break;
            case PREMADE_SQUAD:
                scoreboardContent.add(new FormattedTextBlock().setText("TeamMode>").setColor(ChatColor.TEAL).toString() + "ManualTeams");
                break;
        }

        if (teamFormat != TeamFormat.SOLO)
            scoreboardContent.add(new FormattedTextBlock().setText("TeamSize>").setColor(ChatColor.TEAL).toString() + getTeamSize());
        scoreboardContent.add(new FormattedTextBlock().setText("GameTime>").setColor(ChatColor.TEAL).toString() + getFormattedTimeLimit());
        scoreboardContent.add(new FormattedTextBlock().setText("TreatyTime>").setColor(ChatColor.TEAL).toString() + getFormattedTreatyTime());
        scoreboardContent.add(new FormattedTextBlock().setText("MapSize>").setColor(ChatColor.TEAL).toString() + getMapSize());
        scoreboardContent.add(new FormattedTextBlock().setText("DeathmatchMapSize>").setColor(ChatColor.TEAL).toString() + getDeathmatchMapSize());

        scoreboardContent.add(new FormattedTextBlock().setText("═╡ PLAYERS ╞═══").setColor(ChatColor.ORANGE).setFormatterBlock(new TextFormatterBlock().setBold(true)).toString());
        for (String participatingPlayer : participatingPlayers) scoreboardContent.add(participatingPlayer);

        ScoreboardController.getInstance().setScoreboardContent(scoreboardContent);
    }

    public String getFormattedTimeLimit() {
        long h, m, s, aux;
        h = Math.floorDiv(timeLimit, 60l*60l);
        aux = timeLimit % (60l*60l);
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

    public String getFormattedTreatyTime() {
        long h, m, s, aux;
        h = Math.floorDiv(treatyTime, 60l*60l);
        aux = treatyTime % (60l*60l);
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
}
