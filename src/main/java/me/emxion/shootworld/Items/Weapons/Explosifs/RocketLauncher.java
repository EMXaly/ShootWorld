package me.emxion.shootworld.Items.Weapons.Explosifs;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class RocketLauncher extends Explosif {
    public RocketLauncher() {
        this.name = "RocketLauncher";
        this.material = Material.COAL;
        this.item = new ItemStack(this.material, 1);
        this.damage = 6;
        this.power = 2.5f;
        this.cooldown = 20;
        this.magazineSize = 4;
        this.reloadTime = 60;
        this.accuracy = 0.0225;
        this.projectileVelocity = 1.75;
        this.volume = 3.5f;
        this.pitch = 0.75f;
        this.hasGravity = false;

        this.setup();
    }
}
