package ilevelin.ileuhc.lang.translations;

import ilevelin.ileuhc.utils.enums.ChatColor;
import ilevelin.ileuhc.utils.textFormatting.FormattedTextBlock;
import ilevelin.ileuhc.utils.textFormatting.TextFormatterBlock;

import java.util.HashMap;
import java.util.Map;

public class LangEN implements Lang {

    private Map<String, String> translations;

    private static LangEN instance = null;
    public static LangEN getInstance(){
        if (instance == null) instance = new LangEN();
        return instance;
    }

    public String getTranslation(String code){
        return translations.getOrDefault(code, code);
    }

    private LangEN(){
        translations = new HashMap<>();

        /* COMMAND CONTROLLER *************************************************************************************/
        // GENERAL
        translations.put("Command.General.Error.DoesNotExist",
                new FormattedTextBlock("This command does not exist.")
                        .setColor(ChatColor.RED).toString()
        );
        translations.put("Command.General.Error.NotAdmin",
                new FormattedTextBlock("Only server operators can run \"/uhc\" command. Only \"/uhc lang\" can be used without being a server operator.")
                        .setColor(ChatColor.RED).toString()
        );
        translations.put("Command.General.Error.GameRunning",
                new FormattedTextBlock("\"/uhc\" is disabled during gameplay. Only \"/uhc abort\" is allowed.")
                        .setColor(ChatColor.RED).toString()
        );

        translations.put("Command.General.Info.AvailableOptions",
                new FormattedTextBlock("Available options for \"/uhc $0\" are:")
                        .setColor(ChatColor.TEAL).toString()
        );

        // CONFIGURATION
        translations.put("Command.Configuration.Error.TeamFormatNotImplemented",
                new FormattedTextBlock("This team format is not implemented yet.")
                        .setColor(ChatColor.YELLOW).toString()
        );
        translations.put("Command.Configuration.Error.NotANumber",
                new FormattedTextBlock("\"$0\" is not a valid number.")
                        .setColor(ChatColor.RED).toString()
        );
        translations.put("Command.Configuration.Error.NoNumberSpecified.Seconds",
                new FormattedTextBlock("Please specify a number of seconds.")
                        .setColor(ChatColor.RED).toString()
        );
        translations.put("Command.Configuration.Error.NoNumberSpecified.Blocks",
                new FormattedTextBlock("Please specify a number of blocks.")
                        .setColor(ChatColor.RED).toString()
        );

        translations.put("Command.Configuration.Info.OptionSet",
                new FormattedTextBlock("Set $0 to $1")
                        .setColor(ChatColor.GREEN).toString()
        );


        // PARTICIPANTS
        translations.put("Command.Participants.Error.NotConnected",
                new FormattedTextBlock("The player \"$0\" is not connected to the server.")
                        .setColor(ChatColor.RED).toString()
        );
        translations.put("Command.Participants.Error.AlreadyParticipating",
                new FormattedTextBlock("Player \"$0\" was already added as participant.")
                        .setColor(ChatColor.YELLOW).toString()
        );
        translations.put("Command.Participants.Error.NotAParticipant",
                new FormattedTextBlock("Player \"$0\" was not a participant.")
                        .setColor(ChatColor.YELLOW).toString()
        );
        translations.put("Command.Participants.Error.NoParticipantIndicated",
                new FormattedTextBlock("You must specify the name of a player.")
                        .setColor(ChatColor.RED).toString()
        );

        translations.put("Command.Participants.Info.ParticipantAdded",
                new FormattedTextBlock("Player \"$0\" added as participant.")
                        .setColor(ChatColor.GREEN).toString()
        );
        translations.put("Command.Participants.Info.ParticipantRemoved",
                new FormattedTextBlock("Player \"$0\" removed as participant.")
                        .setColor(ChatColor.GREEN).toString()
        );

        // GAME START
        translations.put("Command.Start.Error.NotEnoughPlayers",
                new FormattedTextBlock("Not enough players to start the game.")
                        .setColor(ChatColor.RED).toString()
        );
        translations.put("Command.Start.Error.PlayersNotConnected",
                new FormattedTextBlock("All players must be connected to the server to start the game.")
                        .setColor(ChatColor.RED).toString()
        );
        translations.put("Command.Start.Error.NotEnoughSpawns",
                new FormattedTextBlock("The map doesn't have enough valid spawns for all the players/teams. Try modifying the map size or generating a new world with a new seed.")
                        .setColor(ChatColor.RED).toString()
        );
        translations.put("Command.Start.Error.Unknown",
                new FormattedTextBlock("An unknown error has occurred while trying to start the game. Check the server console for more details.")
                        .setColor(ChatColor.RED).toString()
        );

        // GAME ABORT
        translations.put("Command.Abort.Error.NotRunning",
                new FormattedTextBlock("Game is not running.")
                        .setColor(ChatColor.RED).toString()
        );

        // LANG
        translations.put("Command.Lang.Info.LangChanged",
                new FormattedTextBlock("Language changed to ENGLISH")
                        .setColor(ChatColor.TEAL).toString()
        );

        /* EVENTS CONTROLLER **************************************************************************************/
        translations.put("Events.Portal.Error.NetherDisabled",
                new FormattedTextBlock("Nether has been disabled!")
                        .setColor(ChatColor.ORANGE).toString()
        );

        /* GAME CONTROLLER ****************************************************************************************/
        translations.put("Game.Info.TreatyEnded",
                new FormattedTextBlock("Treaty time has ended! PvP is now enabled!")
                        .setColor(ChatColor.TEAL).toString()
        );
        translations.put("Game.Info.10MinutesRemain",
                new FormattedTextBlock("Game will end in 10 minutes! Players that remain in the nether once the time ends will be teleported to their relative positions on the overworld with a 2 heart damage penalty.")
                        .setColor(ChatColor.TEAL).toString()
        );
        translations.put("Game.Info.GameEnded",
                new FormattedTextBlock("Game time has ended! The world will slowly shrink for the next 10 minutes until all players meet at the world center. Players that are on the nether have been teleported to their relative positions on the overworld with a 2 heart damage penalty.")
                        .setColor(ChatColor.TEAL).toString()
        );
        translations.put("Game.Info.GameStarts",
                new FormattedTextBlock("GAME STARTS")
                        .setColor(ChatColor.GREEN).toString()
        );
        translations.put("Game.Info.PlayerEliminated",
                new FormattedTextBlock().setText("$0")
                        .setColor(ChatColor.RED).setFormatterBlock(new TextFormatterBlock().setBold(true)).toString() +
                new FormattedTextBlock().setText("has been eliminated!")
                        .setColor(ChatColor.DARK_RED).toString()
        );
        translations.put("Game.Info.WinnerPlayer",
                new FormattedTextBlock("$0")
                        .setColor(ChatColor.GREEN).setFormatterBlock(new TextFormatterBlock().setBold(true)).toString()+
                new FormattedTextBlock("is the winner!")
                        .setColor(ChatColor.DARK_GREEN).toString()
        );
        translations.put("Game.Info.GameAborted",
                new FormattedTextBlock("Game aborted by an administrator")
                        .setColor(ChatColor.RED).toString()
        );
        translations.put("Game.Info.YouWinTitle.Main",
                new FormattedTextBlock("YOU WIN")
                        .setColor(ChatColor.GREEN).setFormatterBlock(new TextFormatterBlock().setBold(true)).toStringWithoutResetEnd()
        );
        translations.put("Game.Info.YouWinTitle.Secondary",
                new FormattedTextBlock("Congratulations!")
                        .setColor(ChatColor.GREEN).toStringWithoutResetEnd()
        );
        translations.put("Game.Info.WinnerPlayerTitle.Main",
                new FormattedTextBlock("$0")
                        .setColor(ChatColor.YELLOW).setFormatterBlock(new TextFormatterBlock().setBold(true)).toStringWithoutResetEnd()
        );
        translations.put("Game.Info.WinnerPlayerTitle.Secondary",
                new FormattedTextBlock("is the winner!")
                        .setColor(ChatColor.RED).toStringWithoutResetEnd()
        );

        /* STATS CONTROLLER ***************************************************************************************/
        translations.put("Stats.Error.CouldNotSave",
                new FormattedTextBlock("An error occurred while trying to create the stats file. Check the server console for more details.")
                        .setColor(ChatColor.RED).toString()
        );
    }

}
