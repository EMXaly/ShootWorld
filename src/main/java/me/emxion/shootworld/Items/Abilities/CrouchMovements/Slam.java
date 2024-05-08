package me.emxion.shootworld.Items.Abilities.CrouchMovements;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.Collection;
import java.util.HashMap;

public class Slam extends CrouchingMovement {

    protected HashMap<Player, Boolean> crouching = new HashMap<Player, Boolean>();
    protected HashMap<Player, Double> yVelocity = new HashMap<Player, Double>();

    public Slam() {
        this.name = "Slam";
        this.material = Material.ANVIL;
        this.item = new ItemStack(this.material, 1);
        this.cooldown = 80;

        this.setup();
    }
    @Override
    public void movement(Player player) {
        Vector playerVelocity = player.getVelocity();
        if (!player.isOnGround()) {
            playerVelocity.setY(playerVelocity.getY() - 0.1);
            player.setVelocity(playerVelocity);
            this.yVelocity.put(player, playerVelocity.getY());
        }
        else {
            if (crouching.get(player))
                this.slamming(player);
        }
    }

    private void slamming(Player player) {
        if (player.getCooldown(this.material) > 0)
            return;

        if (yVelocity.get(player) == null) // weird that it can append...
            return;

        if (yVelocity.get(player) > - 1.4)
            return;

        Location playerLocation = player.getLocation();
        Collection<Entity> inRadius = playerLocation.getWorld().getNearbyEntities(playerLocation, 3, 1, 3);
        for (Entity e: inRadius) {
            if (e instanceof LivingEntity && !e.equals(player)) {
                LivingEntity le = (LivingEntity) e;
                le.damage(5, player);
            }
        }

        this.yVelocity.remove(player);
        player.setCooldown(this.material, this.cooldown);
    }

    @Override
    public void crouching(Player player) {
        crouching.putIfAbsent(player, !player.isSneaking());
        crouching.replace(player, !player.isSneaking());
    }
}
