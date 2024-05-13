package me.emxion.shootworld.Items.Weapons.Firearms;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class SMG extends Firearm {
    public SMG() {
        this.name = "SMG";
        this.material = Material.GOLD_INGOT;
        this.item = new ItemStack(this.material, 1);
        this.damage = 3;
        this.fireRate = 3;
        this.nbProjectile = 1;
        this.hasGravity = false;
        this.magazineSize = 17;
        this.reloadTime = 30;
        this.accuracy = 0.03;
        this.projectileVelocity = 4;
        this.volume = 1f;
        this.pitch = 10f;

        this.setup();
    }
}
