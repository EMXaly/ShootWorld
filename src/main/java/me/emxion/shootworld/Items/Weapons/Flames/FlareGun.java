package me.emxion.shootworld.Items.Weapons.Flames;

import me.emxion.shootworld.ShootWorld;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class FlareGun extends Flames {
    public FlareGun() {
        this.name = "FlareGun";
        this.material = Material.BLAZE_ROD;
        this.item = new ItemStack(this.material, 1);
        this.damage = 4;
        this.fireRate = 7;
        this.nbProjectile = 1;
        this.hasGravity = true;
        this.magazineSize = 1;
        this.reloadTime = 25;
        this.accuracy = 0;
        this.projectileVelocity = 0.6;
        this.volume = 2f;
        this.pitch = 2.5f;
        this.timer = 28;
        this.fireTick = 60;

        this.setup();
    }

    @Override
    public void burning(ProjectileHitEvent event) {
        Block centerBlock = event.getHitBlock().getRelative(BlockFace.UP);
        List<Block> blocks = new ArrayList<>();
        if (centerBlock.getType() != Material.AIR)
            return;

        for(int x = -1; x <= 1; x++) {
            for(int z = -1; z <= 1; z++) {
                Block b = centerBlock.getRelative(x, 0, z);
                if(centerBlock.getLocation().distance(b.getLocation()) <= 1) {
                    blocks.add(b);
                }
            }
        }

        centerBlock.setType(Material.FIRE);
        for (Block block: blocks) {
            if (block.getType() == Material.AIR)
                block.setType(Material.FIRE);
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                centerBlock.setType(Material.AIR);
                for (Block block: blocks) {
                    if (block.getType() == Material.FIRE)
                        block.setType(Material.AIR);
                }
            }
        }.runTaskLater(ShootWorld.getPlugin(ShootWorld.class), this.fireTick);


    }
}
