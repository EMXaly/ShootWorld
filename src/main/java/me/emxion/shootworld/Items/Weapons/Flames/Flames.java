package me.emxion.shootworld.Items.Weapons.Flames;

import me.emxion.shootworld.Items.Weapons.Weapon;
import me.emxion.shootworld.ShootWorld;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.*;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public abstract class Flames extends Weapon {
    protected long timer;
    protected int fireTick;
    @Override
    public void firing(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (this.currentAmmo.get(player) == null)
            this.addPlayerCurrentAmmo(player);

        if (player.getCooldown(this.material) > 0)
            return;

        if (this.reloads.containsKey(player))
            this.breakReload(player);

        for (int i = 0; i < this.nbProjectile; i++) {
            Vector playerLocation = player.getEyeLocation().getDirection();
            SmallFireball smallFireball = player.launchProjectile(org.bukkit.entity.SmallFireball.class);
            playerLocation.add(new Vector(this.getBulletSpread(this.accuracy),this.getBulletSpread(this.accuracy),this.getBulletSpread(this.accuracy)));
            smallFireball.setVelocity(playerLocation.multiply(this.projectileVelocity));

            smallFireball.setCustomName(this.name);
            smallFireball.setGravity(this.hasGravity);
            smallFireball.setVisualFire(true);
            smallFireball.setShooter(player);

            if (smallFireball.hasGravity())
                this.changeGravity(smallFireball);

            Bukkit.getScheduler().runTaskLater(ShootWorld.getPlugin(ShootWorld.class), smallFireball::remove, this.timer);
        }

        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_FIRE_AMBIENT, SoundCategory.PLAYERS,this.volume, this.pitch);
        player.setCooldown(this.material, this.fireRate);

        this.currentAmmo.replace(player, this.currentAmmo.get(player)-1);
        ItemStack item = this.checkPlayerGun(player);
        this.printAmmo(player, item);

        this.reloading(player);
    }

    protected void changeGravity(SmallFireball smallFireball) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (smallFireball.isDead() || !smallFireball.isValid()) {
                    cancel();
                    return;
                }

                Vector velocity = smallFireball.getVelocity();
                velocity.setY(velocity.getY() - 0.025);
                smallFireball.setVelocity(velocity);
            }
        }.runTaskTimer(ShootWorld.getPlugin(ShootWorld.class), 0L, 1L);
    }

    @Override
    public void onHit(ProjectileHitEvent event) {
        // detroyed on wall
        if(!(event.getHitEntity() instanceof LivingEntity)) {
            this.burning(event);
            event.getEntity().remove();
            return;
        }

        event.setCancelled(true);

        LivingEntity damager = (LivingEntity) event.getEntity().getShooter();
        LivingEntity damaged = (LivingEntity) event.getHitEntity();

        damaged.damage(this.damage, damager);
        damaged.setNoDamageTicks(1);
        damaged.setFireTicks(this.fireTick);
        damaged.setVelocity(damaged.getVelocity().multiply(0.5));
    }

    public abstract void burning(ProjectileHitEvent event);
}
