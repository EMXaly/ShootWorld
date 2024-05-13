package me.emxion.shootworld.Items.Weapons.Firearms;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class LMG extends Firearm {
    public LMG() {
        this.name = "LMG";
        this.material = Material.IRON_INGOT;
        this.item = new ItemStack(this.material, 1);
        this.damage = 3.5f;
        this.fireRate = 4;
        this.nbProjectile = 1;
        this.hasGravity = true;
        this.magazineSize = 55;
        this.reloadTime = 90;
        this.accuracy = 0.075;
        this.projectileVelocity = 4.5;
        this.volume = 2f;
        this.pitch = 5f;

        this.setup();
    }
}
