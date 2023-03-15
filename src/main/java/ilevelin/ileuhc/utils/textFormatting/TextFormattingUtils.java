package ilevelin.ileuhc.utils.textFormatting;

import ilevelin.ileuhc.utils.enums.ChatColor;
import ilevelin.ileuhc.utils.enums.ChatFormatter;

public class TextFormattingUtils {

    public static String ColorToString(ChatColor color){
        switch (color){
            case BLACK:
                return "§0";
            case DARK_BLUE:
                return "§1";
            case DARK_GREEN:
                return "§2";
            case DARK_TEAL:
                return "§3";
            case DARK_RED:
                return "§4";
            case DARK_PINK:
                return "§5";
            case ORANGE:
                return "§6";
            case GRAY:
                return "§7";
            case DARK_GRAY:
                return "§8";
            case BLUE:
                return "§9";
            case GREEN:
                return "§a";
            case TEAL:
                return "§b";
            case RED:
                return "§c";
            case PINK:
                return "§d";
            case YELLOW:
                return "§e";
            case WHITE:
                return "§f";
            default:
                return "§r";
        }
    }
    public static String FormatterToString(ChatFormatter formatter){
        switch(formatter){
            case CURSED:
                return "§k";
            case BOLD:
                return "§l";
            case STRIKETHROUGH:
                return "§m";
            case UNDERLINE:
                return "§n";
            case ITALIC:
                return "§o";
            case CLEAR:
            default:
                return "§r";
        }
    }


}
