package me.emxion.shootworld.Items.Abilities.List;

import me.emxion.shootworld.Items.Abilities.Ability;
import me.emxion.shootworld.Items.Abilities.Interfaces.OnFlying;
import me.emxion.shootworld.Items.Abilities.Interfaces.OnLanding;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.HashMap;

public class DoubleJump extends Ability implements OnFlying, OnLanding {
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
    public void OnFlying(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();

        if (player.getCooldown(this.material) > 0)
            return;

        if (this.maxJump.get(player) == null)
            this.addPlayer(player);

        int maxJump = this.maxJump.get(player);
        int nbJumps = this.nbJumps.get(player);

        if (nbJumps + 1 <= maxJump) {
            Vector playerDirection = player.getLocation().getDirection().multiply(0.35);
            playerDirection.setY(0.4);
            player.setVelocity(playerDirection);

            nbJumps++;
            this.nbJumps.replace(player, nbJumps);

            if (nbJumps == maxJump)
                player.setCooldown(this.material, this.cooldown);

            player.sendMessage("+1 jump");
        }
    }

    @Override
    public void OnLanding(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        this.nbJumps.replace(player, 0);
        player.setCooldown(this.material, 0);
    }

    public void addPlayer(Player player) {
        this.maxJump.put(player, 1);
        this.nbJumps.put(player, 0);
    }

}
