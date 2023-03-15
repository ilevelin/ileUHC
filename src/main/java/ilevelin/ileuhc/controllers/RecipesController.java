package ilevelin.ileuhc.controllers;

import ilevelin.ileuhc.data.CustomItemList;
import ilevelin.ileuhc.utils.customItem.CustomItem;
import ilevelin.ileuhc.utils.customItem.CustomItemBuilder;
import org.bukkit.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class RecipesController {

    private static RecipesController instance;
    public static RecipesController getInstance(){
        if (instance == null) instance = new RecipesController();
        return instance;
    }
    private RecipesController() {}

    private boolean isEnabled = false;

    private List<Material> itemsToRemoveRecipes = new ArrayList<>();
    private List<CustomItem> customItemsToLoad = new ArrayList<>();
    private JavaPlugin plugin;
    public RecipesController includePlugin(JavaPlugin plugin) {
        if (this.plugin != null) return this;
        this.plugin = plugin;


        /* List of items to remove its recipes */
        itemsToRemoveRecipes.add(Material.GLISTERING_MELON_SLICE);

        /* List of custom items to add */
        customItemsToLoad.add(CustomItemList.WITHER_APPLE);
        customItemsToLoad.add(CustomItemList.PLAYER_HEAD_APPLE);
        customItemsToLoad.add(CustomItemList.SUPER_GOLD_APPLE);
        customItemsToLoad.add(CustomItemList.DIAMOND_APPLE);
        customItemsToLoad.add(CustomItemList.BLUE_GOLD_APPLE);
        customItemsToLoad.add(CustomItemList.ROYAL_APPLE);
        customItemsToLoad.add(CustomItemList.ALLOYED_APPLE);
        customItemsToLoad.add(CustomItemList.MIDAS_APPLE);
        customItemsToLoad.add(CustomItemList.GOD_APPLE);
        customItemsToLoad.add(CustomItemList.COLLECTOR_APPLE);

        customItemsToLoad.add(CustomItemList.GLISTERING_MELON_SLICE);

        return this;
    }


    public boolean enable() {
        if (isEnabled) return false;

        itemsToRemoveRecipes.forEach((item) -> {
            List<Recipe> recipesForItem = Bukkit.getServer().getRecipesFor(new ItemStack(item));
            recipesForItem.forEach((recipe) -> {
                Keyed keyed;
                if (recipe instanceof Keyed) {
                    keyed = (Keyed) recipe;
                    Bukkit.getServer().removeRecipe(keyed.getKey());
                }
            });
        });

        List<Recipe> recipeList = new ArrayList<>();
        customItemsToLoad.forEach((item) -> {
            CustomItemBuilder.getInstance().createItemRecipes(item).forEach((recipe) -> {
                recipeList.add(recipe);
            });
        });
        recipeList.forEach((customRecipe) -> {
            Bukkit.getServer().addRecipe(customRecipe);
        });

        isEnabled = true;
        return true;
    }

    public boolean disable() {
        if (!isEnabled) return false;
        Bukkit.getServer().resetRecipes();
        isEnabled = false;
        return true;
    }

}
