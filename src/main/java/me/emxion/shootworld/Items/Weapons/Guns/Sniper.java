package me.emxion.shootworld.Items.Weapons.Guns;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Sniper extends Gun{
    public Sniper() {
        this.name = "Sniper";
        this.material = Material.EMERALD;
        this.item = new ItemStack(this.material, 1);
        this.damage = 10;
        this.cooldown = 20;
        this.bullets = 1;
        this.magazineSize = 5;
        this.reloadTime = 50;
        this.accuracy = 0;
        this.bulletVelocity = 5;
        this.volume = 4f;
        this.pitch = 2f;

        this.setup();
    }
}
