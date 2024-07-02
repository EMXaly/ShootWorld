package me.emxion.shootworld.Loadouts;

import me.emxion.shootworld.Items.Abilities.Ability;
import me.emxion.shootworld.Items.Abilities.List.*;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

public class AbilityGUI {
    private final Inventory inv;
    private HashMap<Integer, Ability> abilitySlots = new HashMap<>();
    private HashMap<Player, Integer> playerSlot = new HashMap<>();

    public AbilityGUI() {
        // Create a new inventory, with no owner (as this isn't a real inventory), a size of nine, called example
        this.inv = Bukkit.createInventory(null, 45, "Capacités");

        this.abilitySlots.put(0, new Dash());
        this.abilitySlots.put(9, new JumpPad());
        this.abilitySlots.put(10, new AntiGravity());
        this.abilitySlots.put(18, new DoubleJump());
        this.abilitySlots.put(19, new Teleporter());
        this.abilitySlots.put(27, new Slam());
        this.abilitySlots.put(28, new Slide());

        // Put the items into the inventory
        initializeItems();
    }

    public void addPlayerSlot(Player player, int playerSlot) {
        this.playerSlot.put(player, playerSlot);
    }

    // You can call this whenever you want to put the items in
    public void initializeItems() {
        for (int i : this.abilitySlots.keySet())
            inv.setItem(i, this.abilitySlots.get(i).getItem());
    }

    // You can open the inventory with this
    public void openInventory(final HumanEntity ent) {
        ent.openInventory(inv);
    }

    public Inventory getInventory() {
        return this.inv;
    }

    public org.apache.commons.lang3.tuple.Pair<Integer, String> onClick(Player player, int getRawSlot) {
        return new Pair<Integer, String>() {
            @Override
            public Integer getLeft() {
                return playerSlot.get(player);
            }

            @Override
            public String getRight() {
                return abilitySlots.get(getRawSlot).getName();
            }

            @Override
            public String setValue(String value) {
                return null;
            }
        };
    }

}
