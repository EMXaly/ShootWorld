package me.emxion.shootworld.Items.Abilities.SwapMovements;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class Dash extends SwapMovement {
    public Dash() {
        this.name = "Dash";
        this.material = Material.FIREWORK_ROCKET;
        this.item = new ItemStack(this.material, 1);
        this.cooldown = 50;

        this.setup();
    }
    @Override
    public void movement(Player player) {
        if (player.getCooldown(this.material) > 0)
            return;

        Location playerLocation = player.getLocation(); // récupére la localisation du joueur
        Vector direction = playerLocation.getDirection(); // récupére la direction où regarde le joueur
        //player.setVelocity(direction.multiply(this.boost)); // change la vélocité du joueur par la direction où regarde le joueur et la multiplie par le boost
        Vector playerVelocity = player.getVelocity().multiply(1);
        playerVelocity.setY(0);
        Vector directionVelocity = direction.multiply(1);
        player.setVelocity(playerVelocity.add(directionVelocity));

        player.setCooldown(this.material, this.cooldown);
    }
}
