package me.emxion.shootworld.Items.Weapons.Launchers;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Shrapnel extends Launcher {
    public Shrapnel() {
        this.name = "Shrapnel";
        this.material = Material.PRISMARINE_CRYSTALS;
        this.item = new ItemStack(this.material, 1);
        this.damage = 2;
        this.power = 1f;
        this.fireRate = 30;
        this.magazineSize = 1;
        this.reloadTime = 50;
        this.accuracy = 0.075;
        this.projectileVelocity = 1.25;
        this.volume = 2.5f;
        this.pitch = 0.85f;
        this.nbProjectile = 4;
        this.hasGravity = true;

        this.setup();
    }
}
