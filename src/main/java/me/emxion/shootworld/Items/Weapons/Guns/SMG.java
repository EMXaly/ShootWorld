package me.emxion.shootworld.Items.Weapons.Guns;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SMG extends Gun{
    public SMG() {
        this.name = "SMG";
        this.material = Material.GOLD_INGOT;
        this.item = new ItemStack(this.material, 1);
        this.damage = 3;
        this.cooldown = 3;
        this.bullets = 1;
        this.magazineSize = 17;
        this.reloadTime = 30;
        this.accuracy = 0.03;
        this.bulletVelocity = 4;
        this.volume = 1f;
        this.pitch = 10f;

        this.setup();
    }
}
