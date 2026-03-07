package me.emxion.shootworld.Items.Abilities.List;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import me.emxion.shootworld.Items.Abilities.Ability;
import me.emxion.shootworld.Items.Abilities.Interfaces.IOnJumping;
import me.emxion.shootworld.Items.Abilities.Interfaces.IOnLanding;
import me.emxion.shootworld.Items.Abilities.Interfaces.IOnSneaking;
import me.emxion.shootworld.ShootWorld;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Slide extends Ability implements IOnSneaking, IOnLanding, IOnJumping {
    private final HashMap<Player, Vector> playersVelocities = new HashMap<>();
    private final  Vector storeVelocityBoost = new Vector(1.5, 1, 1.5);
    private final Vector antiGravityBoost = new Vector(0.94, 0, 0.94);
    private final Vector slideBoost = new Vector(0.95, 1, 0.95);
    private final double minVelocityJump = 0.5;
    private final double jumpBoost = 0.5;
    private Vector power = new Vector(0, 0, 0);
    public Slide() {
        this.name = "Slide";
        this.material = Material.ICE;
        this.item = new ItemStack(this.material, 1);
        this.cooldown = 0;
        this.sound = null;
        this.volume = 1f;
        this.pitch = 1f;

        this.setup();
    }

    @Override
    public void setPower(double power) {
        return;

        /*double i = 0;
        while (power >= 1.25) {
            i += 0.005;
            power -= 0.25;
        }
        this.power = new Vector(i, i, i);*/
    }

    @Override
    public void onSneaking(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (!player.isOnGround() && player.hasGravity()) {
            Vector playerVelocity = player.getVelocity();
            this.playersVelocities.put(player, playerVelocity.multiply(this.storeVelocityBoost.add(this.power)));
        }

        // Antigravity combo
        if (!player.hasGravity()) {
            Vector playerVelocity = player.getVelocity();
            playerVelocity.multiply(this.antiGravityBoost.add(this.power));
            player.setVelocity(playerVelocity);
        }
    }

    @Override
    public void onLanding(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (player.isSneaking()) {
            if (this.playersVelocities.containsKey(player)) {
                Vector playerVelocity = this.playersVelocities.get(player).setY(player.getVelocity().getY());
                player.setVelocity(playerVelocity);
                this.playersVelocities.replace(player, player.getVelocity().multiply(this.slideBoost));
            }
        }
        else
            this.playersVelocities.remove(player);
    }

    @Override
    public void onJumping(PlayerJumpEvent event) {
        Player player = event.getPlayer();
        if (this.playersVelocities.containsKey(player)) {
            if (this.playersVelocities.get(player).length() > this.minVelocityJump)
                if (player.isSneaking()) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            Vector velocity = player.getVelocity();
                            player.setVelocity(velocity.setY(playersVelocities.get(player).length() * jumpBoost));
                            //player.sendMessage("" + playersVelocities.get(player).length());
                        }
                    }.runTaskLater(ShootWorld.getPlugin(ShootWorld.class), 1);
                }
        }
    }

    @Override
    public List<Ability> getIncompatibleAbilities() {
        List<Ability> incompatibleAbilities = new ArrayList<>();
        incompatibleAbilities.add(new Slam());
        return incompatibleAbilities;
    }
}
