package me.emxion.shootworld.Items.Weapons;

import me.emxion.shootworld.Items.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public interface Weapon extends Item {
    public void attacking(PlayerInteractEvent event);
    public void addPlayer(Player player);
}
