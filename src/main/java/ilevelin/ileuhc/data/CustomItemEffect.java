package ilevelin.ileuhc.data;

import ilevelin.ileuhc.utils.enums.AppleType;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.time.LocalDateTime;

public class CustomItemEffect {

    private static AttributeModifier getHeartAdderModifier(int heartAmount) {
        return new AttributeModifier("addHeartContainers."+ LocalDateTime.now(), 2*heartAmount, AttributeModifier.Operation.ADD_NUMBER);
    }
    
    public static void applyEffect(Player player, AppleType apple) {
        switch (apple) {
            case WITHER_APPLE:
            case PLAYER_HEAD_APPLE:
            case SUPER_GOLD_APPLE:
                player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 180*20, 1));
                player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 10*20, 1));
                break;

            case TEAL_APPLE:
                player.getAttribute(Attribute.GENERIC_MAX_HEALTH).addModifier(getHeartAdderModifier(2));
                break;

            case DIAMOND_APPLE:
                player.getAttribute(Attribute.GENERIC_MAX_HEALTH).addModifier(getHeartAdderModifier(4));
                break;

            case TEAL_GOLD_APPLE:
                player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 160*20, 0));
                player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 5*20, 1));
                player.getAttribute(Attribute.GENERIC_MAX_HEALTH).addModifier(getHeartAdderModifier(2));
                break;

            case BLUE_GOLD_APPLE:
                player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 160*20, 0));
                player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 5*20, 1));
                player.getAttribute(Attribute.GENERIC_MAX_HEALTH).addModifier(getHeartAdderModifier(4));
                break;

            case KING_APPLE:
                player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 180*20, 2));
                player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 5*20, 3));
                player.getAttribute(Attribute.GENERIC_MAX_HEALTH).addModifier(getHeartAdderModifier(4));
                break;

            case COLLECTOR_APPLE:
                player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 10*60*20, 4));
                player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 5*20, 4));
                player.getAttribute(Attribute.GENERIC_MAX_HEALTH).addModifier(getHeartAdderModifier(8));
                break;
        }
    }

}