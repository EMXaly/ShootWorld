package me.emxion.shootworld.Items.Weapons.Firearms;

import me.emxion.shootworld.ShootWorld;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class Shotgun extends Firearm {
    public Shotgun() {
        this.name = "Shotgun";
        this.material = Material.LAPIS_LAZULI;
        this.item = new ItemStack(this.material, 1);
        this.damage = 1;
        this.fireRate = 19;
        this.nbProjectile = 8;
        this.hasGravity = false;
        this.magazineSize = 6;
        this.reloadTime = 15;
        this.accuracy = 0.08;
        this.projectileVelocity = 4;
        this.volume = 3f;
        this.pitch = 2f;

        this.setup();
    }

    @Override
    public void reloading(Player player) {
        if (this.currentAmmo.get(player) <= 0) {
            player.setCooldown(this.material, this.reloadTime + this.fireRate);
            this.emptyMag(player);
        } else
            this.notEmptyMag(player);

    }

    private void emptyMag(Player player) {
        int reloading = new BukkitRunnable() {
            @Override
            public void run() {
                currentAmmo.replace(player, currentAmmo.get(player) + 1);
                ItemStack item = checkPlayerGun(player);
                printAmmo(player, item);
                player.playSound(player.getLocation(), Sound.BLOCK_CHAIN_HIT, 1.5f, 1f);

                if (currentAmmo.get(player) < magazineSize)
                    reloading(player);
            }
        }.runTaskLaterAsynchronously(ShootWorld.getPlugin(ShootWorld.class), this.reloadTime + this.fireRate).getTaskId();

        this.reloads.put(player, reloading);
    }

    private void notEmptyMag(Player player) {
        int reloading = new BukkitRunnable() {
            @Override
            public void run() {
                currentAmmo.replace(player, currentAmmo.get(player) + 1);
                ItemStack item = checkPlayerGun(player);
                printAmmo(player, item);
                player.playSound(player.getLocation(), Sound.BLOCK_CHAIN_HIT, 1.5f, 1f);

                if (currentAmmo.get(player) < magazineSize)
                    reloading(player);
            }
        }.runTaskLaterAsynchronously(ShootWorld.getPlugin(ShootWorld.class), this.reloadTime + player.getCooldown(this.material)).getTaskId();

        this.reloads.put(player, reloading);
    }
}
