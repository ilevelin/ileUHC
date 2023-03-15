package ilevelin.ileuhc.utils;

import ilevelin.ileuhc.utils.enums.ChatColor;
import ilevelin.ileuhc.utils.textFormatting.FormattedTextBlock;
import ilevelin.ileuhc.utils.textFormatting.TextFormatterBlock;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class Messenger {

    private static final String
            CONSOLE_PREFIX = "[ileUHC] ",
            CHAT_PREFIX = new FormattedTextBlock().setText(CONSOLE_PREFIX).setColor(ChatColor.ORANGE).setFormatterBlock(new TextFormatterBlock().setBold(true)).toString();

    public static void ConsoleLog (String message) {
        System.out.println(CONSOLE_PREFIX + message);
    }

    public static void ChatLog (String message, boolean opsOnly) {
        String finalMessage = CONSOLE_PREFIX + message;
        if (opsOnly) {
            Bukkit.getServer().getOnlinePlayers().forEach((player) -> player.sendRawMessage(finalMessage));
        } else {
            Bukkit.getServer().broadcastMessage(finalMessage);
        }
    }

    public static void MessageBroadcast (boolean opsOnly, FormattedTextBlock... messages) {
        StringBuilder auxMessage = new StringBuilder(CHAT_PREFIX);
        for (FormattedTextBlock message : messages) auxMessage.append(message);
        final String finalMessage = auxMessage.toString();
        if (opsOnly) {
            Bukkit.getServer().getOnlinePlayers().forEach((player) -> player.sendRawMessage(finalMessage));
        } else {
            Bukkit.getServer().broadcastMessage(finalMessage);
        }
    }

    public static void MessagePlayer (CommandSender player, FormattedTextBlock... messages) {
        StringBuilder auxMessage = new StringBuilder(CHAT_PREFIX);
        for (FormattedTextBlock message : messages) auxMessage.append(message);
        final String finalMessage = auxMessage.toString();
        player.sendMessage(finalMessage);
    }

}
