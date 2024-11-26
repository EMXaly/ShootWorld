package me.emxion.shootworld.Items.Weapons.Launchers;

import me.emxion.shootworld.ShootWorld;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class GrenadeLauncher extends Launcher {
    private final int fullReload = 60;
    public GrenadeLauncher() {
        this.name = "GrenadeLauncher";
        this.material = Material.CHARCOAL;
        this.item = new ItemStack(this.material, 1);
        this.damage = 9;
        this.power = 2f;
        this.fireRate = 20;
        this.magazineSize = 6;
        this.reloadTime = 17;
        this.accuracy = 0.025;
        this.projectileVelocity = 1.50;
        this.volume = 2.5f;
        this.pitch = 0.85f;
        this.nbProjectile = 1;
        this.hasGravity = true;

        this.setup();
    }

    @Override
    public void reloading(Player player) {
        if (this.currentAmmo.get(player) <= 0) {
            player.setCooldown(this.material, this.fullReload + this.fireRate);
            this.fullReload(player);
        } else
            singleReload(player);
    }

    private void fullReload(Player player) {
        int reloading = new BukkitRunnable() {
            @Override
            public void run() {
                currentAmmo.replace(player, magazineSize);
                ItemStack item = checkPlayerGun(player);
                printAmmo(player, item);
                player.playSound(player.getLocation(), Sound.BLOCK_CHAIN_HIT, 1.5f, 1f);
            }
        }.runTaskLaterAsynchronously(ShootWorld.getPlugin(ShootWorld.class), fullReload + this.fireRate).getTaskId();
        this.reloads.put(player, reloading);
    }

    private void singleReload(Player player) {
        int reloading = new BukkitRunnable() {
            @Override
            public void run() {
                if (player.getCooldown(material) == 0) {
                    currentAmmo.replace(player, currentAmmo.get(player) +1);
                    ItemStack item = checkPlayerGun(player);
                    printAmmo(player, item);
                    player.playSound(player.getLocation(), Sound.BLOCK_CHAIN_HIT, 1.5f, 1f);
                }

                if (currentAmmo.get(player) < magazineSize)
                    reloading(player);
            }
        }.runTaskLaterAsynchronously(ShootWorld.getPlugin(ShootWorld.class), this.reloadTime + player.getCooldown(this.material)).getTaskId();
        this.reloads.put(player, reloading);
    }
}
