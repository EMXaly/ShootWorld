package me.emxion.shootworld.Items.Abilities;

import me.emxion.shootworld.Items.Item;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface Abilities extends Item {
    public void setCooldown(int cooldown);
    public void movement(Player player);
}
