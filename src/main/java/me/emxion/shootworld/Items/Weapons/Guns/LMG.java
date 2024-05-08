package me.emxion.shootworld.Items.Weapons.Guns;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class LMG extends Gun {
    public LMG() {
        this.name = "LMG";
        this.material = Material.IRON_INGOT;
        this.item = new ItemStack(this.material, 1);
        this.damage = 3.5f;
        this.cooldown = 4;
        this.bullets = 1;
        this.magazineSize = 55;
        this.reloadTime = 100;
        this.accuracy = 0.075;
        this.bulletVelocity = 4.5;
        this.volume = 2f;
        this.pitch = 5f;

        this.setup();
    }
}
