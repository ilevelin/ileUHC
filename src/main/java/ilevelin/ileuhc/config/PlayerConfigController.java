package ilevelin.ileuhc.config;

import java.util.HashMap;
import java.util.Map;

public class PlayerConfigController {

    private static PlayerConfigController instance = null;
    public static PlayerConfigController getInstance(){
        if (instance == null) instance = new PlayerConfigController();
        return instance;
    }

    private PlayerConfigController() { loadedConfigs = new HashMap<>(); }

    private Map<String, PlayerConfig> loadedConfigs;

    public PlayerConfig getPlayerConfig(String playerName) {
        if (!loadedConfigs.keySet().contains(playerName)) loadPlayerConfig(playerName);
        return loadedConfigs.get(playerName);
    }

    private void loadPlayerConfig(String playerName) {
        if (loadedConfigs.keySet().contains(playerName)) return;
        loadedConfigs.put(playerName, new PlayerConfig(playerName));
    }

}
