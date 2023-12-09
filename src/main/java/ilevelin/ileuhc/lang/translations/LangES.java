package ilevelin.ileuhc.lang.translations;

import ilevelin.ileuhc.utils.enums.ChatColor;
import ilevelin.ileuhc.utils.textFormatting.FormattedTextBlock;
import ilevelin.ileuhc.utils.textFormatting.TextFormatterBlock;

import java.util.*;

public class LangES implements Lang {

    private Map<String, String> translations;

    private static LangES instance = null;
    public static LangES getInstance(){
        if (instance == null) instance = new LangES();
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

    private LangES(){
        translations = new HashMap<>();

        /* COMMAND CONTROLLER *************************************************************************************/
        // GENERAL
        translations.put("Command.General.Error.DoesNotExist",
                new FormattedTextBlock("Este comando no existe.")
                        .setColor(ChatColor.RED).toString()
        );
        translations.put("Command.General.Error.NotAdmin",
                new FormattedTextBlock("Solo operadores del servidor pueden usar el comando \"/uhc\". Solo \"/uhc lang\" puede ser usado sin ser operador del servidor.")
                        .setColor(ChatColor.RED).toString()
        );
        translations.put("Command.General.Error.GameRunning",
                new FormattedTextBlock("\"/uhc\" está desactivado durante la partida. Solo se permite el uso de \"/uhc abort\".")
                        .setColor(ChatColor.RED).toString()
        );

        translations.put("Command.General.Info.AvailableOptions",
                new FormattedTextBlock("Las opciones disponibles para \"/uhc $0\" son:")
                        .setColor(ChatColor.TEAL).toString()
        );

        // CONFIGURATION
        translations.put("Command.Configuration.Error.TeamFormatNotImplemented",
                new FormattedTextBlock("Este formato de equipo no está disponible aún.")
                        .setColor(ChatColor.YELLOW).toString()
        );
        translations.put("Command.Configuration.Error.NotANumber",
                new FormattedTextBlock("\"$0\" no es un número válido.")
                        .setColor(ChatColor.RED).toString()
        );
        translations.put("Command.Configuration.Error.NoNumberSpecified.Seconds",
                new FormattedTextBlock("Por favor, especifica un número de segundos.")
                        .setColor(ChatColor.RED).toString()
        );
        translations.put("Command.Configuration.Error.NoNumberSpecified.Blocks",
                new FormattedTextBlock("Por favor, especifica un número de bloques.")
                        .setColor(ChatColor.RED).toString()
        );

        translations.put("Command.Configuration.Info.OptionSet",
                new FormattedTextBlock("Ajustado $0 a $1")
                        .setColor(ChatColor.GREEN).toString()
        );


        // PARTICIPANTS
        translations.put("Command.Participants.Error.NotConnected",
                new FormattedTextBlock("El jugador \"$0\" no está conectado al servidor.")
                        .setColor(ChatColor.RED).toString()
        );
        translations.put("Command.Participants.Error.AlreadyParticipating",
                new FormattedTextBlock("El juador \"$0\" ya era un participante.")
                        .setColor(ChatColor.YELLOW).toString()
        );
        translations.put("Command.Participants.Error.NotAParticipant",
                new FormattedTextBlock("El jugador \"$0\" no era un participante.")
                        .setColor(ChatColor.YELLOW).toString()
        );
        translations.put("Command.Participants.Error.NoParticipantIndicated",
                new FormattedTextBlock("Debes especificar el nombre de un jugador.")
                        .setColor(ChatColor.RED).toString()
        );

        translations.put("Command.Participants.Info.ParticipantAdded",
                new FormattedTextBlock("Añadido al jugador \"$0\" como participante.")
                        .setColor(ChatColor.GREEN).toString()
        );
        translations.put("Command.Participants.Info.ParticipantRemoved",
                new FormattedTextBlock("Eliminado al jugador \"$0\" como participante.")
                        .setColor(ChatColor.GREEN).toString()
        );

        // GAME START
        translations.put("Command.Start.Error.NotEnoughPlayers",
                new FormattedTextBlock("No hay suficientes jugadores para empezar la partida.")
                        .setColor(ChatColor.RED).toString()
        );
        translations.put("Command.Start.Error.PlayersNotConnected",
                new FormattedTextBlock("Todos los jugadores deben estar conectados al servidor para poder empezar la partida.")
                        .setColor(ChatColor.RED).toString()
        );
        translations.put("Command.Start.Error.NotEnoughSpawns",
                new FormattedTextBlock("El mapa no tiene suficientes puntos de aparición válidos para todos los jugadores/equipos. Intenta modificar el tamaño del mapa o genera un nuevo mundo con una nueva semilla.")
                        .setColor(ChatColor.RED).toString()
        );
        translations.put("Command.Start.Error.UnevenTeams",
                new FormattedTextBlock("El número de jugadores y el tamaño de equipos configurado daría como resultado equipos desiguales. Intenta cambiar el tamaño de los equipos o invitar a mas juagdores.")
                        .setColor(ChatColor.RED).toString()
        );
        translations.put("Command.Start.Error.Unknown",
                new FormattedTextBlock("Ha ocurrido un error desconocido intentando empezar la partida. Comprueba la consola del servidor para obtener mas detalles.")
                        .setColor(ChatColor.RED).toString()
        );

        // GAME ABORT
        translations.put("Command.Abort.Error.NotRunning",
                new FormattedTextBlock("La partida no está en curso.")
                        .setColor(ChatColor.RED).toString()
        );

        // LANG
        translations.put("Command.Lang.Info.LangChanged",
                new FormattedTextBlock("Idioma cambiado a CASTELLANO")
                        .setColor(ChatColor.TEAL).toString()
        );

        /* EVENTS CONTROLLER **************************************************************************************/
        translations.put("Events.Portal.Error.NetherDisabled",
                new FormattedTextBlock("Se ha deshabilitado el infierno!")
                        .setColor(ChatColor.ORANGE).toString()
        );

        /* GAME CONTROLLER ****************************************************************************************/
        translations.put("Game.Info.TreatyEnded",
                new FormattedTextBlock("El pacto de caballeros ha finalizado! Se ha activado el JcJ!")
                        .setColor(ChatColor.TEAL).toString()
        );
        translations.put("Game.Info.10MinutesRemain",
                new FormattedTextBlock("La partida finalizará en 10 minutos! Los jugadores que permanezcan en el infierno una vez acabe el tiempo serán teletransportados a su posición relativa en el mundo con una penalización de 2 corazones de daño.")
                        .setColor(ChatColor.TEAL).toString()
        );
        translations.put("Game.Info.GameEnded",
                new FormattedTextBlock("Se acabó el tiempo! El mundo se irá encogiendo durante los próximos 10 minutos hasta que todos los jugadores se encuentren en el centro del mapa. Los jugadores que estaban en el infierno han sido teletransportados a su posición relativa en el mundo con una penalización de 2 corazones de daño.")
                        .setColor(ChatColor.TEAL).toString()
        );
        translations.put("Game.Info.GameStarts",
                new FormattedTextBlock("COMIENZA LA PARTIDA")
                        .setColor(ChatColor.GREEN).toString()
        );
        translations.put("Game.Info.PlayerEliminated",
                new FormattedTextBlock().setText("$0")
                        .setColor(ChatColor.RED).setFormatterBlock(new TextFormatterBlock().setBold(true)).toString() +
                new FormattedTextBlock().setText(" ha sido eliminado!")
                        .setColor(ChatColor.DARK_RED).toString()
        );
        translations.put("Game.Info.TeamEliminated",
                new FormattedTextBlock().setText("El equipo ")
                        .setColor(ChatColor.DARK_RED).toString() +
                new FormattedTextBlock().setText("$0")
                        .setColor(ChatColor.RED).setFormatterBlock(new TextFormatterBlock().setBold(true)).toString() +
                new FormattedTextBlock().setText(" ha sido eliminado!")
                        .setColor(ChatColor.DARK_RED).toString()
        );
        translations.put("Game.Info.WinnerPlayer",
                new FormattedTextBlock("$0")
                        .setColor(ChatColor.GREEN).setFormatterBlock(new TextFormatterBlock().setBold(true)).toString() +
                new FormattedTextBlock(" es el ganador!")
                        .setColor(ChatColor.DARK_GREEN).toString()
        );
        translations.put("Game.Info.WinnerTeam",
                new FormattedTextBlock("$0")
                        .setColor(ChatColor.GREEN).setFormatterBlock(new TextFormatterBlock().setBold(true)).toString() +
                new FormattedTextBlock(" son los ganadores!")
                        .setColor(ChatColor.RED).toStringWithoutResetEnd()
        );
        translations.put("Game.Info.GameAborted",
                new FormattedTextBlock("Partida abortada por un administrador")
                        .setColor(ChatColor.RED).toString()
        );
        translations.put("Game.Info.YouWinTitle.Main",
                new FormattedTextBlock("HAS GANADO")
                        .setColor(ChatColor.GREEN).setFormatterBlock(new TextFormatterBlock().setBold(true)).toStringWithoutResetEnd()
        );
        translations.put("Game.Info.YouWinTitle.Secondary",
                new FormattedTextBlock("Felicidades!")
                        .setColor(ChatColor.GREEN).toStringWithoutResetEnd()
        );
        translations.put("Game.Info.WinnerPlayerTitle.Main",
                new FormattedTextBlock("$0")
                        .setColor(ChatColor.YELLOW).setFormatterBlock(new TextFormatterBlock().setBold(true)).toStringWithoutResetEnd()
        );
        translations.put("Game.Info.WinnerPlayerTitle.Secondary",
                new FormattedTextBlock("es el ganador!")
                        .setColor(ChatColor.RED).toStringWithoutResetEnd()
        );
        translations.put("Game.Info.WinnerTeamTitle.Secondary",
                new FormattedTextBlock("son los ganadores!")
                        .setColor(ChatColor.RED).toStringWithoutResetEnd()
        );

        /* STATS CONTROLLER ***************************************************************************************/
        translations.put("Stats.Error.CouldNotSave",
                new FormattedTextBlock("Ha courrido un error mientras se intentaba crear el archivo de estadísticas. Comprueba la consola del servidor para mas detalles.")
                        .setColor(ChatColor.RED).toString()
        );
    }
}
