package me.emxion.shootworld.Loadouts;

import me.emxion.shootworld.Items.Heals.Heal;
import me.emxion.shootworld.Items.Heals.List.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

public class HealGUI {
    private final Inventory inv;
    private HashMap<Integer, Heal> healsSlots = new HashMap<>();

    public HealGUI() {
        // Create a new inventory, with no owner (as this isn't a real inventory), a size of nine, called example
        this.inv = Bukkit.createInventory(null, 18, "Soins");

        this.healsSlots.put(0, new CrazedKiller());
        this.healsSlots.put(1, new QuickHeal());
        this.healsSlots.put(2, new SpeedDemon());

        // Put the items into the inventory
        initializeItems();
    }

    // You can call this whenever you want to put the items in
    public void initializeItems() {
        for (int i : this.healsSlots.keySet())
            inv.setItem(i, this.healsSlots.get(i).getItem());
    }

    // You can open the inventory with this
    public void openInventory(final HumanEntity ent) {
        ent.openInventory(inv);
    }

    public Inventory getInventory() {
        return this.inv;
    }

    public String onClick(Player player, int getRawSlot) {
        player.sendMessage("vous avez selectionnez : " + this.healsSlots.get(getRawSlot).getName());
        return this.healsSlots.get(getRawSlot).getName();
    }
}
