package me.emxion.shootworld.Items.Abilities.List;

import me.emxion.shootworld.Items.Abilities.Ability;
import me.emxion.shootworld.Items.Abilities.Interfaces.IOnLanding;
import me.emxion.shootworld.Items.Abilities.Interfaces.IOnLeftClick;
import me.emxion.shootworld.Items.Abilities.Interfaces.IOnMoving;
import me.emxion.shootworld.ShootWorld;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JumpPad extends Ability implements IOnLeftClick, IOnMoving, IOnLanding {
    private final int maxHeight = 5;
    private final float velocityGain = 2.25f;
    private final int destroyTime = 200;
    private final HashMap<Block, List<Player>> usedByPlayers = new HashMap<>();
    private final HashMap<Player, Location> locations = new HashMap<>();

    private double power = 1;

    public JumpPad() {
        this.name = "JumpPad";
        this.material = Material.WAXED_WEATHERED_CUT_COPPER_SLAB;
        this.item = new ItemStack(this.material, 1);
        this.cooldown = 100;
        this.sound = Sound.ITEM_ARMOR_EQUIP_ELYTRA;
        this.volume = 3f;
        this.pitch = 1f;

        this.setup();
    }

    @Override
    public void setPower(double power) {
        this.power = power;
    }

    @Override
    public void onLeftClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (player.getCooldown(this.material) > 0)
            return;

        if (event.getClickedBlock() == null)
            return;

        Block block = event.getClickedBlock().getRelative(event.getBlockFace());
        if (block.getType() != Material.AIR)
            return;

        block.setType(this.material);
        player.setCooldown(this.material, this.cooldown);
        this.usedByPlayers.put(block, new ArrayList<>());
        this.finishCooldown(player);
        //player.sendMessage("+1 placed jumppad");

        BukkitRunnable particleTask = new BukkitRunnable() {
            int tick = 0;
            @Override
            public void run() {
                if (tick > destroyTime) {
                    this.cancel();
                    return;
                }

                Location particleLocation = block.getLocation().add(0.5, 1, 0.5);
                block.getWorld().spawnParticle(Particle.WHITE_SMOKE, particleLocation, 2, 0.1, 2, 0.1, 0.01);

                tick++;
            }
        };
        particleTask.runTaskTimer(ShootWorld.getPlugin(ShootWorld.class), 0, 1);

        new BukkitRunnable() {
            @Override
            public void run() {
                block.setType(Material.AIR);
            }
        }.runTaskLater(ShootWorld.getPlugin(ShootWorld.class), this.destroyTime);
    }

    @Override
    public void onMoving(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        int i = 0;
        boolean findBlock = false;
        boolean findJumpPad = false;

        if (player.isOnGround()) {
            for (List<Player> players: this.usedByPlayers.values())
                players.remove(player);
        }

        Block block = player.getLocation().getBlock();
        while (i < this.maxHeight && !findBlock) {
            if (block.getType() != Material.AIR) {
                findBlock = true;
                if (block.getType() == this.material)
                    findJumpPad = true;
            }

            if (!findBlock)
                block = block.getRelative(BlockFace.DOWN);

            i++;
        }

        if (findJumpPad) {
            if (this.usedByPlayers.get(block) != null)
                if (this.usedByPlayers.get(block).contains(player))
                    return;


            @NotNull Vector velocity = player.getVelocity();
            player.setVelocity(velocity.setY(this.velocityGain).multiply(this.power));
            player.getWorld().playSound(player.getLocation(), this.sound, SoundCategory.PLAYERS, this.volume, this.pitch);
            this.usedByPlayers.get(block).add(player);
            //player.sendMessage("+1 used jumppad");
            this.locations.put(player, player.getLocation());
        }
    }

    @Override
    public void onLanding(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (!this.locations.containsKey(player))
            return;

        double distance = player.getLocation().distance(this.locations.get(player));
        //player.sendMessage("distance parcouru JumpPad : " + distance);
        this.locations.remove(player);
    }

    @Override
    public List<Ability> getIncompatibleAbilities() {
        List<Ability> incompatibleAbilities = new ArrayList<>();
        incompatibleAbilities.add(new AntiGravity());
        return incompatibleAbilities;
    }
}
