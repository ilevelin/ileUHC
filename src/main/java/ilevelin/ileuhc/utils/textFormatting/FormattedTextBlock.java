package ilevelin.ileuhc.utils.textFormatting;

import ilevelin.ileuhc.utils.enums.ChatColor;

import static ilevelin.ileuhc.utils.textFormatting.TextFormattingUtils.*;

public class FormattedTextBlock {

    private String text;
    private ChatColor color;
    private TextFormatterBlock formatterBlock;

    public FormattedTextBlock() {
        text = "";
        color = ChatColor.WHITE;
        formatterBlock = new TextFormatterBlock();
    }

    public FormattedTextBlock(String text) {
        this.text = text;
        color = ChatColor.WHITE;
        formatterBlock = new TextFormatterBlock();
    }

    /* Getters */
    public String getText() { return text; }
    public ChatColor getColor() { return color; }
    public TextFormatterBlock getFormatterBlock() { return formatterBlock; }

    /* Setters */
    public FormattedTextBlock setFormatterBlock(TextFormatterBlock formatterBlock) { this.formatterBlock = formatterBlock; return this; }
    public FormattedTextBlock setColor(ChatColor color) { this.color = color; return this; }
    public FormattedTextBlock setText(String text) { this.text = text; return this; }

    public String toString() { return toStringWithoutResetEnd() + "§r "; }
    public String toStringWithoutResetEnd(){ return ColorToString(color) + formatterBlock.toString() + text; }
}
