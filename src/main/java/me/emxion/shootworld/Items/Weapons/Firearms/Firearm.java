package me.emxion.shootworld.Items.Weapons.Firearms;

import me.emxion.shootworld.Items.Weapons.Weapon;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.*;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public abstract class Firearm extends Weapon {
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
            Arrow arrow = player.launchProjectile(org.bukkit.entity.Arrow.class);
            playerLocation.add(new Vector(this.getBulletSpread(this.accuracy),this.getBulletSpread(this.accuracy),this.getBulletSpread(this.accuracy)));
            arrow.setVelocity(playerLocation.multiply(this.projectileVelocity));

            arrow.setCustomName(this.name);
            arrow.setGravity(this.hasGravity);
            arrow.setPickupStatus(AbstractArrow.PickupStatus.DISALLOWED);
        }

        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS,this.volume, this.pitch);
        player.setCooldown(this.material, this.fireRate);

        this.currentAmmo.replace(player, this.currentAmmo.get(player)-1);
        ItemStack item = this.checkPlayerGun(player);
        this.printAmmo(player, item);

        this.reloading(player);
    }

    @Override
    public void onHit(ProjectileHitEvent event) {
        // detroyed on wall
        if(!(event.getHitEntity() instanceof LivingEntity)) {
            event.getEntity().remove();
            return;
        }

        LivingEntity damager = (LivingEntity) event.getEntity().getShooter();
        LivingEntity damaged = (LivingEntity) event.getHitEntity();

        damaged.setNoDamageTicks(0);
        damaged.damage(this.damage, damager);

        event.setCancelled(true);
    }
}
