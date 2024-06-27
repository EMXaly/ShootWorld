package me.emxion.shootworld.Items.Abilities.List;

import me.emxion.shootworld.Items.Abilities.Ability;
import me.emxion.shootworld.Items.Abilities.Interfaces.OnLanding;
import me.emxion.shootworld.Items.Abilities.Interfaces.OnSwapingItem;
import me.emxion.shootworld.ShootWorld;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.List;

public class Dash extends Ability implements OnSwapingItem, OnLanding {
    private final float velocityMult = 1.5f;
    private HashMap<Player, Location> locations = new HashMap<>();

    public Dash() {
        this.name = "Dash";
        this.material = Material.FIREWORK_ROCKET;
        this.item = new ItemStack(this.material, 1);
        this.cooldown = 50;
        this.sound = Sound.ENTITY_FIREWORK_ROCKET_LAUNCH;
        this.volume = 1f;
        this.pitch = 1f;

        this.setup();
    }
    @Override
    public void OnSwapItem(PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();

        if (player.getCooldown(this.material) > 0)
            return;

        Location playerLocation = player.getLocation(); // récupére la localisation du joueur
        Vector direction = playerLocation.getDirection(); // récupére la direction où regarde le joueur
        Vector playerVelocity = player.getVelocity().multiply(this.velocityMult);
        playerVelocity.setY(0);
        Vector directionVelocity = direction.multiply(this.velocityMult);
        player.setVelocity(playerVelocity.add(directionVelocity));

        player.getWorld().playSound(playerLocation, this.sound, SoundCategory.PLAYERS, this.volume, this.pitch);
        BukkitRunnable particleTask = new BukkitRunnable() {
            @Override
            public void run() {
                if (!player.isOnline() || player.isDead() || player.isOnGround()) {
                    this.cancel();
                    return;
                }

                Location particleLocation = player.getLocation().add(0, 1, 0);
                player.getWorld().spawnParticle(Particle.GLOW, particleLocation, 2, 0.3, 0.3, 0.3, 2);
            }
        };
        particleTask.runTaskTimer(ShootWorld.getPlugin(ShootWorld.class), 0, 1);

        player.setCooldown(this.material, this.cooldown);
        this.finishCooldown(player);
        this.locations.put(player, playerLocation);
    }

    @Override
    public void OnLanding(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (!this.locations.containsKey(player))
            return;

        double distance = player.getLocation().distance(this.locations.get(player));
        //player.sendMessage("distance parcouru Dash : " + distance);
        this.locations.remove(player);
    }

    @Override
    public List<Ability> getIncompatibleAbilities() {
        return null;
    }
}
