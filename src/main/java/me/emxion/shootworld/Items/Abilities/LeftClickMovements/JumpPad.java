package me.emxion.shootworld.Items.Abilities.LeftClickMovements;

import me.emxion.shootworld.ShootWorld;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public class JumpPad extends LeftClickMovement {
    private final int maxHeight = 5;
    private final int destroyTime = 200;
    public JumpPad() {
        this.name = "JumpPad";
        this.material = Material.WAXED_WEATHERED_CUT_COPPER_SLAB;
        this.item = new ItemStack(this.material, 1);
        this.cooldown = 100;

        this.setup();
    }

    @Override
    public void leftClick(PlayerInteractEvent event) {
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

        new BukkitRunnable() {
            @Override
            public void run() {
                block.setType(Material.AIR);
            }
        }.runTaskLater(ShootWorld.getPlugin(ShootWorld.class), this.destroyTime);
    }
    @Override
    public void movement(Player player) {
        int i = 0;
        boolean findBlock = false;
        boolean findJumpPad = false;

        Block block = player.getLocation().getBlock();
        while (i < this.maxHeight && !findBlock) {
            if (block.getType() != Material.AIR) {
                findBlock = true;
                if (block.getType() == this.material)
                    findJumpPad = true;
            }

            block = block.getRelative(BlockFace.DOWN);
            i++;
        }
        if (findJumpPad) {
            @NotNull Vector velocity = player.getVelocity();
            player.setVelocity(velocity.setY(1));
        }
    }
}
