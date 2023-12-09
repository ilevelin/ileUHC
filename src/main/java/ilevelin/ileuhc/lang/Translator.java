package ilevelin.ileuhc.lang;

import ilevelin.ileuhc.lang.translations.*;
import ilevelin.ileuhc.utils.enums.LangCode;

public class Translator {

    private static Translator instance = null;
    public static Translator getInstance(){
        if (instance == null) instance = new Translator();
        return instance;
    }
    private Translator(){}

    public String getTranslation(String stringCode, LangCode langCode, String[] replacements) {
        Lang langFile;
        switch (langCode) {
            case CA:
                langFile = LangCA.getInstance();
                break;
            case ES:
                langFile = LangES.getInstance();
                break;
            case EN:
            default:
                langFile = LangEN.getInstance();
                break;
        }

        String translatedString = langFile.getTranslation(stringCode);

        for (int i = 0; i < replacements.length; i++)
            translatedString = translatedString.replaceAll("\\$" + i, replacements[i]);

        return translatedString;
    }

    public String getTranslation(String stringCode, LangCode langCode) {
        return getTranslation(stringCode, langCode, new String[]{});
    }

}
