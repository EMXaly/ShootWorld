package me.emxion.shootworld.Items.Weapons.Guns;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class AR extends Gun{
    public AR() {
        this.name = "AR";
        this.material = Material.NETHERITE_INGOT;
        this.item = new ItemStack(this.material, 1);
        this.damage = 6;
        this.cooldown = 7;
        this.bullets = 1;
        this.magazineSize = 20;
        this.reloadTime = 45;
        this.accuracy = 0.015;
        this.bulletVelocity = 4.5;
        this.volume = 2f;
        this.pitch = 5f;

        this.setup();
    }
}
