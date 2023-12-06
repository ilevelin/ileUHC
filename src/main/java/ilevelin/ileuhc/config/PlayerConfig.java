package ilevelin.ileuhc.config;

import ilevelin.ileuhc.utils.enums.LangCode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PlayerConfig {

    private static String configFolder = "";
    public static void setConfigFolder(String folder) { configFolder = folder; }

    private String playerName;
    public String getPlayerName() { return playerName; }

    public PlayerConfig(String playerName) {
        this.playerName = playerName;
        try {
            File file = new File(configFolder+"/playerConfigs/"+playerName+".txt");
            BufferedReader configReader = new BufferedReader(new FileReader(file));
            configReader.lines().forEach((configLine) -> {
                if (!configLine.contains("=")) return;
                String field = configLine.substring(0, configLine.indexOf("="));
                String value = configLine.substring(configLine.indexOf("=")+1);

                switch (field) {
                    case "lang":
                        switch (value) {
                            case "es":
                                this.lang = LangCode.ES;
                                break;
                            case "ca":
                                this.lang = LangCode.CA;
                                break;
                            case "en":
                            default:
                                this.lang = LangCode.EN;
                                break;
                        }
                        break;
                    default:
                        break;
                }
            });
        } catch (Exception e) { }
    }

    private void saveChanges() {
        try {
            Files.createDirectories(Paths.get(configFolder+"/playerConfigs/"));
            File file = new File(configFolder+"/playerConfigs/"+playerName+".txt");
            file.delete();
            FileWriter statsFile = new FileWriter(file);

            statsFile.write("lang=");
            switch (this.lang){
                case EN:
                    statsFile.write("en");
                    break;
                case ES:
                    statsFile.write("es");
                    break;
                case CA:
                    statsFile.write("ca");
                    break;
            }
            statsFile.write("\n");

            statsFile.close();
        } catch (Exception e) { }
    }

    private LangCode lang = LangCode.EN;
    public LangCode getLang() { return lang; }
    public void setLang(LangCode lang) { this.lang = lang; saveChanges(); }
}
