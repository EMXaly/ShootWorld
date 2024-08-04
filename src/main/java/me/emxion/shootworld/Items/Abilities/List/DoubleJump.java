package me.emxion.shootworld.Items.Abilities.List;

import me.emxion.shootworld.Items.Abilities.Ability;
import me.emxion.shootworld.Items.Abilities.Interfaces.OnFlying;
import me.emxion.shootworld.Items.Abilities.Interfaces.OnLanding;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DoubleJump extends Ability implements OnFlying, OnLanding {
    protected HashMap<Player, Integer> maxJump = new HashMap<Player, Integer>();
    protected HashMap<Player, Integer> nbJumps = new HashMap<Player, Integer>();
    private HashMap<Player, Location> locations = new HashMap<>();

    private int power = 0;

    public DoubleJump() {
        this.name = "DoubleJump";
        this.material = Material.PISTON;
        this.item = new ItemStack(this.material, 1);
        this.cooldown = 999999999;

        this.setup();
    }

    @Override
    public void setPower(double power) {
        int i = 0;
        while (power >= 1.25) {
            i++;
            power -= 0.25;
        }

        this.power = i;

        for (Player player: Bukkit.getOnlinePlayers())
            this.addPlayer(player);
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
            Vector playerDirection = player.getLocation().getDirection().multiply(player.getVelocity().length());
            playerDirection.setY(0.4);
            player.setVelocity(playerDirection);

            nbJumps++;
            this.nbJumps.replace(player, nbJumps);

            if (nbJumps == maxJump)
                player.setCooldown(this.material, this.cooldown);

            //player.sendMessage("+1 jump");
            this.locations.put(player, player.getLocation());
        }
    }

    @Override
    public void OnLanding(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        this.nbJumps.replace(player, 0);
        player.setCooldown(this.material, 0);

        if (!this.locations.containsKey(player))
            return;

        double distance = player.getLocation().distance(this.locations.get(player));
        //player.sendMessage("distance parcouru DoubleJump : " + distance);
        this.locations.remove(player);
    }

    public void addPlayer(Player player) {
        this.maxJump.put(player, 2 + this.power);
        this.nbJumps.put(player, 0);
    }

    @Override
    public List<Ability> getIncompatibleAbilities() {
        List<Ability> incompatibleAbilities = new ArrayList<>();
        incompatibleAbilities.add(new Teleporter());
        return incompatibleAbilities;
    }
}
