package me.emxion.shootworld.Items.Weapons.Firearms;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class AR extends Firearm {
    public AR() {
        this.name = "AR";
        this.material = Material.NETHERITE_INGOT;
        this.item = new ItemStack(this.material, 1);
        this.damage = 6;
        this.fireRate = 7;
        this.nbProjectile = 1;
        this.hasGravity = false;
        this.magazineSize = 20;
        this.reloadTime = 45;
        this.accuracy = 0.015;
        this.projectileVelocity = 4.5;
        this.volume = 2f;
        this.pitch = 5f;

        this.setup();
    }
}
