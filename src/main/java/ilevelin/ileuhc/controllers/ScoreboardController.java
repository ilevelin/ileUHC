package ilevelin.ileuhc.controllers;

import ilevelin.ileuhc.utils.enums.ChatColor;
import ilevelin.ileuhc.utils.textFormatting.FormattedTextBlock;
import ilevelin.ileuhc.utils.textFormatting.TextFormatterBlock;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.*;
import java.util.ArrayList;
import java.util.List;

public class ScoreboardController {

    private static ScoreboardController instance;
    public static ScoreboardController getInstance() {
        if (instance == null) instance = new ScoreboardController();
        return instance;
    }

    private Scoreboard scoreboard;
    private Objective objective;

    private ScoreboardController() {
        scoreboard = Bukkit.getServer().getScoreboardManager().getMainScoreboard();
    }

    BukkitScheduler updater;
    JavaPlugin plugin;
    public void enable(JavaPlugin plugin) {
        this.plugin = plugin;
        updater = Bukkit.getScheduler();
        updater.scheduleSyncRepeatingTask(plugin, () -> updateScoreboard(), 0, 5);
    }

    private List<String> scoreboardContent = new ArrayList<>();

    public ScoreboardController setScoreboardContent(List<String> scoreboardContent) {
        this.scoreboardContent = scoreboardContent;
        return this;
    }

    public void updateScoreboard() {
        if (objective != null) { objective.unregister(); }
        objective = scoreboard.registerNewObjective("ileUHC_Objective", Criteria.DUMMY, new FormattedTextBlock()
                .setText("-=≡ ileUHC ≡=-")
                .setColor(ChatColor.RED)
                .setFormatterBlock(new TextFormatterBlock().setBold(true).setUnderline(true))
                .toStringWithoutResetEnd());
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        for(int i = 0; i < scoreboardContent.size(); i++){
            objective.getScore(scoreboardContent.get(i)).setScore(-(i+1));
        }
    }

    public void disable() {
        updater.cancelTasks(plugin);
        if (objective != null) objective.unregister();
    }

}
