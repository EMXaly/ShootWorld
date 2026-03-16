package me.emxion.shootworld.Items.Weapons.Launchers;

import me.emxion.shootworld.ShootWorld;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class RocketLauncher extends Launcher {
    public RocketLauncher() {
        this.name = "RocketLauncher";
        this.material = Material.COAL;
        this.item = new ItemStack(this.material, 1);
        this.damage = 8;
        this.power = 2.5f;
        this.fireRate = 25;
        this.magazineSize = 4;
        this.reloadTime = 19;
        this.accuracy = 0.0225;
        this.projectileVelocity = 4;
        this.volume = 3.5f;
        this.pitch = 0.75f;
        this.nbProjectile = 1;
        this.hasGravity = false;

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
