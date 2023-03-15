package ilevelin.ileuhc.utils.customItem;

import ilevelin.ileuhc.utils.enums.ChatColor;
import ilevelin.ileuhc.utils.recipes.UnkeyedRecipeShaped;
import ilevelin.ileuhc.utils.recipes.UnkeyedRecipeShapeless;
import ilevelin.ileuhc.utils.textFormatting.FormattedTextBlock;
import ilevelin.ileuhc.utils.textFormatting.TextFormatterBlock;
import ilevelin.ileuhc.utils.textFormatting.TextFormattingUtils;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class CustomItemBuilder {

    private static CustomItemBuilder instance;
    public static CustomItemBuilder getInstance() {
        if (instance == null) instance = new CustomItemBuilder();
        return instance;
    }
    private JavaPlugin plugin = null;

    public CustomItemBuilder includePlugin(JavaPlugin plugin) {
        if (this.plugin == null) this.plugin = plugin;
        return this;
    }

    public List<Recipe> createItemRecipes(CustomItem item) {
        if (plugin == null) return null;
        ItemStack itemStack = new ItemStack(item.getMaterial());
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName(getFormattedName(item));
        itemMeta.setLore(item.getLore());

        itemStack.setItemMeta(itemMeta);

        List<Recipe> recipeList = new ArrayList<>();
        item.recipeList.forEach((unkeyedRecipe) -> {
            if(unkeyedRecipe instanceof UnkeyedRecipeShaped) {
                UnkeyedRecipeShaped aux = (UnkeyedRecipeShaped) unkeyedRecipe;

                ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin, aux.getName()), itemStack);
                recipe.shape(aux.getShape());
                aux.getIngredients().forEach((ingredient) -> {
                    recipe.setIngredient(ingredient.first, ingredient.second);
                });

                recipeList.add(recipe);

            } else if (unkeyedRecipe instanceof UnkeyedRecipeShapeless) {
                UnkeyedRecipeShapeless aux = (UnkeyedRecipeShapeless) unkeyedRecipe;

                ShapelessRecipe recipe = new ShapelessRecipe(new NamespacedKey(plugin, aux.getName()), itemStack);
                aux.getIngredients().forEach((ingredient) -> {
                    recipe.addIngredient(ingredient);
                });
            }
        });

        return recipeList;
    }

    public String getFormattedName(CustomItem item) {

        String name = item.getName();
        if (name.length() == 0)
            name = new ItemStack(item.getMaterial()).getItemMeta().getDisplayName();

        switch (item.getTier()) {
            case TIER_1:
                return new FormattedTextBlock()
                        .setText("- " + name + " -")
                        .setColor(ChatColor.DARK_GREEN)
                        .setFormatterBlock(new TextFormatterBlock().setBold(true))
                        .toStringWithoutResetEnd();
            case TIER_2:
                return new FormattedTextBlock()
                        .setText("= " + name + " =")
                        .setColor(ChatColor.DARK_TEAL)
                        .setFormatterBlock(new TextFormatterBlock().setBold(true))
                        .toStringWithoutResetEnd();
            case TIER_3:
                return new FormattedTextBlock()
                        .setText("≡ " + name + " ≡")
                        .setColor(ChatColor.DARK_PINK)
                        .setFormatterBlock(new TextFormatterBlock().setBold(true))
                        .toStringWithoutResetEnd();
            case TIER_4:
                return new FormattedTextBlock()
                        .setText("█ " + name + " █")
                        .setColor(ChatColor.ORANGE)
                        .setFormatterBlock(new TextFormatterBlock().setBold(true))
                        .toStringWithoutResetEnd();
            case TIER_5:
                return new FormattedTextBlock()
                        .setText("⍟ " + name + " ⍟")
                        .setColor(ChatColor.DARK_RED)
                        .setFormatterBlock(new TextFormatterBlock().setBold(true))
                        .toStringWithoutResetEnd();
            case VANILLA:
            default:
                return name;
        }

    }

}
