package ilevelin.ileuhc.controllers;

import ilevelin.ileuhc.data.CustomItemEffect;
import ilevelin.ileuhc.data.CustomItemList;
import ilevelin.ileuhc.utils.Messenger;
import ilevelin.ileuhc.utils.customItem.CustomItemBuilder;
import ilevelin.ileuhc.utils.enums.AppleType;
import ilevelin.ileuhc.utils.enums.ChatColor;
import ilevelin.ileuhc.utils.enums.DeathSource;
import ilevelin.ileuhc.utils.textFormatting.TextFormatterBlock;
import ilevelin.ileuhc.utils.textFormatting.FormattedTextBlock;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class EventsController implements Listener {

    private final CustomItemBuilder customItemBuilderInstance = CustomItemBuilder.getInstance();


    @EventHandler
    public void onPlayerItemConsume(PlayerItemConsumeEvent event) {
        String consumedItemDisplayName = event.getItem().getItemMeta().getDisplayName();
        AppleType consumedAppleType = null;

        if (consumedItemDisplayName.equals(new ItemStack(Material.GOLDEN_APPLE).getItemMeta().getDisplayName())) {
            consumedAppleType = AppleType.GOLDEN_APPLE;
        } else if (consumedItemDisplayName.equals(new ItemStack(Material.ENCHANTED_GOLDEN_APPLE).getItemMeta().getDisplayName())) {
            consumedAppleType = AppleType.ENCHANTED_GOLDEN_APPLE;
        } else if (consumedItemDisplayName.equals(customItemBuilderInstance.getFormattedName(CustomItemList.WITHER_APPLE))) {
            consumedAppleType = AppleType.WITHER_APPLE;
        } else if (consumedItemDisplayName.equals(customItemBuilderInstance.getFormattedName(CustomItemList.PLAYER_HEAD_APPLE))) {
            consumedAppleType = AppleType.PLAYER_HEAD_APPLE;
        } else if (consumedItemDisplayName.equals(customItemBuilderInstance.getFormattedName(CustomItemList.SUPER_GOLD_APPLE))) {
            consumedAppleType = AppleType.SUPER_GOLD_APPLE;
        } else if (consumedItemDisplayName.equals(customItemBuilderInstance.getFormattedName(CustomItemList.TEAL_APPLE))) {
            consumedAppleType = AppleType.TEAL_APPLE;
        } else if (consumedItemDisplayName.equals(customItemBuilderInstance.getFormattedName(CustomItemList.DIAMOND_APPLE))) {
            consumedAppleType = AppleType.DIAMOND_APPLE;
        } else if (consumedItemDisplayName.equals(customItemBuilderInstance.getFormattedName(CustomItemList.TEAL_GOLD_APPLE))) {
            consumedAppleType = AppleType.TEAL_GOLD_APPLE;
        } else if (consumedItemDisplayName.equals(customItemBuilderInstance.getFormattedName(CustomItemList.BLUE_GOLD_APPLE))) {
            consumedAppleType = AppleType.BLUE_GOLD_APPLE;
        } else if (consumedItemDisplayName.equals(customItemBuilderInstance.getFormattedName(CustomItemList.KING_APPLE))) {
            consumedAppleType = AppleType.KING_APPLE;
        } else if (consumedItemDisplayName.equals(customItemBuilderInstance.getFormattedName(CustomItemList.COLLECTOR_APPLE))) {
            consumedAppleType = AppleType.COLLECTOR_APPLE;
        }

        if (consumedAppleType == null) return;

        StatsController.getInstance().addConsumedApple(event.getPlayer().getDisplayName(), consumedAppleType);
        if (consumedAppleType != AppleType.GOLDEN_APPLE && consumedAppleType != AppleType.ENCHANTED_GOLDEN_APPLE) {
            event.getPlayer().getItemInUse().setAmount(event.getPlayer().getItemInUse().getAmount()-1);
            CustomItemEffect.applyEffect(event.getPlayer(), consumedAppleType);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        switch (event.getEntity().getType()){
            case GHAST:
                event.getDrops().forEach((drop) -> {
                    if (drop.getType().equals(Material.GHAST_TEAR))
                        drop.setType(Material.GOLD_INGOT);
                });
                break;
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player deadPlayer = event.getEntity();
        deadPlayer.getAttribute(Attribute.GENERIC_MAX_HEALTH).getModifiers().forEach((attributeModifier ->
                deadPlayer.getAttribute(Attribute.GENERIC_MAX_HEALTH).removeModifier(attributeModifier)));

        deadPlayer.setGameMode(GameMode.SPECTATOR);
        if (GameController.getInstance().isGameRunning()) GameController.getInstance().killPlayer(deadPlayer);

        ItemStack playerHeadStack = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta playerHeadMeta = (SkullMeta) playerHeadStack.getItemMeta();
        playerHeadMeta.setOwningPlayer(deadPlayer);
        playerHeadMeta.setDisplayName(new FormattedTextBlock()
                .setText(deadPlayer.getDisplayName() + "'s head")
                .setColor(ChatColor.DARK_PINK)
                .setFormatterBlock(new TextFormatterBlock().setBold(true))
                .toStringWithoutResetEnd());
        playerHeadStack.setItemMeta(playerHeadMeta);
        event.getDrops().add(playerHeadStack);

        Player killer = deadPlayer.getKiller();
        if (killer != null) {
            StatsController.getInstance().addKilledPlayer(killer.getDisplayName());
            StatsController.getInstance().setDeathSource(deadPlayer.getDisplayName(), DeathSource.PLAYER);
        } else
            StatsController.getInstance().setDeathSource(deadPlayer.getDisplayName(), DeathSource.OTHER);
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Entity damagedEntity = event.getEntity();
        Entity attackingEntity = event.getDamager();
        if (damagedEntity instanceof Player && attackingEntity instanceof Player) {
            StatsController.getInstance().addDamageToPlayers(((Player) attackingEntity).getDisplayName(), (float) event.getFinalDamage());
            StatsController.getInstance().addReceivedFromPlayers(((Player) damagedEntity).getDisplayName(), (float) event.getFinalDamage());
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        Entity damagedEntity = event.getEntity();
        if (damagedEntity instanceof Player) {
            Player damagedPlayer = (Player) damagedEntity;
            StatsController.getInstance().addReceivedFromOther(damagedPlayer.getDisplayName(), (float) event.getFinalDamage());
        }
    }

    @EventHandler
    public void onEntityHeal(EntityRegainHealthEvent event) {
        Entity healedEntity = event.getEntity();
        if (healedEntity instanceof Player) {
            Player healedPlayer = (Player) healedEntity;
            StatsController.getInstance().addLifeHealed(healedPlayer.getDisplayName(), (float) event.getAmount());
        }
    }

    @EventHandler
    public void onPlayerEntersPortal(PlayerPortalEvent event) {
        switch (event.getCause()) {
            case END_PORTAL:
                event.setCancelled(true);
                break;
            case NETHER_PORTAL:
                if (GameController.getInstance().getTimeRemaining() == 0) {
                    Messenger.MessagePlayerTranslated(event.getPlayer(), "Events.Portal.Error.NetherDisabled");
                    event.getPlayer().playSound(event.getPlayer(), Sound.ENTITY_SILVERFISH_DEATH, 0.5f, 0.5f);
                    event.setCancelled(true);
                }
                break;
        }
    }

    @EventHandler
    public void onPlayerLogin(PlayerJoinEvent event) {
        if (GameController.getInstance().isGameRunning() && !GameController.getInstance().isPlayerAlive(event.getPlayer()))
            event.getPlayer().setGameMode(GameMode.SPECTATOR);
    }

}
