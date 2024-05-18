package me.emxion.shootworld.Items.Weapons.Launchers;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.List;

public class Shrapnel extends Launcher {
    private int nbShrapnel = 4;
    public Shrapnel() {
        this.name = "Shrapnel";
        this.material = Material.PRISMARINE_CRYSTALS;
        this.item = new ItemStack(this.material, 1);
        this.damage = 3;
        this.power = 1f;
        this.fireRate = 30;
        this.magazineSize = 1;
        this.reloadTime = 30;
        this.accuracy = 0.05;
        this.projectileVelocity = 1.25;
        this.volume = 2.5f;
        this.pitch = 0.85f;
        this.nbProjectile = 1;
        this.hasGravity = true;

        this.setup();
    }

    @Override
    public void exploding(EntityExplodeEvent event) {
        TNTPrimed tnt = (TNTPrimed) event.getEntity();
        Location explosion = event.getLocation();
        World world = tnt.getWorld();

        world.spawnParticle(Particle.EXPLOSION_LARGE, explosion, (int) this.power);
        world.playSound(explosion, Sound.ENTITY_GENERIC_EXPLODE, this.power * 2, 1f);

        if (tnt.getSource() instanceof LivingEntity) {
            for (int i = 0; i < this.nbShrapnel; i++) {
                Vector explosionDirection = new Vector(0, 0.25, 0);
                Snowball snowball = world.spawn(tnt.getLocation(), org.bukkit.entity.Snowball.class);
                explosionDirection.add(new Vector(this.getBulletSpread(this.accuracy * 2),this.getBulletSpread(this.accuracy * 2),this.getBulletSpread(this.accuracy * 2)));
                snowball.setVelocity(explosionDirection.multiply(this.projectileVelocity));

                snowball.setCustomName(this.name);
                snowball.setGravity(this.hasGravity);
            }
        }
        else {
            List<Entity> entities = (List<Entity>) explosion.getNearbyEntities(this.power, this.power, this.power);
            for (Entity e: entities) {
                if (e instanceof LivingEntity) {
                    LivingEntity le = (LivingEntity) e;
                    le.damage(this.damage, tnt.getSource());
                    le.setNoDamageTicks(0);
                }
            }
        }
    }
}
