package me.emxion.shootworld.Items.Abilities.List;

import me.emxion.shootworld.Items.Abilities.Ability;
import me.emxion.shootworld.Items.Abilities.Interfaces.OnLeftClick;
import me.emxion.shootworld.Items.Abilities.Interfaces.OnProjectileHit;
import me.emxion.shootworld.ShootWorld;
import net.kyori.adventure.util.TriState;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class AntiGravity extends Ability implements OnLeftClick, OnProjectileHit {
    private HashMap<LivingEntity, Integer> entitiesNoGrabity = new HashMap<>();
    public AntiGravity() {
        this.name = "AntiGravity";
        this.material = Material.END_CRYSTAL;
        this.item = new ItemStack(this.material, 1);
        this.cooldown = 100;
        this.sound = Sound.ENTITY_SHULKER_BULLET_HIT;
        this.volume = 5f;
        this.pitch = 0.75f;

        this.setup();
    }
    @Override
    public void OnLeftClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (player.getCooldown(this.material) > 0)
            return;

        Vector playerLocation = player.getEyeLocation().getDirection();
        Snowball snowball = player.launchProjectile(org.bukkit.entity.Snowball.class);
        snowball.setVelocity(playerLocation.multiply(1.5f));
        snowball.setCustomName(this.name);
        snowball.setItem(new ItemStack(Material.ENDER_PEARL, 1));

        player.setCooldown(this.material, this.cooldown);
        this.finishCooldown(player);
    }

    @Override
    public void onProjectileHit(ProjectileHitEvent event) {
        Projectile projectile = event.getEntity();
        Location projectileLocation = event.getEntity().getLocation();
        projectile.getWorld().playSound(projectileLocation, this.sound, SoundCategory.PLAYERS, this.volume, this.pitch);
        Collection<Entity> inRadius = projectileLocation.getWorld().getNearbyEntities(projectileLocation, 3, 3, 3);
        projectileLocation.getWorld().spawnParticle(Particle.END_ROD, projectileLocation, 25, 1, 1, 1, 0.1);
        for (Entity e: inRadius) {
            if (e instanceof LivingEntity) {
                if (this.entitiesNoGrabity.get(e) != null) {
                    Bukkit.getServer().getScheduler().cancelTask(this.entitiesNoGrabity.get(e));
                    this.entitiesNoGrabity.remove(e);
                }

                e.setGravity(false);
                ((LivingEntity) e).setFrictionState(TriState.FALSE);

                int task = new BukkitRunnable() {
                    @Override
                    public void run() {
                        e.setGravity(true);
                        ((LivingEntity) e).setFrictionState(TriState.TRUE);
                    }
                }.runTaskLaterAsynchronously(ShootWorld.getPlugin(ShootWorld.class), 50).getTaskId();

                this.entitiesNoGrabity.put((LivingEntity) e, task);
            }
        }
    }

    @Override
    public List<Ability> getIncompatibleAbilities() {
        List<Ability> incompatibleAbilities = new ArrayList<>();
        incompatibleAbilities.add(new JumpPad());
        return incompatibleAbilities;
    }
}
