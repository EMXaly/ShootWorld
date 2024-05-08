package me.emxion.shootworld.Items.Weapons.Guns;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Shotgun extends Gun {
    public Shotgun() {
        this.name = "Shotgun";
        this.material = Material.LAPIS_LAZULI;
        this.item = new ItemStack(this.material, 1);
        this.damage = 1;
        this.cooldown = 15;
        this.bullets = 8;
        this.magazineSize = 6;
        this.reloadTime = 60;
        this.accuracy = 0.075;
        this.bulletVelocity = 4;
        this.volume = 3f;
        this.pitch = 2f;

        this.setup();
    }
}
