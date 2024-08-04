package me.emxion.shootworld.Items.Weapons;

import me.emxion.shootworld.Items.Item;
import me.emxion.shootworld.ShootWorld;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public abstract class Weapon extends Item {
    protected float damage;
    protected int fireRate;
    protected int nbProjectile;
    protected boolean hasGravity;
    protected int magazineSize;
    protected HashMap<Player, Integer> currentAmmo = new HashMap<Player, Integer>();
    protected int reloadTime;
    protected HashMap<Player, Integer> reloads = new HashMap<>();
    protected double accuracy;
    protected double projectileVelocity;
    protected float volume;
    protected float pitch;

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public float getDamage() {
        return this.damage;
    }

    public void setReloadTime(int reloadTime) {
        this.reloadTime = reloadTime;
    }

    public int getReloadTime() {
        return this.reloadTime;
    }

    public HashMap<Player, Integer> getCurrentAmmo() {
        return this.currentAmmo;
    }

    public void addPlayerCurrentAmmo(Player player) {
        this.currentAmmo.put(player, this.magazineSize);
    }

    public void deployWeapon(Player player) {
        if (this.currentAmmo.get(player) == null)
            this.addPlayerCurrentAmmo(player);

        if (this.currentAmmo.get(player) < this.magazineSize)
            this.reloading(player);
    }
    public void holsterWeapon(Player player) {
        this.breakReload(player);
    }

    public abstract void firing(PlayerInteractEvent event);

    public void reloading(Player player) {
        if (this.currentAmmo.get(player) <= 0)
            player.setCooldown(this.material, this.reloadTime + this.fireRate);

        int reloading = new BukkitRunnable() {
            @Override
            public void run() {
                currentAmmo.replace(player, magazineSize);
                ItemStack item = checkPlayerGun(player);
                printAmmo(player, item);
                player.playSound(player.getLocation(), Sound.BLOCK_CHAIN_HIT, 1.5f, 1f);
            }
        }.runTaskLaterAsynchronously(ShootWorld.getPlugin(ShootWorld.class), this.reloadTime + this.fireRate).getTaskId();
        this.reloads.put(player, reloading);
    }

    public void breakReload(Player player) {
        if (!this.reloads.containsKey(player))
            return;

        Bukkit.getServer().getScheduler().cancelTask(this.reloads.get(player));
        this.reloads.remove(player);
    }

    protected double getBulletSpread(double accuracy) {
        return (Math.random() * (accuracy - (-accuracy))) - accuracy;
    }

    protected ItemStack checkPlayerGun(Player player) {
        for (ItemStack item: player.getInventory().getContents())
            if (item != null)
                if (item.getType() == this.material) {
                    return item;
                }
        return null;
    }

    protected void printAmmo(Player player, ItemStack item) {
        if (item == null)
            return;

        ItemMeta playerItemMeta = item.getItemMeta();
        playerItemMeta.displayName(Component.text(String.format("%s (%d/%d)", this.name, this.currentAmmo.get(player), this.magazineSize)));
        item.setItemMeta(playerItemMeta);
    }

    public abstract void onHit(ProjectileHitEvent event);
}
