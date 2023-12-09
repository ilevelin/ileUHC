package ilevelin.ileuhc.lang.translations;

import ilevelin.ileuhc.utils.enums.ChatColor;
import ilevelin.ileuhc.utils.textFormatting.FormattedTextBlock;
import ilevelin.ileuhc.utils.textFormatting.TextFormatterBlock;

import java.util.*;

public class LangCA implements Lang {

    private Map<String, String> translations;

    private static LangCA instance = null;
    public static LangCA getInstance(){
        if (instance == null) instance = new LangCA();
        return instance;
    }

    public String getTranslation(String code){ return translations.getOrDefault(code, code); }
    public Set<String> getKeys() { return translations.keySet(); }
    public List<String> checkKeys(Set<String> modelKeySet ) {
        Set<String> localKeys = translations.keySet();
        List<String> missingKeys = new ArrayList<>();
        modelKeySet.forEach(modelKey -> {
            if (!localKeys.contains(modelKey)) missingKeys.add(modelKey);
        });
        return missingKeys;
    }

    private LangCA(){
        translations = new HashMap<>();

        /* COMMAND CONTROLLER *************************************************************************************/
        // GENERAL
        translations.put("Command.General.Error.DoesNotExist",
                new FormattedTextBlock("Aquesta ordre no existeix.")
                        .setColor(ChatColor.RED).toString()
        );
        translations.put("Command.General.Error.NotAdmin",
                new FormattedTextBlock("Nomes operadors del servidor poden utilitzar l'ordre \"/uhc\". Tansols \"/uhc lang\" pot ser utilitzat sense ser operador del servidor.")
                        .setColor(ChatColor.RED).toString()
        );
        translations.put("Command.General.Error.GameRunning",
                new FormattedTextBlock("\"/uhc\" està desactivat durant la partida. Nomes es pemiteix l'us de l'ordre \"/uhc abort\".")
                        .setColor(ChatColor.RED).toString()
        );

        translations.put("Command.General.Info.AvailableOptions",
                new FormattedTextBlock("Les opcións disponibles per \"/uhc $0\" son:")
                        .setColor(ChatColor.TEAL).toString()
        );

        // CONFIGURATION
        translations.put("Command.Configuration.Error.TeamFormatNotImplemented",
                new FormattedTextBlock("Aquest format d'equip encara no està disponible.")
                        .setColor(ChatColor.YELLOW).toString()
        );
        translations.put("Command.Configuration.Error.NotANumber",
                new FormattedTextBlock("\"$0\" no es un número valid.")
                        .setColor(ChatColor.RED).toString()
        );
        translations.put("Command.Configuration.Error.NoNumberSpecified.Seconds",
                new FormattedTextBlock("Per favor, especifica un número de segons.")
                        .setColor(ChatColor.RED).toString()
        );
        translations.put("Command.Configuration.Error.NoNumberSpecified.Blocks",
                new FormattedTextBlock("Per favor, especifica un número de blocs.")
                        .setColor(ChatColor.RED).toString()
        );
        translations.put("Command.Configuration.Error.NoNumberSpecified.Players",
                new FormattedTextBlock("Per favor, especifica un número de jugadors.")
                        .setColor(ChatColor.RED).toString()
        );

        translations.put("Command.Configuration.Info.OptionSet",
                new FormattedTextBlock("Ajustat $0 a $1")
                        .setColor(ChatColor.GREEN).toString()
        );


        // PARTICIPANTS
        translations.put("Command.Participants.Error.NotConnected",
                new FormattedTextBlock("El jugador \"$0\" no està conectat al servidor.")
                        .setColor(ChatColor.RED).toString()
        );
        translations.put("Command.Participants.Error.AlreadyParticipating",
                new FormattedTextBlock("El juador \"$0\" ja era un participant.")
                        .setColor(ChatColor.YELLOW).toString()
        );
        translations.put("Command.Participants.Error.NotAParticipant",
                new FormattedTextBlock("El jugador \"$0\" no era un participant.")
                        .setColor(ChatColor.YELLOW).toString()
        );
        translations.put("Command.Participants.Error.NoParticipantIndicated",
                new FormattedTextBlock("Deus especificar el nom d'un jugador.")
                        .setColor(ChatColor.RED).toString()
        );

        translations.put("Command.Participants.Info.ParticipantAdded",
                new FormattedTextBlock("Afegit al jugador \"$0\" como participant.")
                        .setColor(ChatColor.GREEN).toString()
        );
        translations.put("Command.Participants.Info.ParticipantRemoved",
                new FormattedTextBlock("Eliminat al jugador \"$0\" como participant.")
                        .setColor(ChatColor.GREEN).toString()
        );

        // GAME START
        translations.put("Command.Start.Error.NotEnoughPlayers",
                new FormattedTextBlock("No n'hi han suficients jugadors per escomençar la partida.")
                        .setColor(ChatColor.RED).toString()
        );
        translations.put("Command.Start.Error.PlayersNotConnected",
                new FormattedTextBlock("Tots els jugadors tenen que estar conectats al servidor per escomençar la partida")
                        .setColor(ChatColor.RED).toString()
        );
        translations.put("Command.Start.Error.NotEnoughSpawns",
                new FormattedTextBlock("El mapa no te suficients punts d'aparició válids per tots els jugadors/equips. Intenta modificar el tamany del mapa o generar un nou mon amb una nova llavor")
                        .setColor(ChatColor.RED).toString()
        );
        translations.put("Command.Start.Error.UnevenTeams",
                new FormattedTextBlock("El nombre de jugadors i el tamany dels equips configurat resultaria amb equips desiguals. Proba a cambiar el tamany dels equips o invitar a mes jugadors.")
                        .setColor(ChatColor.RED).toString()
        );
        translations.put("Command.Start.Error.Unknown",
                new FormattedTextBlock("Ha ocorregut un error desconegut al intentar escomençar la partida. Comprova la consola del servidor per obtindre mes detalls.")
                        .setColor(ChatColor.RED).toString()
        );

        // GAME ABORT
        translations.put("Command.Abort.Error.NotRunning",
                new FormattedTextBlock("La partida no està en curs.")
                        .setColor(ChatColor.RED).toString()
        );

        // LANG
        translations.put("Command.Lang.Info.LangChanged",
                new FormattedTextBlock("Idioma cambiat a CATALÀ")
                        .setColor(ChatColor.TEAL).toString()
        );

        /* EVENTS CONTROLLER **************************************************************************************/
        translations.put("Events.Portal.Error.NetherDisabled",
                new FormattedTextBlock("S'ha deshabilitat l'infern!")
                        .setColor(ChatColor.ORANGE).toString()
        );

        /* GAME CONTROLLER ****************************************************************************************/
        translations.put("Game.Info.TreatyEnded",
                new FormattedTextBlock("El pacte de cavallers ha finalitzat! S'ha activat el JcJ!")
                        .setColor(ChatColor.TEAL).toString()
        );
        translations.put("Game.Info.10MinutesRemain",
                new FormattedTextBlock("La partida finalitzarà en 10 minuts! Els jugadors que estiguen a l'infern al acabar el temps seràn teletransportats a la seua posició relativa en el mon amb una penalització de 2 cors de dany.")
                        .setColor(ChatColor.TEAL).toString()
        );
        translations.put("Game.Info.GameEnded",
                new FormattedTextBlock("S'ha acabat el temps! El mon s'encongirà durant el próxims 10 minuts fins que tots els jugadors es troben al centre del mapa. Tots el jugadors que estaben a l'infern han sigut teletransportats a la seua posició relativa en el mon amb una penalització de 2 cors de dany.")
                        .setColor(ChatColor.TEAL).toString()
        );
        translations.put("Game.Info.GameStarts",
                new FormattedTextBlock("ESCOMENÇA LA PARTIDA")
                        .setColor(ChatColor.GREEN).toString()
        );
        translations.put("Game.Info.PlayerEliminated",
                new FormattedTextBlock().setText("$0")
                        .setColor(ChatColor.RED).setFormatterBlock(new TextFormatterBlock().setBold(true)).toString() +
                new FormattedTextBlock().setText(" ha sigut eliminat!")
                        .setColor(ChatColor.DARK_RED).toString()
        );
        translations.put("Game.Info.TeamEliminated",
                new FormattedTextBlock().setText("L'equip ")
                        .setColor(ChatColor.DARK_RED).toString() +
                new FormattedTextBlock().setText("$0")
                        .setColor(ChatColor.RED).setFormatterBlock(new TextFormatterBlock().setBold(true)).toString() +
                new FormattedTextBlock().setText(" ha sigut eliminat!")
                        .setColor(ChatColor.DARK_RED).toString()
        );
        translations.put("Game.Info.WinnerPlayer",
                new FormattedTextBlock("$0")
                        .setColor(ChatColor.GREEN).setFormatterBlock(new TextFormatterBlock().setBold(true)).toString()+
                new FormattedTextBlock(" es el guanyador!")
                        .setColor(ChatColor.DARK_GREEN).toString()
        );
        translations.put("Game.Info.WinnerPlayer",
                new FormattedTextBlock("$0")
                        .setColor(ChatColor.GREEN).setFormatterBlock(new TextFormatterBlock().setBold(true)).toString()+
                new FormattedTextBlock(" son els guanyadors!")
                        .setColor(ChatColor.RED).toStringWithoutResetEnd()
        );
        translations.put("Game.Info.GameAborted",
                new FormattedTextBlock("Partida abortada per un administrador")
                        .setColor(ChatColor.RED).toString()
        );
        translations.put("Game.Info.YouWinTitle.Main",
                new FormattedTextBlock("HAS GUANYAT")
                        .setColor(ChatColor.GREEN).setFormatterBlock(new TextFormatterBlock().setBold(true)).toStringWithoutResetEnd()
        );
        translations.put("Game.Info.YouWinTitle.Secondary",
                new FormattedTextBlock("Felicitats!")
                        .setColor(ChatColor.GREEN).toStringWithoutResetEnd()
        );
        translations.put("Game.Info.WinnerPlayerTitle.Main",
                new FormattedTextBlock("$0")
                        .setColor(ChatColor.YELLOW).setFormatterBlock(new TextFormatterBlock().setBold(true)).toStringWithoutResetEnd()
        );
        translations.put("Game.Info.WinnerPlayerTitle.Secondary",
                new FormattedTextBlock("es el guanyador!")
                        .setColor(ChatColor.RED).toStringWithoutResetEnd()
        );
        translations.put("Game.Info.WinnerTeamTitle.Secondary",
                new FormattedTextBlock("son els guanyadors!")
                        .setColor(ChatColor.RED).toStringWithoutResetEnd()
        );

        /* STATS CONTROLLER ***************************************************************************************/
        translations.put("Stats.Error.CouldNotSave",
                new FormattedTextBlock("Ha ocorregut un error mentres s'intentava crear l'archiu d'estadístiques. Comproba la consola del servidor per mes detalls.")
                        .setColor(ChatColor.RED).toString()
        );
    }

}
