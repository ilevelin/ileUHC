package ilevelin.ileuhc.utils.recipes;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class UnkeyedRecipeShapeless extends UnkeyedRecipe {

    private List<Material> ingredients = new ArrayList<>();
    private String name = "";

    public UnkeyedRecipeShapeless(String name) { this.name = name; }

    /* Getters */
    public List<Material> getIngredients() { return ingredients; }
    public String getName() { return name; }

    /* "Setters" */
    public UnkeyedRecipeShapeless addIngredient(Material material) {
        if (ingredients.size() < 9) ingredients.add(material);
        return this;
    }
    public UnkeyedRecipeShapeless setName(String name){
        this.name = name;
        return this;
    }

}
