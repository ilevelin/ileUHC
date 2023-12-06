package ilevelin.ileuhc.data;

import ilevelin.ileuhc.utils.customItem.CustomItem;
import ilevelin.ileuhc.utils.enums.CustomItemTier;
import ilevelin.ileuhc.utils.recipes.UnkeyedRecipeShaped;
import ilevelin.ileuhc.utils.recipes.UnkeyedRecipeShapeless;
import org.bukkit.Material;

public class CustomItemList {

    public static final CustomItem WITHER_APPLE = new CustomItem("WITHER APPLE")
            .setMaterial(Material.ENCHANTED_GOLDEN_APPLE)
            .setTier(CustomItemTier.TIER_1)
            .addLore("+ 4 Yellow Hearts for 3 minutes")
            .addLore("+ Regen 4 hearts in 10 seconds")
            .addRecipe(new UnkeyedRecipeShaped("WITHER_APPLE")
                    .setShape("GGG","GSG","GGG")
                    .addIngredient('G', Material.GOLD_INGOT)
                    .addIngredient('S', Material.WITHER_SKELETON_SKULL));

    public static final CustomItem PLAYER_HEAD_APPLE = new CustomItem("PLAYER HEAD APPLE")
            .setMaterial(Material.ENCHANTED_GOLDEN_APPLE)
            .setTier(CustomItemTier.TIER_1)
            .addLore("+ 4 Yellow Hearts for 3 minutes")
            .addLore("+ Regen 4 hearts in 10 seconds")
            .addRecipe(new UnkeyedRecipeShaped("PLAYER_HEAD_APPLE")
                    .setShape("GGG","GHG","GGG")
                    .addIngredient('G', Material.GOLD_INGOT)
                    .addIngredient('H', Material.PLAYER_HEAD));

    public static final CustomItem SUPER_GOLD_APPLE = new CustomItem("SUPER GOLD APPLE")
            .setMaterial(Material.ENCHANTED_GOLDEN_APPLE)
            .setTier(CustomItemTier.TIER_1)
            .addLore("+ 4 Yellow Hearts for 3 minutes")
            .addLore("+ Regen 4 hearts in 10 seconds")
            .addRecipe(new UnkeyedRecipeShaped("SUPER_GOLD_APPLE")
                    .setShape("GGG","GAG","GGG")
                    .addIngredient('G', Material.GOLD_INGOT)
                    .addIngredient('A', Material.GOLDEN_APPLE));

    public static final CustomItem TEAL_APPLE = new CustomItem("TEAL APPLE")
            .setMaterial(Material.ENCHANTED_GOLDEN_APPLE)
            .setTier(CustomItemTier.TIER_1)
            .addLore("+ 2 Heart Containers")
            .addRecipe(new UnkeyedRecipeShaped("TEAL_APPLE")
                    .setShape(" D ","DAD"," D ")
                    .addIngredient('D', Material.DIAMOND)
                    .addIngredient('A', Material.APPLE));

    public static final CustomItem DIAMOND_APPLE = new CustomItem("DIAMOND APPLE")
            .setMaterial(Material.ENCHANTED_GOLDEN_APPLE)
            .setTier(CustomItemTier.TIER_2)
            .addLore("+ 4 Heart Containers")
            .addRecipe(new UnkeyedRecipeShaped("DIAMOND_APPLE")
                    .setShape("DDD","DAD","DDD")
                    .addIngredient('D', Material.DIAMOND)
                    .addIngredient('A', Material.APPLE));

    public static final CustomItem TEAL_GOLD_APPLE = new CustomItem("TEAL-GOLD APPLE")
            .setMaterial(Material.ENCHANTED_GOLDEN_APPLE)
            .setTier(CustomItemTier.TIER_2)
            .addLore("+ 2 Heart Containers")
            .addLore("+ 2 Yellow Hearts for 2 minutes")
            .addLore("+ Regen 2 hearts in 5 seconds")
            .addRecipe(new UnkeyedRecipeShaped("TEAL_GOLD_APPLE")
                    .setShape("DDD","DGD","DDD")
                    .addIngredient('D', Material.DIAMOND)
                    .addIngredient('G', Material.GOLDEN_APPLE));

    public static final CustomItem BLUE_GOLD_APPLE = new CustomItem("BLUE GOLD APPLE")
            .setMaterial(Material.ENCHANTED_GOLDEN_APPLE)
            .setTier(CustomItemTier.TIER_3)
            .addLore("+ 4 Heart Containers")
            .addLore("+ 2 Yellow Hearts for 2 minutes")
            .addLore("+ Regen 2 hearts in 5 seconds")
            .addRecipe(new UnkeyedRecipeShaped("BLUE_GOLD_APPLE")
                    .setShape("DDD","DGD","DDD")
                    .addIngredient('D', Material.DIAMOND)
                    .addIngredient('G', Material.GOLDEN_APPLE));

    public static final CustomItem KING_APPLE = new CustomItem("KING APPLE")
            .setMaterial(Material.ENCHANTED_GOLDEN_APPLE)
            .setTier(CustomItemTier.TIER_4)
            .addLore("+ 4 Heart Containers")
            .addLore("+ 4 Yellow Hearts for 3 minutes")
            .addLore("+ Regen 4 hearts in 10 seconds")
            .addRecipe(new UnkeyedRecipeShaped("KING_APPLE")
                    .setShape("DGD","DAD","DGD")
                    .addIngredient('G', Material.GOLD_BLOCK)
                    .addIngredient('D', Material.DIAMOND)
                    .addIngredient('A', Material.GOLDEN_APPLE))
            .addRecipe(new UnkeyedRecipeShaped("KING_APPLE_2")
                    .setShape("DDD","GAG","DDD")
                    .addIngredient('G', Material.GOLD_BLOCK)
                    .addIngredient('D', Material.DIAMOND)
                    .addIngredient('A', Material.GOLDEN_APPLE));

    public static final CustomItem COLLECTOR_APPLE = new CustomItem("COLLECTOR APPLE")
            .setMaterial(Material.ENCHANTED_GOLDEN_APPLE)
            .setTier(CustomItemTier.TIER_5)
            .addLore("+ 8 Heart Containers")
            .addLore("+ 10 Yellow Hearts for 10 minutes")
            .addLore("+ Regen 10 hearts in 5 seconds")
            .addRecipe(new UnkeyedRecipeShaped("COLLECTOR_APPLE")
                    .setShape("DGD","HAS","DGD")
                    .addIngredient('D',Material.DIAMOND)
                    .addIngredient('G', Material.GOLD_BLOCK)
                    .addIngredient('H', Material.PLAYER_HEAD)
                    .addIngredient('A', Material.GOLDEN_APPLE)
                    .addIngredient('S', Material.WITHER_SKELETON_SKULL))
            .addRecipe(new UnkeyedRecipeShaped("COLLECTOR_APPLE_2")
                    .setShape("DGD","SAH","DGD")
                    .addIngredient('D',Material.DIAMOND)
                    .addIngredient('G', Material.GOLD_BLOCK)
                    .addIngredient('H', Material.PLAYER_HEAD)
                    .addIngredient('A', Material.GOLDEN_APPLE)
                    .addIngredient('S', Material.WITHER_SKELETON_SKULL));

    public static final CustomItem GLISTERING_MELON_SLICE = new CustomItem("")
            .setMaterial(Material.GLISTERING_MELON_SLICE)
            .setTier(CustomItemTier.VANILLA)
            .addRecipe(new UnkeyedRecipeShapeless("UHC_MELON_SLICE")
                    .addIngredient(Material.MELON_SLICE)
                    .addIngredient(Material.GOLD_BLOCK));

}