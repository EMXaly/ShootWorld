package me.emxion.shootworld.Loadouts;

import me.emxion.shootworld.Items.Abilities.Ability;
import me.emxion.shootworld.Items.Heals.Heal;
import me.emxion.shootworld.Items.LoadItems;
import me.emxion.shootworld.Items.Weapons.Weapon;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public class Loadout {
    private final Inventory inv;
    private PlayersLoadouts playersLoadouts;

    public Loadout(PlayersLoadouts playersLoadouts) {
        this.playersLoadouts = playersLoadouts;
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

    private Inventory initializeLoadoutInventory(PlayerLoadout playerLoadout) {
        Inventory playerInventory = Bukkit.createInventory(null, 45, "Loadout");

        int i = 0;
        for (String weapon: playerLoadout.getWeapons().values()) {
            if (Objects.equals(weapon, "null"))
                playerInventory.setItem(i, createGuiItem(Material.RED_STAINED_GLASS, "Arme " + i+1, ""));
            else {
                for (Weapon weapon1: new LoadItems().getWeapons()) {
                    if (weapon1.getName().equals(weapon))
                        playerInventory.setItem(i, createGuiItem(weapon1.getMaterial(), weapon1.getName(), ""));
                }
            }
            i++;
        }

        i = 9;
        for (String heal: playerLoadout.getHeals().values()) {
            if (Objects.equals(heal, "null"))
                playerInventory.setItem(i, createGuiItem(Material.PINK_STAINED_GLASS, "Soin " + (i-8), ""));
            else {
                for (Heal heal1: new LoadItems().getHeals()) {
                    if (heal1.getName().equals(heal))
                        playerInventory.setItem(i, createGuiItem(heal1.getMaterial(), heal1.getName(), ""));
                }
            }
            i++;
        }

        i = 18;
        for (String ability: playerLoadout.getAbilities().values()) {
            if (Objects.equals(ability, "null"))
                playerInventory.setItem(i, createGuiItem(Material.BLUE_STAINED_GLASS, "Capacité " + (i-17), ""));
            else {
                for (Ability ability1: new LoadItems().getAbilities()) {
                    if (ability1.getName().equals(ability))
                        playerInventory.setItem(i, createGuiItem(ability1.getMaterial(), ability1.getName(), ""));
                }
            }
            i++;
        }

        return playerInventory;
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
        //ent.openInventory(inv);
        ent.openInventory(this.initializeLoadoutInventory(this.playersLoadouts.getPlayerLoadout((Player) ent)));
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
