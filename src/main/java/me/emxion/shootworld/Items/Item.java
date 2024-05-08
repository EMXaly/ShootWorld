package me.emxion.shootworld.Items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public interface Item {
    public void setName(String name);
    public String getName();
    public Material getMaterial();
    public ItemStack getItem();
}
