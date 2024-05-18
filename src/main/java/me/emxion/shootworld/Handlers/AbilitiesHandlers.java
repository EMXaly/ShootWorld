package me.emxion.shootworld.Handlers;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import me.emxion.shootworld.Items.Abilities.Interfaces.*;
import me.emxion.shootworld.Items.Item;
import me.emxion.shootworld.Items.LoadItems;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;

public class AbilitiesHandlers implements Listener {
    private LoadItems loadItems;
    public AbilitiesHandlers(LoadItems loadItems) {
        this.loadItems = loadItems;
    }
    @EventHandler
    public void OnJoining(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.setGameMode(GameMode.SPECTATOR);
        player.getInventory().clear();
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        if (event.getAction().isLeftClick()) {
            Player player = event.getPlayer();
            Inventory playerInventory = player.getInventory();
            for (Item item: loadItems.getOnLeftClickItems())
                if (playerInventory.contains(item.getMaterial())) {
                    OnLeftClick onLeftClick = (OnLeftClick) item;
                    onLeftClick.OnLeftClick(event);
                    return;
                }
        }
    }

    @EventHandler
    public void onMoving(PlayerMoveEvent event) {
        if (!event.hasChangedPosition())
            return;

        Player player = event.getPlayer();
        if (!player.getAllowFlight())
            player.setAllowFlight(true);

        for (Item item: loadItems.getOnMoving()) {
            OnMoving onMoving = (OnMoving) item;
            onMoving.OnMoving(event);
        }

        if (player.isOnGround()) {
            for (Item item: loadItems.getOnLanding()) {
                OnLanding onLanding = (OnLanding) item;
                onLanding.OnLanding(event);
            }
        }

        if (player.isSneaking()) {
            Inventory playerInventory = player.getInventory();

            for (Item item: loadItems.getOnSneaking()) {
                if (playerInventory.contains(item.getMaterial())) {
                    OnSneaking onSneaking = (OnSneaking) item;
                    onSneaking.OnSneaking(event);
                }
            }
        }
    }

    @EventHandler
    public void OnSwapItem(PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();
        Inventory playerInventory = player.getInventory();

        for (Item item: this.loadItems.getOnSwaping())
            if (playerInventory.contains(item.getMaterial())) {
                OnSwapingItem swapItem = (OnSwapingItem) item;
                swapItem.OnSwapItem(event);
                event.setCancelled(true);
            }
    }

    @EventHandler
    public void OnFlight(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();
        Inventory playerInventory = player.getInventory();

        if (player.getGameMode() != GameMode.CREATIVE && player.getGameMode() != GameMode.SPECTATOR) {
            event.setCancelled(true);
            player.setAllowFlight(false);
            player.setFlying(false);
        }

        if(event.isFlying() && (player.getGameMode() != GameMode.CREATIVE || player.getGameMode() != GameMode.SPECTATOR)) {
            for (Item item: loadItems.getOnFlying())
                if (playerInventory.contains(item.getMaterial())) {
                    OnFlying onFlying = (OnFlying) item;
                    onFlying.OnFlying(event);
                }
        }
    }

    @EventHandler
    public void onJumping(PlayerJumpEvent event) {
        Player player = event.getPlayer();
        Inventory playerInventory = player.getInventory();
        for (Item item: loadItems.getOnJumping()) {
            if (playerInventory.contains(item.getMaterial())) {
                OnJumping onJumping = (OnJumping) item;
                onJumping.OnJumping(event);
            }
        }
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        Projectile projectile = event.getEntity();
        for (Item item: loadItems.getOnProjectileHit()) {
            if (projectile.getName().equals(item.getName())) {
                OnProjectileHit onProjectileHit = (OnProjectileHit) item;
                onProjectileHit.onProjectileHit(event);
            }
        }
    }
}
