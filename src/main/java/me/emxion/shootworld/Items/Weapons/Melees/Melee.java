package me.emxion.shootworld.Items.Weapons.Melees;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public interface Melee {
    public String getName();
    public ItemStack getItem();
    public void attacking(LivingEntity attacker, LivingEntity attacked);
}
