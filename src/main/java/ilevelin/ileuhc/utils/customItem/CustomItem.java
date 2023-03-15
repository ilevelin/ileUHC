package ilevelin.ileuhc.utils.customItem;

import ilevelin.ileuhc.utils.enums.CustomItemTier;
import ilevelin.ileuhc.utils.recipes.UnkeyedRecipe;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class CustomItem {
    String name = "";
    CustomItemTier tier = CustomItemTier.VANILLA;
    Material material = Material.AIR;
    List<String> lore = new ArrayList<>();

    List<UnkeyedRecipe> recipeList = new ArrayList<>();

    public CustomItem(){ }
    public CustomItem(String name){ this.name = name; }

    public String getName() {
        return name;
    }
    public CustomItemTier getTier() {
        return tier;
    }
    public Material getMaterial() {
        return material;
    }
    public List<UnkeyedRecipe> getRecipeList() {
        return recipeList;
    }
    public List<String> getLore() {
        return lore;
    }

    public CustomItem setName(String name) {
        this.name = name;
        return this;
    }
    public CustomItem setMaterial(Material material) {
        this.material = material;
        return this;
    }
    public CustomItem setTier(CustomItemTier tier) {
        this.tier = tier;
        return this;
    }
    public CustomItem addRecipe(UnkeyedRecipe recipe){
        this.recipeList.add(recipe);
        return this;
    }
    public CustomItem addLore(String loreLine) {
        this.lore.add(loreLine);
        return this;
    }
}
