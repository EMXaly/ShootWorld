package me.emxion.shootworld.Items.Abilities.FlyMovements;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.HashMap;

public class DoubleJump extends FlyMovement{
    protected HashMap<Player, Integer> maxJump = new HashMap<Player, Integer>();
    protected HashMap<Player, Integer> nbJumps = new HashMap<Player, Integer>();

    public DoubleJump() {
        this.name = "DoubleJump";
        this.material = Material.PISTON;
        this.item = new ItemStack(this.material, 1);
        this.cooldown = 999999999;

        this.setup();
    }
    @Override
    public void movement(Player player) {
        if (player.getCooldown(this.material) > 0)
            return;

        if (this.maxJump.get(player) == null)
            this.addPlayer(player);

        int maxJump = this.maxJump.get(player);
        int nbJumps = this.nbJumps.get(player);

        if (nbJumps + 1 <= maxJump) {
            // loose velocity but gain air strafe
            Vector playerDirection = player.getLocation().getDirection().multiply(0.35);
            playerDirection.setY(0.4);
            player.setVelocity(playerDirection);

            // Keep velocity but loose air strafe
            /*Vector playerVelocity = player.getVelocity();
            Vector playerDirection = player.getLocation().getDirection().multiply(0.35);
            playerDirection.setY(0.4);
            player.setVelocity(playerVelocity.add(playerDirection));*/

            nbJumps++;
            this.nbJumps.replace(player, nbJumps);

            if (nbJumps == maxJump)
                player.setCooldown(this.material, this.cooldown);
        }
    }

    @Override
    public void landing(Player player) {
        this.nbJumps.replace(player, 0);
        player.setCooldown(this.material, 0);
    }

    public void addPlayer(Player player) {
        this.maxJump.put(player, 1);
        this.nbJumps.put(player, 0);
    }

    public void addJump(Player player, int nbJump) {
        if (this.maxJump.get(player) == null)
            this.addPlayer(player);

        int maxJump = this.maxJump.get(player) + nbJump;
        this.maxJump.put(player, maxJump);
    }
}
