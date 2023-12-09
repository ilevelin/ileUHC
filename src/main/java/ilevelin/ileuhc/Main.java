package ilevelin.ileuhc;

import ilevelin.ileuhc.config.PlayerConfig;
import ilevelin.ileuhc.controllers.*;
import ilevelin.ileuhc.lang.translations.LangCA;
import ilevelin.ileuhc.lang.translations.LangEN;
import ilevelin.ileuhc.lang.translations.LangES;
import ilevelin.ileuhc.utils.Messenger;
import ilevelin.ileuhc.utils.customItem.CustomItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Set;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        Messenger.ConsoleLog("====================[STARTING]====================");

        Messenger.ConsoleLog("Initializing command /uhc...");
        this.getCommand("uhc").setExecutor(new CommandController());
        this.getCommand("uhc").setTabCompleter(new CommandController());

        Messenger.ConsoleLog("Initializing scoreboard controller...");
        try {
            Bukkit.getServer().getScoreboardManager().getMainScoreboard().getObjective("ileUHC_Objective").unregister();
        } catch (Exception e) { }
        ScoreboardController.getInstance().enable(this);

        Messenger.ConsoleLog("Initializing custom item builder...");
        CustomItemBuilder.getInstance().includePlugin(this);

        Messenger.ConsoleLog("Initializing recipes controller...");
        RecipesController.getInstance().includePlugin(this);

        Messenger.ConsoleLog("Initializing game controller...");
        GameController.getInstance().includePlugin(this);

        Messenger.ConsoleLog("Enabling custom items and recipes...");
        RecipesController.getInstance().enable();

        Messenger.ConsoleLog("Registering event controller...");
        getServer().getPluginManager().registerEvents(new EventsController(), this);

        Messenger.ConsoleLog("Running first scoreboard update to have it appear on startup...");
        GameSetupController.getInstance().setPluginVersion(getDescription().getVersion());

        Messenger.ConsoleLog("Creating and configurating plugin folder...");
        this.getDataFolder().mkdirs();
        String dataPath = this.getDataFolder().getAbsolutePath();
        PlayerConfig.setConfigFolder(dataPath);
        StatsController.setConfigFolder(dataPath);

        // Compress this step into a for loop in a future
        Messenger.ConsoleLog("Loading languages and checking for missing strings...");
        Set<String> enKeySet = LangEN.getInstance().getKeys();
        List<String> esDiff = LangES.getInstance().checkKeys(enKeySet);
        if (esDiff.size() != 0) {
            Messenger.ConsoleLog("> Missing strings found in ES:");
            esDiff.forEach(key -> Messenger.ConsoleLog("\t"+key));
        }
        List<String> caDiff = LangCA.getInstance().checkKeys(enKeySet);
        if (esDiff.size() != 0) {
            Messenger.ConsoleLog("> Missing strings found in CA:");
            esDiff.forEach(key -> Messenger.ConsoleLog("\t"+key));
        }

        Messenger.ConsoleLog("");
        Messenger.ConsoleLog("Plugin initialization completed!");
        Messenger.ConsoleLog("==================================================");
    }

    @Override
    public void onDisable() {
        Messenger.ConsoleLog("====================[STOPPING]====================");

        Messenger.ConsoleLog("Disabling custom items and recipes...");
        RecipesController.getInstance().disable();

        Messenger.ConsoleLog("Disabling scoreboard controller...");
        ScoreboardController.getInstance().disable();

        Messenger.ConsoleLog("");
        Messenger.ConsoleLog("Plugin disabled!");
        Messenger.ConsoleLog("==================================================");
    }
}
