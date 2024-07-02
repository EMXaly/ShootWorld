package me.emxion.shootworld.Handlers;

import me.emxion.shootworld.Loadouts.*;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;

public class LoadoutHandlers implements Listener {
    private Loadout loadout = new Loadout();
    private WeaponGUI weaponGUI = new WeaponGUI();
    private HealGUI healGUI = new HealGUI();
    private AbilityGUI abilityGUI = new AbilityGUI();

    private PlayersLoadouts playersLoadouts = new PlayersLoadouts();

    public PlayersLoadouts getPlayersLoadouts() {
        return this.playersLoadouts;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        try {
            playersLoadouts.onPlayerJoin(event.getPlayer());
        }
        catch (IOException e) {
            return;
        }
    }

    // Check for clicks on items
    @EventHandler
    public void onInventoryClick(final InventoryClickEvent event) {
        if (event.getClickedInventory().getHolder() != null)
            return;

        event.setCancelled(true);

        final ItemStack clickedItem = event.getCurrentItem();

        // verify current item is not null
        if (clickedItem == null || clickedItem.getType().isAir()) return;

        final Player player = (Player) event.getWhoClicked();
        int getRawSlot = event.getRawSlot();

        if (event.getView().getTitle().equals("Loadout"))
            switch (loadout.onClick(player, getRawSlot)) {
                case 1:
                    weaponGUI.addPlayerSlot(player, getRawSlot % 9 +1);
                    weaponGUI.openInventory(player);
                    break;
                case 2:
                    healGUI.openInventory(player);
                    break;
                case 3:
                    abilityGUI.addPlayerSlot(player, getRawSlot % 9 +1);
                    abilityGUI.openInventory(player);
                    break;
            }
        else if (event.getView().getTitle().equals("Armes")) {
            Pair<Integer, String> slotWeapon = weaponGUI.onClick(player, getRawSlot);
            this.playersLoadouts.changeWeapon(player, slotWeapon.getLeft(), slotWeapon.getRight());
            loadout.openInventory(player);
        }
        else if (event.getView().getTitle().equals("Soins")) {
            this.playersLoadouts.changeHeal(player, 1, healGUI.onClick(player, getRawSlot));
            loadout.openInventory(player);
        }
        else if (event.getView().getTitle().equals("Capacités")) {
            Pair<Integer, String> slotAbility = abilityGUI.onClick(player, getRawSlot);
            this.playersLoadouts.changeAbility(player, slotAbility.getLeft(), slotAbility.getRight());
            loadout.openInventory(player);
        }
    }

    // Cancel dragging in our inventory
    @EventHandler
    public void onInventoryClick(final InventoryDragEvent event) {
        if (event.getInventory().getHolder() != null)
            return;

        event.setCancelled(true);
    }
}
