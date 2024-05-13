package me.emxion.shootworld.Items;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class Item {
    protected String name;
    protected Material material;
    protected ItemStack item;
    protected ItemMeta itemMeta;

    public String getName() {
        return name;
    }
    public Material getMaterial() {
        return material;
    }

    public ItemStack getItem() {
        return item;
    }

    public ItemMeta getItemMeta() {
        return itemMeta;
    }

    public void setup() {
        this.itemMeta = this.item.getItemMeta();
        this.itemMeta.displayName(Component.text(this.name));
        this.itemMeta.setUnbreakable(true);
        this.item.setItemMeta(this.itemMeta);
    }
}
