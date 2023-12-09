package ilevelin.ileuhc.controllers;

import ilevelin.ileuhc.config.PlayerConfigController;
import ilevelin.ileuhc.utils.Messenger;
import ilevelin.ileuhc.utils.enums.ChatColor;
import ilevelin.ileuhc.utils.enums.LangCode;
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
    *      teamFormat <TeamFormat>
    *      participants [add/remove] <PlayerName>
    *      start
    *      treatyTime <time>
    *      gameTime <time>
    *      mapSize <size>
    *      deathmatchMapSize <size>
    *      lang <language>
    */

    private void sendCommandOptions(CommandSender sender, Command command, String label, String[] args) {
        sendCommandOptions(sender, command, label, args,  false);
    }

    private void sendCommandOptions(CommandSender sender, Command command, String label, String[] args,  boolean isError) {
        String argsAsString = "";
        for (int i = 0; i < args.length; i++) {
            if (!isError || i != args.length - 1) argsAsString = argsAsString + " " + args[i];
        }

        if (isError) Messenger.MessagePlayerTranslated(sender, "Command.General.Error.DoesNotExist");
        Messenger.MessagePlayerTranslated(sender, "Command.General.Info.AvailableOptions", argsAsString);
        onTabComplete(sender, command, label, args).forEach((option) -> {
            Messenger.MessagePlayer(sender, new FormattedTextBlock(" - " + option).setColor(ChatColor.TEAL));
        });
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (!sender.isOp() && !args[0].equals("lang")) {
            Messenger.MessagePlayerTranslated(sender, "Command.General.Error.NotAdmin");
            return true;
        }
        if (GameController.getInstance().isGameRunning()){
            if (args[0].equals("abort"))
                GameController.getInstance().endGame("");
            else
                Messenger.MessagePlayerTranslated(sender, "Command.General.Error.GameRunning");
            return true;
        }
        if (args.length > 0){
            switch (args[0]) {
                case "teamFormat":
                    if (args.length > 1) {
                        switch (args[1]) {
                            case "solo":
                                GameSetupController.getInstance().setTeamFormat(TeamFormat.SOLO);
                                Messenger.MessagePlayerTranslated(sender, "Command.Configuration.Info.OptionSet", "teamFormat", "Solo");
                                break;
                            case "premadeTeams":
                                //GameSetupController.getInstance().setTeamFormat(TeamFormat.PREMADE_SQUAD);
                                //break;
                            case "draftedTeams":
                                //GameSetupController.getInstance().setTeamFormat(TeamFormat.DRAFTED_SQUAD);
                                //break;
                            case "foundTeams":
                                //GameSetupController.getInstance().setTeamFormat(TeamFormat.FOUND_SQUAD);
                                Messenger.MessagePlayerTranslated(sender, "Command.Configuration.Error.TeamFormatNotImplemented");
                                break;
                            case "randomTeams":
                                GameSetupController.getInstance().setTeamFormat(TeamFormat.RANDOM_SQUAD);
                                Messenger.MessagePlayerTranslated(sender, "Command.Configuration.Info.OptionSet", "teamFormat", "RandomTeams");
                                break;
                            default:
                                sendCommandOptions(sender, command, label, args, true);
                        }
                    } else {
                        sendCommandOptions(sender, command, label, args);
                    }
                    return true;
                case "teamSize":
                    if (args.length > 1) {
                        try {
                            GameSetupController.getInstance().setTeamSize(Integer.parseInt(args[1]));
                            Messenger.MessagePlayerTranslated(sender, "Command.Configuration.Info.OptionSet", "teamSize", GameSetupController.getInstance().getTeamSize()+"");
                        } catch (Exception e) {
                            Messenger.MessagePlayerTranslated(sender, "Command.Configuration.Error.NotANumber", args[1]);
                        }
                    } else
                        Messenger.MessagePlayerTranslated(sender, "Command.Configuration.Error.NoNumberSpecified.Players");
                    return true;
                case "participants":
                    if (args.length > 2) {
                        switch (args[1]) {
                            case "add":
                                if (Bukkit.getServer().getPlayer(args[2]) == null) {
                                    Messenger.MessagePlayerTranslated(sender, "Command.Participants.Error.NotConnected", args[2]);
                                } else if (GameSetupController.getInstance().addParticipant(args[2])) {
                                    Messenger.MessagePlayerTranslated(sender, "Command.Participants.Info.ParticipantAdded", args[2]);
                                } else {
                                    Messenger.MessagePlayerTranslated(sender, "Command.Participants.Error.AlreadyParticipating", args[2]);
                                }
                                return true;
                            case "remove":
                                if (GameSetupController.getInstance().removeParticipant(args[2])) {
                                    Messenger.MessagePlayerTranslated(sender, "Command.Participants.Info.ParticipantRemoved", args[2]);
                                } else {
                                    Messenger.MessagePlayerTranslated(sender, "Command.Participants.Error.NotAParticipant", args[2]);
                                }
                                return true;

                            default:
                                sendCommandOptions(sender, command, label, args);
                        }
                    } else if (args.length == 2) {
                        switch (args[1]) {
                            case "add":
                            case "remove":
                                Messenger.MessagePlayerTranslated(sender, "Command.Participants.Error.NoParticipantIndicated");
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
                            Messenger.MessagePlayerTranslated(sender, "Command.Start.Error.NotEnoughPlayers");
                            break;
                        case PLAYERS_NOT_CONNECTED:
                            Messenger.MessagePlayerTranslated(sender, "Command.Start.Error.PlayersNotConnected");
                            break;
                        case NOT_ENOUGH_VALID_SPAWNS:
                            Messenger.MessagePlayerTranslated(sender, "Command.Start.Error.NotEnoughSpawns");
                            break;
                        case UNEVEN_TEAM_SIZES:
                            Messenger.MessagePlayerTranslated(sender, "Command.Start.Error.UnevenTeams");
                            break;
                        case UNKERR:
                            Messenger.MessagePlayerTranslated(sender, "Command.Start.Error.Unknown");
                            break;
                    }
                    return true;
                case "abort":
                    Messenger.MessagePlayerTranslated(sender, "Command.Abort.Error.NotRunning");
                    return true;
                case "treatyTime":
                    if (args.length > 1) {
                        try {
                            GameSetupController.getInstance().setTreatyTime(Integer.parseInt(args[1]));
                            Messenger.MessagePlayerTranslated(sender, "Command.Configuration.Info.OptionSet", "treatyTime", GameSetupController.getInstance().getFormattedTreatyTime());
                        } catch (Exception e) {
                            Messenger.MessagePlayerTranslated(sender, "Command.Configuration.Error.NotANumber", args[1]);
                        }
                    } else
                        Messenger.MessagePlayerTranslated(sender, "Command.Configuration.Error.NoNumberSpecified.Seconds");
                    return true;
                case "gameTime":
                    if (args.length > 1) {
                        try {
                            GameSetupController.getInstance().setTimeLimit(Integer.parseInt(args[1]));
                            Messenger.MessagePlayerTranslated(sender, "Command.Configuration.Info.OptionSet", "gameTime", GameSetupController.getInstance().getFormattedTimeLimit());
                        } catch (Exception e) {
                            Messenger.MessagePlayerTranslated(sender, "Command.Configuration.Error.NotANumber", args[1]);
                        }
                    } else
                        Messenger.MessagePlayerTranslated(sender, "Command.Configuration.Error.NoNumberSpecified.Seconds");
                    return true;
                case "mapSize":
                    if (args.length > 1) {
                        try {
                            GameSetupController.getInstance().setMapSize(Integer.parseInt(args[1]));
                            Messenger.MessagePlayerTranslated(sender, "Command.Configuration.Info.OptionSet", "mapSize", ""+GameSetupController.getInstance().getMapSize());
                        } catch (Exception e) {
                            Messenger.MessagePlayerTranslated(sender, "Command.Configuration.Error.NotANumber", args[1]);
                        }
                    } else
                        Messenger.MessagePlayerTranslated(sender, "Command.Configuration.Error.NoNumberSpecified.Blocks");
                    return true;
                case "deathmatchMapSize":
                    if (args.length > 1) {
                        try {
                            GameSetupController.getInstance().setDeathmatchMapSize(Integer.parseInt(args[1]));
                            Messenger.MessagePlayerTranslated(sender, "Command.Configuration.Info.OptionSet", "deathmatchMapSize", ""+GameSetupController.getInstance().getMapSize());
                        } catch (Exception e) {
                            Messenger.MessagePlayerTranslated(sender, "Command.Configuration.Error.NotANumber", args[1]);
                        }
                    } else
                        Messenger.MessagePlayerTranslated(sender, "Command.Configuration.Error.NoNumberSpecified.Blocks");
                    return true;
                case "lang":
                    if (args.length > 1) {
                        switch (args[1]) {
                            case "en":
                                PlayerConfigController.getInstance().getPlayerConfig(sender.getName()).setLang(LangCode.EN);
                                break;
                            case "es":
                                PlayerConfigController.getInstance().getPlayerConfig(sender.getName()).setLang(LangCode.ES);
                                break;
                            case "ca":
                                PlayerConfigController.getInstance().getPlayerConfig(sender.getName()).setLang(LangCode.CA);
                                break;
                            default:
                                sendCommandOptions(sender, command, label, args, true);
                                break;
                        }
                        Messenger.MessagePlayerTranslated(sender, "Command.Lang.Info.LangChanged");
                    } else {

                    }
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
                case "teamFormat":
                    if (args.length == 2) {
                        options.add("solo");
                        options.add("premadeTeams");
                        options.add("draftedTeams");
                        options.add("foundTeams");
                        options.add("randomTeams");
                    }
                    break;
                case "participants":
                    if (args.length > 2) {
                        Bukkit.getServer().getOnlinePlayers().forEach((player) -> options.add(player.getDisplayName()));
                    } else {
                        options.add("add");
                        options.add("remove");
                    }
                    break;
                case "lang":
                    if (args.length == 2) {
                        options.add("en");
                        options.add("es");
                        options.add("ca");
                    }
                    break;
                default:
                    break;
            }
        } else {
            options.add("teamFormat");
            options.add("participants");
            options.add("start");
            options.add("treatyTime");
            options.add("gameTime");
            options.add("mapSize");
            options.add("deathmatchMapSize");
            options.add("lang");
        }

        return options;
    }
}
