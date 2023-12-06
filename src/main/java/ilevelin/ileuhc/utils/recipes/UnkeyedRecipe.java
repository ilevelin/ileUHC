package ilevelin.ileuhc.utils.recipes;

public abstract class UnkeyedRecipe {
    protected String name = "";

    public String getName() { return name; }

    public UnkeyedRecipe setName(String name){
        this.name = name;
        return this;
    }
}
