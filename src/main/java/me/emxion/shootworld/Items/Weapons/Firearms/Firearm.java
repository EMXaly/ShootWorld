package me.emxion.shootworld.Items.Weapons.Firearms;

import me.emxion.shootworld.Items.Weapons.Weapon;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

public abstract class Firearm extends Weapon {
    @Override
    public void firing(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();

        if (this.currentAmmo.get(player) == null)
            this.addPlayerCurrentAmmo(player);

        if (player.getCooldown(this.material) > 0)
            return;

        if (this.reloads.containsKey(player))
            this.breakReload(player);

        for (int i = 0; i < this.nbProjectile; i++) {
            Location playerLocation = player.getEyeLocation();
            Vector playerDirection = playerLocation.getDirection();
            playerDirection.add(new Vector(this.getBulletSpread(this.accuracy),this.getBulletSpread(this.accuracy),this.getBulletSpread(this.accuracy)));

            RayTraceResult shootResult = world.rayTrace(playerLocation, playerDirection, (double) 100, FluidCollisionMode.ALWAYS, true, 0.05, p -> !player.getUniqueId().equals(p.getUniqueId()));
            if (shootResult != null)
                if (shootResult.getHitEntity() != null)
                    if (shootResult.getHitEntity() instanceof LivingEntity) {
                        LivingEntity livingEntity = (LivingEntity) shootResult.getHitEntity();
                        livingEntity.setNoDamageTicks(0);
                        livingEntity.damage(this.damage, player);
                    }
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

    }
}
