package me.emxion.shootworld.Loadouts;

import me.emxion.shootworld.Items.Weapons.Firearms.*;
import me.emxion.shootworld.Items.Weapons.Flames.FlameThrower;
import me.emxion.shootworld.Items.Weapons.Flames.FlareGun;
import me.emxion.shootworld.Items.Weapons.Launchers.*;
import me.emxion.shootworld.Items.Weapons.Weapon;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.HashMap;

public class WeaponGUI {
    private final Inventory inv;
    private HashMap<Integer, Weapon> weaponsSlots = new HashMap<>();
    private HashMap<Player, Integer> playerSlot = new HashMap<>();

    public WeaponGUI() {
        // Create a new inventory, with no owner (as this isn't a real inventory), a size of nine, called example
        this.inv = Bukkit.createInventory(null, 36, "Armes");

        this.weaponsSlots.put(0, new SMG());
        this.weaponsSlots.put(1, new LMG());
        this.weaponsSlots.put(2, new AR());
        this.weaponsSlots.put(3, new Shotgun());
        this.weaponsSlots.put(4, new Sniper());
        this.weaponsSlots.put(9, new RocketLauncher());
        this.weaponsSlots.put(10, new GrenadeLauncher());
        this.weaponsSlots.put(11, new Shrapnel());
        this.weaponsSlots.put(18, new FlameThrower());
        this.weaponsSlots.put(19, new FlareGun());


        // Put the items into the inventory
        initializeItems();
    }

    public void addPlayerSlot(Player player, int playerSlot) {
        this.playerSlot.put(player, playerSlot);
    }

    // You can call this whenever you want to put the items in
    public void initializeItems() {
        for (int i : this.weaponsSlots.keySet())
            inv.setItem(i, this.weaponsSlots.get(i).getItem());
    }

    // Nice little method to create a gui item with a custom name, and description
    protected ItemStack createGuiItem(final Material material, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        // Set the name of the item
        meta.setDisplayName(name);

        // Set the lore of the item
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
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
                return weaponsSlots.get(getRawSlot).getName();
            }

            @Override
            public String setValue(String value) {
                return null;
            }
        };
    }
}
