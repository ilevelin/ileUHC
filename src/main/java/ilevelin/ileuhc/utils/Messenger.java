package ilevelin.ileuhc.utils;

import ilevelin.ileuhc.config.PlayerConfigController;
import ilevelin.ileuhc.lang.Translator;
import ilevelin.ileuhc.utils.enums.ChatColor;
import ilevelin.ileuhc.utils.enums.LangCode;
import ilevelin.ileuhc.utils.textFormatting.FormattedTextBlock;
import ilevelin.ileuhc.utils.textFormatting.TextFormatterBlock;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

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
            Bukkit.getServer().getOnlinePlayers().forEach((player) -> {if (player.isOp()) player.sendRawMessage(finalMessage);});
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

    public static void MessageBroadcastTranslated (boolean opsOnly, String messageID, String... replacements) {
        Bukkit.getServer().getOnlinePlayers().forEach((onlinePlayer) -> {
            if (!opsOnly || onlinePlayer.isOp()) {
                MessagePlayerTranslated(onlinePlayer, messageID, replacements);
            }
        });
    }

    public static void MessagePlayerTranslated (CommandSender player, String messageID, String... replacements) {
        LangCode lang = PlayerConfigController.getInstance().getPlayerConfig(player.getName()).getLang();
        StringBuilder auxMessage = new StringBuilder(CHAT_PREFIX);
        auxMessage.append(Translator.getInstance().getTranslation(messageID, lang, Arrays.stream(replacements).toArray(String[]::new)));
        final String finalMessage = auxMessage.toString();
        player.sendMessage(finalMessage);
    }

    public static void SendTitleTranslated (CommandSender player, String mainMessageID, String[] mainReplacements, String secondaryMessageID, String[] secondaryReplacements, int fadeIn, int stay, int fadeOut) {
        LangCode lang = PlayerConfigController.getInstance().getPlayerConfig(player.getName()).getLang();
        String mainMessage = Translator.getInstance().getTranslation(mainMessageID, lang, mainReplacements);
        String secondaryMessage = Translator.getInstance().getTranslation(secondaryMessageID, lang, secondaryReplacements);
        ((Player) player).sendTitle(mainMessage, secondaryMessage, fadeIn, stay, fadeOut);
    }

}
