package me.emxion.shootworld.Handlers;

import me.emxion.shootworld.Items.LoadItems;
import me.emxion.shootworld.Items.Weapons.Launchers.Launcher;
import me.emxion.shootworld.Items.Weapons.Weapon;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class WeaponsHandlers implements Listener {
    private LoadItems loadItems;

    public WeaponsHandlers(LoadItems loadItems) {
        this.loadItems = loadItems;
    }
    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        if (event.getAction().isRightClick()) {
            event.setCancelled(true);
            Player player = event.getPlayer();
            for (Weapon weapon: loadItems.getWeapons()) {
                if (player.getItemInHand().getType() == weapon.getItem().getType()) {
                    weapon.firing(event);
                    return;
                }
            }
        }
    }

    @EventHandler
    public void onItemHeld(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        ItemStack oldItem = player.getInventory().getItem(event.getPreviousSlot());
        ItemStack newItem = player.getInventory().getItem(event.getNewSlot());
        for (Weapon weapon: loadItems.getWeapons()) {
            if (oldItem != null)
                if (weapon.getMaterial() == oldItem.getType())
                    weapon.holsterWeapon(player);
            if (newItem != null)
                if (weapon.getMaterial() == newItem.getType())
                    weapon.deployWeapon(player);
        }
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        Projectile projectile = event.getEntity();
        for (Weapon weapon: loadItems.getWeapons()) {
            if (projectile.getName().equals(weapon.getName()))
                weapon.onHit(event);
        }
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        event.setCancelled(true);

        for (Weapon weapon : loadItems.getWeapons()) {
            if (weapon instanceof Launcher) {
                Launcher launcher = (Launcher) weapon;
                if (Objects.equals(event.getEntity().getCustomName(), launcher.getName()))
                    launcher.exploding(event);
            }
        }
    }
}
