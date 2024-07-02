package me.emxion.shootworld.Loadouts;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class Loadout {
    private final Inventory inv;

    public Loadout() {
        // Create a new inventory, with no owner (as this isn't a real inventory), a size of nine, called example
        this.inv = Bukkit.createInventory(null, 45, "Loadout");

        // Put the items into the inventory
        initializeItems();
    }

    // You can call this whenever you want to put the items in
    public void initializeItems() {
        inv.addItem(createGuiItem(Material.RED_STAINED_GLASS, "Arme 1", ""));
        inv.addItem(createGuiItem(Material.RED_STAINED_GLASS, "Arme 2", ""));

        inv.setItem(9, createGuiItem(Material.PINK_STAINED_GLASS, "Soin ", ""));

        for (int i = 1; i <= 4; i++) {
            ItemStack itemStack = createGuiItem(Material.BLUE_STAINED_GLASS, "Abilité " + i, "");
            inv.setItem(17 + i, itemStack);
        }

        /*for (int i = 1; i <= 9; i++) {
            ItemStack itemStack = createGuiItem(Material.BLACK_STAINED_GLASS, "Loadout " + i, "");
            inv.setItem(35 + i, itemStack);
        }*/
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

    public int onClick(Player player, int getRawSlot) {
        if (this.inv.getItem(getRawSlot).getType() == Material.RED_STAINED_GLASS)
            return 1;
        else if (this.inv.getItem(getRawSlot).getType() == Material.PINK_STAINED_GLASS)
            return 2;
        else if (this.inv.getItem(getRawSlot).getType() == Material.BLUE_STAINED_GLASS)
            return 3;
        return 0;
    }
}
