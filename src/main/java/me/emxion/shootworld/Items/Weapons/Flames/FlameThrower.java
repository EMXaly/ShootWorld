package me.emxion.shootworld.Items.Weapons.Flames;

import org.bukkit.Material;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;

public class FlameThrower extends Flames{
    public FlameThrower() {
        this.name = "FlameThrower";
        this.material = Material.BLAZE_POWDER;
        this.item = new ItemStack(this.material, 1);
        this.damage = 1;
        this.fireRate = 3;
        this.nbProjectile = 4;
        this.hasGravity = false;
        this.magazineSize = 20;
        this.reloadTime = 45;
        this.accuracy = 0.33;
        this.projectileVelocity = 0.75;
        this.volume = 2f;
        this.pitch = 2f;
        this.timer = 10;
        this.fireTick = 8;

        this.setup();
    }

    @Override
    public void burning(ProjectileHitEvent event) {
        return;
    }
}
