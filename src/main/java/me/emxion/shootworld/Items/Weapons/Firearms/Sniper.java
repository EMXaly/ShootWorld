package me.emxion.shootworld.Items.Weapons.Firearms;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Sniper extends Firearm {
    public Sniper() {
        this.name = "Sniper";
        this.material = Material.EMERALD;
        this.item = new ItemStack(this.material, 1);
        this.damage = 10;
        this.fireRate = 25;
        this.nbProjectile = 1;
        this.hasGravity = false;
        this.magazineSize = 5;
        this.reloadTime = 40;
        this.accuracy = 0;
        this.projectileVelocity = 6;
        this.volume = 4f;
        this.pitch = 2f;

        this.setup();
    }
}
