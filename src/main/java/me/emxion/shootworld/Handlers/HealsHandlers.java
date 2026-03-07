package me.emxion.shootworld.Handlers;

import me.emxion.shootworld.Items.Heals.Interfaces.*;
import me.emxion.shootworld.Items.Item;
import me.emxion.shootworld.Items.LoadItems;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;

public class HealsHandlers implements Listener {
    private final LoadItems loadItems;

    public HealsHandlers(LoadItems loadItems) {
        this.loadItems = loadItems;
    }
    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        if (event.getAction().isRightClick()) {
            Player player = event.getPlayer();
            if (player.getGameMode() != GameMode.CREATIVE)
                event.setCancelled(true);

            if (player.isDead())
                return;

            for (Item item: loadItems.getHeals()) {
                if (player.getItemInHand().getType() == item.getItem().getType()) {
                    IOnRightClick onRightClick = (IOnRightClick) item;
                    onRightClick.onRightClick(event);
                    return;
                }
            }
        }
    }

    @EventHandler
    public void onKill(PlayerDeathEvent event) {
        Player player = event.getPlayer().getKiller();
        if (player == null)
            return;
        if (player.isDead())
            return;

        Inventory playerInventory = player.getInventory();
        for (Item item: loadItems.getOnKill()) {
            if (playerInventory.contains(item.getMaterial())) {
                IOnKill onKill = (IOnKill) item;
                onKill.onKill(event);
                return;
            }
        }
    }
    @EventHandler
    public void onMoving(PlayerMoveEvent event) {
        if (!event.hasChangedPosition())
            return;

        Player player = event.getPlayer();

        if (player.isDead())
            return;


        Inventory playerInventory = player.getInventory();
        for (Item item: loadItems.getOnSpeed()) {
            if (playerInventory.contains(item.getItem())) {
                IOnSpeed onSpeed = (IOnSpeed) item;
                onSpeed.onSpeed(event);
            }
        }
    }

}
