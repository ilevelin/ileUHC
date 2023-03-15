package ilevelin.ileuhc.utils.recipes;

import ilevelin.ileuhc.utils.Pair;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class UnkeyedRecipeShaped extends UnkeyedRecipe {

    private String[] shape = new String[]{"   ","   ","   "};
    private List<Pair<Character, Material>> ingredients = new ArrayList<>();
    private String name = "";

    public UnkeyedRecipeShaped(String name) { this.name = name; }

    /* Getters */
    public String[] getShape() { return shape; }
    public List<Pair<Character, Material>> getIngredients() { return ingredients; }
    public String getName() { return name; }

    /* "Setters" */
    public UnkeyedRecipeShaped setShape(String topRow, String midRow, String botRow) {
        if (topRow.length() != 3 || midRow.length() != 3 || botRow.length() != 3) return this;
        shape[0] = topRow; shape[1] = midRow; shape[2] = botRow;
        return this;
    }
    public UnkeyedRecipeShaped addIngredient(char character, Material material) {
        ingredients.add(new Pair<>(character, material));
        return this;
    }
    public UnkeyedRecipeShaped setName(String name){
        this.name = name;
        return this;
    }
}
