package me.emxion.shootworld.Items.Weapons.Launchers;

import me.emxion.shootworld.Items.Weapons.Weapon;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.List;

public class Launcher extends Weapon {
    protected float power;
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
            Snowball snowball = player.launchProjectile(org.bukkit.entity.Snowball.class);
            playerLocation.add(new Vector(this.getBulletSpread(this.accuracy),this.getBulletSpread(this.accuracy),this.getBulletSpread(this.accuracy)));
            snowball.setVelocity(playerLocation.multiply(this.projectileVelocity));

            snowball.setCustomName(this.name);
            snowball.setGravity(this.hasGravity);
        }

        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_TWINKLE_FAR, SoundCategory.PLAYERS, this.volume, this.pitch);
        player.setCooldown(this.material, this.fireRate);

        this.currentAmmo.replace(player, this.currentAmmo.get(player)-1);
        ItemStack item = this.checkPlayerGun(player);
        this.printAmmo(player, item);

        this.reloading(player);
    }

    @Override
    public void onHit(ProjectileHitEvent event) {
        Snowball sb = (Snowball) event.getEntity();
        TNTPrimed tnt = sb.getWorld().spawn(sb.getLocation(), TNTPrimed.class);
        tnt.setVisibleByDefault(false);
        tnt.setFuseTicks(0);
        tnt.customName(Component.text(this.getName()));
        tnt.setSource((Entity) sb.getShooter());
        tnt.setCustomNameVisible(false);
        tnt.setSilent(sb.isSilent());
    }

    public void exploding(EntityExplodeEvent event) {
        TNTPrimed tnt = (TNTPrimed) event.getEntity();
        Location explosion = event.getLocation();
        World world = tnt.getWorld();

        world.spawnParticle(Particle.EXPLOSION, explosion, (int) this.power);
        world.playSound(explosion, Sound.ENTITY_GENERIC_EXPLODE, this.power * 2, 1f);


        List<Entity> entities = (List<Entity>) explosion.getNearbyEntities(this.power, this.power, this.power);
        for (Entity e: entities) {
            if (e instanceof LivingEntity) {
                LivingEntity le = (LivingEntity) e;

                if (le.getName().equals(tnt.getSource().getName()))
                    le.damage(this.damage / 2, tnt.getSource());
                else
                    le.damage(this.damage, tnt.getSource());

                le.setNoDamageTicks(0);
            }
        }
    }
}
