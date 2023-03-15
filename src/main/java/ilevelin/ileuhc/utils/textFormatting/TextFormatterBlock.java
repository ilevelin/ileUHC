package ilevelin.ileuhc.utils.textFormatting;

import ilevelin.ileuhc.utils.enums.ChatFormatter;

import java.util.ArrayList;
import java.util.List;

import static ilevelin.ileuhc.utils.textFormatting.TextFormattingUtils.*;

public class TextFormatterBlock {

    private boolean
            cursed = false,
            bold = false,
            strikethrough = false,
            underline = false,
            italic = false;

    public TextFormatterBlock() {
        cursed = false;
        bold = false;
        strikethrough = false;
        underline = false;
        italic = false;
    }

    /* Getters */
    public boolean getCursed() { return cursed; }
    public boolean getBold() { return bold; }
    public boolean getStrikethrough() { return strikethrough; }
    public boolean getUnderline() { return underline; }
    public boolean getItalic() { return  italic; }

    /* Setters */
    public TextFormatterBlock setCursed(boolean cursed) { this.cursed = cursed; return this; }
    public TextFormatterBlock setBold(boolean bold) { this.bold = bold; return this; }
    public TextFormatterBlock setStrikethrough(boolean strikethrough) { this.strikethrough = strikethrough; return this; }
    public TextFormatterBlock setUnderline(boolean underline) { this.underline = underline; return this; }
    public TextFormatterBlock setItalic(boolean italic) { this.italic = italic; return this; }

    public String toString() {
        String formattingString = "";

        if (cursed) formattingString = formattingString + FormatterToString(ChatFormatter.CURSED);
        if (bold) formattingString = formattingString + FormatterToString(ChatFormatter.BOLD);
        if (strikethrough) formattingString = formattingString + FormatterToString(ChatFormatter.STRIKETHROUGH);
        if (underline) formattingString = formattingString + FormatterToString(ChatFormatter.UNDERLINE);
        if (italic) formattingString = formattingString + FormatterToString(ChatFormatter.ITALIC);

        return formattingString;
    }

    public List<ChatFormatter> getAsList() {
        List<ChatFormatter> formattersList = new ArrayList<>();

        if (cursed) formattersList.add(ChatFormatter.CURSED);
        if (bold) formattersList.add(ChatFormatter.BOLD);
        if (strikethrough) formattersList.add(ChatFormatter.STRIKETHROUGH);
        if (underline) formattersList.add(ChatFormatter.UNDERLINE);
        if (italic) formattersList.add(ChatFormatter.ITALIC);

        return formattersList;
    }

    public void setFormatter(ChatFormatter formatter, boolean enabled) {
        switch (formatter) {
            case CURSED:
                cursed = enabled;
                return;
            case BOLD:
                bold = enabled;
                return;
            case ITALIC:
                italic = enabled;
                return;
            case UNDERLINE:
                underline = enabled;
                return;
            case STRIKETHROUGH:
                strikethrough = enabled;
                return;
        }
    }

    public boolean getFormatter(ChatFormatter formatter) {
        switch (formatter) {
            case CURSED:
                return cursed;
            case BOLD:
                return bold;
            case ITALIC:
                return italic;
            case UNDERLINE:
                return underline;
            case STRIKETHROUGH:
                return strikethrough;
            default:
                return false;
        }
    }

}
