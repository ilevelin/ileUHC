package ilevelin.ileuhc.controllers;

import ilevelin.ileuhc.utils.Messenger;
import ilevelin.ileuhc.utils.enums.ChatColor;
import ilevelin.ileuhc.utils.textFormatting.FormattedTextBlock;
import ilevelin.ileuhc.utils.enums.TeamFormat;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class CommandController implements CommandExecutor, TabCompleter {

   /* *********************
    * Commands
    * *********************
    * /uhc <command>
    *      setTeamFormat <TeamFormat>
    *      participants [add/remove] <PlayerName>
    *      start
    *      treatyTime <time>
    *      gameTime <time>
    *      mapSize <size>
    *      deathmatchMapSize <size>
    */

    private void sendCommandOptions(CommandSender sender, Command command, String label, String[] args) {
        sendCommandOptions(sender, command, label, args,  false);
    }

    private void sendCommandOptions(CommandSender sender, Command command, String label, String[] args,  boolean isError) {
        ChatColor messageColor = isError ? ChatColor.RED : ChatColor.TEAL;

        String argsAsString = "";
        for (int i = 0; i < args.length; i++) {
            if (!isError || i != args.length - 1) argsAsString = argsAsString + " " + args[i];
        }

        if (isError) Messenger.MessagePlayer(sender, new FormattedTextBlock("This command does not exist.").setColor(messageColor));
        Messenger.MessagePlayer(sender, new FormattedTextBlock("Available options for \"/uhc"+argsAsString+"\" are:").setColor(messageColor));
        onTabComplete(sender, command, label, args).forEach((option) -> {
            Messenger.MessagePlayer(sender, new FormattedTextBlock(" - " + option).setColor(messageColor));
        });
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (!sender.isOp()) {
            Messenger.MessagePlayer(sender, new FormattedTextBlock("Only server operators can run \"/uhc\" command.").setColor(ChatColor.RED));
            return true;
        }
        if (GameController.getInstance().isGameRunning()){
            if (args[0].equals("abort"))
                GameController.getInstance().endGame("");
            else
                Messenger.MessagePlayer(sender, new FormattedTextBlock("\"/uhc\" is disabled during gameplay. Only \"/uhc abort\" is allowed.").setColor(ChatColor.RED));
            return true;
        }
        if (args.length > 0){
            switch (args[0]) {
                case "setTeamFormat":
                    if (args.length > 1) {
                        switch (args[1]) {
                            case "solo":
                                GameSetupController.getInstance().setTeamFormat(TeamFormat.SOLO);
                                break;
                            case "premadeTeams":
                                Messenger.MessagePlayer(sender, new FormattedTextBlock("This team format is not implemented yet.").setColor(ChatColor.YELLOW));
                                //GameSetupController.getInstance().setTeamFormat(TeamFormat.PREMADE_SQUAD);
                                break;
                            case "draftedTeams":
                                Messenger.MessagePlayer(sender, new FormattedTextBlock("This team format is not implemented yet.").setColor(ChatColor.YELLOW));
                                //GameSetupController.getInstance().setTeamFormat(TeamFormat.DRAFTED_SQUAD);
                                break;
                            case "foundTeams":
                                Messenger.MessagePlayer(sender, new FormattedTextBlock("This team format is not implemented yet.").setColor(ChatColor.YELLOW));
                                //GameSetupController.getInstance().setTeamFormat(TeamFormat.FOUND_SQUAD);
                                break;
                            case "randomTeams":
                                Messenger.MessagePlayer(sender, new FormattedTextBlock("This team format is not implemented yet.").setColor(ChatColor.YELLOW));
                                //GameSetupController.getInstance().setTeamFormat(TeamFormat.RANDOM_SQUAD);
                                break;
                            default:
                                sendCommandOptions(sender, command, label, args, true);
                        }
                    } else {
                        sendCommandOptions(sender, command, label, args);
                    }
                    return true;
                case "participants":
                    if (args.length > 2) {
                        switch (args[1]) {
                            case "add":
                                if (Bukkit.getServer().getPlayer(args[2]) == null) {
                                    Messenger.MessagePlayer(sender, new FormattedTextBlock("The player \""+args[2]+"\" is not connected to the server.").setColor(ChatColor.RED));
                                } else if (GameSetupController.getInstance().addParticipant(args[2])) {
                                    Messenger.MessagePlayer(sender, new FormattedTextBlock("Player \""+args[2]+"\" added as participant.").setColor(ChatColor.GREEN));
                                } else {
                                    Messenger.MessagePlayer(sender, new FormattedTextBlock("Player \""+args[2]+"\" was already added as participant.").setColor(ChatColor.YELLOW));
                                }
                                return true;
                            case "remove":
                                if (GameSetupController.getInstance().removeParticipant(args[2])) {
                                    Messenger.MessagePlayer(sender, new FormattedTextBlock("Player \""+args[2]+"\" removed as participant.").setColor(ChatColor.GREEN));
                                } else {
                                    Messenger.MessagePlayer(sender, new FormattedTextBlock("Player \""+args[2]+"\" was not a participant.").setColor(ChatColor.YELLOW));
                                }
                                return true;

                            default:
                                sendCommandOptions(sender, command, label, args);
                        }
                    } else if (args.length == 2) {
                        switch (args[1]) {
                            case "add":
                            case "remove":
                                Messenger.MessagePlayer(sender, new FormattedTextBlock("You must specify the name of a player.").setColor(ChatColor.RED));
                                return true;
                            default:
                                sendCommandOptions(sender, command, label, args, true);
                                return true;
                        }
                    } else {
                        sendCommandOptions(sender, command, label, args);
                        return true;
                    }
                case "start":
                    switch (GameController.getInstance().prepareGame()) {
                        case NOT_ENOUGH_PLAYERS:
                            Messenger.MessagePlayer(sender, new FormattedTextBlock("Not enough players to start the game.").setColor(ChatColor.RED));
                            break;
                        case PLAYERS_NOT_CONNECTED:
                            Messenger.MessagePlayer(sender, new FormattedTextBlock("All players must be connected to the server to start the game.").setColor(ChatColor.RED));
                            break;
                        case NOT_ENOUGH_VALID_SPAWNS:
                            Messenger.MessagePlayer(sender, new FormattedTextBlock("The map doesn't have enough valid spawns for all the players/teams.").setColor(ChatColor.RED));
                            Messenger.MessagePlayer(sender, new FormattedTextBlock("Try modifying the map size or generating a new world with a new seed.").setColor(ChatColor.RED));
                            break;
                        case UNKERR:
                            Messenger.MessagePlayer(sender, new FormattedTextBlock("An unknown error has occurred while trying to start the game. Check the server console for more details.").setColor(ChatColor.RED));
                            break;
                    }
                    return true;
                case "abort":
                    Messenger.MessagePlayer(sender, new FormattedTextBlock("Game is not running").setColor(ChatColor.RED));
                    return true;
                case "treatyTime":
                    if (args.length > 1) {
                        try {
                            GameSetupController.getInstance().setTreatyTime(Integer.parseInt(args[1]));
                            Messenger.MessagePlayer(sender, new FormattedTextBlock("Set time to "+GameSetupController.getInstance().getFormattedTreatyTime()).setColor(ChatColor.GREEN));
                        } catch (Exception e) {
                            Messenger.MessagePlayer(sender, new FormattedTextBlock("\""+args[1]+"\" is not a valid number.").setColor(ChatColor.RED));
                        }
                    } else
                        Messenger.MessagePlayer(sender, new FormattedTextBlock("Please specify a number of seconds.").setColor(ChatColor.RED));
                    return true;
                case "gameTime":
                    if (args.length > 1) {
                        try {
                            GameSetupController.getInstance().setTimeLimit(Integer.parseInt(args[1]));
                            Messenger.MessagePlayer(sender, new FormattedTextBlock("Set time to "+GameSetupController.getInstance().getFormattedTimeLimit()).setColor(ChatColor.GREEN));
                        } catch (Exception e) {
                            Messenger.MessagePlayer(sender, new FormattedTextBlock("\""+args[1]+"\" is not a valid number.").setColor(ChatColor.RED));
                        }
                    } else
                        Messenger.MessagePlayer(sender, new FormattedTextBlock("Please specify a number of seconds.").setColor(ChatColor.RED));
                    return true;
                case "mapSize":
                    if (args.length > 1) {
                        try {
                            GameSetupController.getInstance().setMapSize(Integer.parseInt(args[1]));
                            Messenger.MessagePlayer(sender, new FormattedTextBlock("Set map size to "+GameSetupController.getInstance().getMapSize()).setColor(ChatColor.GREEN));
                        } catch (Exception e) {
                            Messenger.MessagePlayer(sender, new FormattedTextBlock("\""+args[1]+"\" is not a valid number.").setColor(ChatColor.RED));
                        }
                    } else
                        Messenger.MessagePlayer(sender, new FormattedTextBlock("Please specify a number of blocks.").setColor(ChatColor.RED));
                    return true;
                case "deathmatchMapSize":
                    if (args.length > 1) {
                        try {
                            GameSetupController.getInstance().setDeathmatchMapSize(Integer.parseInt(args[1]));
                            Messenger.MessagePlayer(sender, new FormattedTextBlock("Set deathmatch map size to "+GameSetupController.getInstance().getMapSize()).setColor(ChatColor.GREEN));
                        } catch (Exception e) {
                            Messenger.MessagePlayer(sender, new FormattedTextBlock("\""+args[1]+"\" is not a valid number.").setColor(ChatColor.RED));
                        }
                    } else
                        Messenger.MessagePlayer(sender, new FormattedTextBlock("Please specify a number of blocks.").setColor(ChatColor.RED));
                    return true;
                default:
                    sendCommandOptions(sender, command, label, args, true);
                    return true;
            }
        } else {
            sendCommandOptions(sender, command, label, args);
            return true;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args){
        List<String> options = new ArrayList<>();

        if (args.length > 1) {
            switch (args[0]) {
                case "setTeamFormat":
                    if (args.length == 2) {
                        options.add("solo");
                        options.add("premadeTeams");
                        options.add("draftedTeams");
                        options.add("foundTeams");
                        options.add("randomTeams");
                        break;
                    }
                case "participants":
                    if (args.length > 2) {
                        Bukkit.getServer().getOnlinePlayers().forEach((player) -> options.add(player.getDisplayName()));
                    } else {
                        options.add("add");
                        options.add("remove");
                    }
                    break;
                case "help":
                    // Nothing
                default:
                    break;
            }
        } else {
            options.add("setTeamFormat");
            options.add("participants");
            options.add("start");
            options.add("treatyTime");
            options.add("gameTime");
            options.add("mapSize");
            options.add("deathmatchMapSize");
        }

        return options;
    }
}
