package me.emxion.shootworld.Items.Abilities.List;

import me.emxion.shootworld.Items.Abilities.Ability;
import me.emxion.shootworld.Items.Abilities.Interfaces.OnLeftClick;
import me.emxion.shootworld.Items.Abilities.Interfaces.OnProjectileHit;
import me.emxion.shootworld.ShootWorld;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.*;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AntiGravity extends Ability implements OnLeftClick, OnProjectileHit {
    public AntiGravity() {
        this.name = "AntiGravity";
        this.material = Material.END_CRYSTAL;
        this.item = new ItemStack(this.material, 1);
        this.cooldown = 80;
        this.sound = Sound.ENTITY_SHULKER_BULLET_HIT;
        this.volume = 5f;
        this.pitch = 0.75f;
    }
    @Override
    public void OnLeftClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (player.getCooldown(this.material) > 0)
            return;

        Vector playerLocation = player.getEyeLocation().getDirection();
        EnderPearl enderPearl = player.launchProjectile(org.bukkit.entity.EnderPearl.class);
        enderPearl.setVelocity(playerLocation.multiply(1.5f));
        enderPearl.setCustomName(this.name);
        enderPearl.setShooter(null);

        player.setCooldown(this.material, this.cooldown);
        this.finishCooldown(player);
    }

    @Override
    public void onProjectileHit(ProjectileHitEvent event) {
        Projectile projectile = event.getEntity();
        Location projectileLocation = event.getEntity().getLocation();
        projectile.getWorld().playSound(projectileLocation, this.sound, SoundCategory.PLAYERS, this.volume, this.pitch);
        Collection<Entity> inRadius = projectileLocation.getWorld().getNearbyEntities(projectileLocation, 3, 3, 3);
        for (Entity e: inRadius) {
            if (e instanceof LivingEntity) {
                e.setGravity(false);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        e.setGravity(true);
                    }
                }.runTaskLater(ShootWorld.getPlugin(ShootWorld.class), 30);
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
