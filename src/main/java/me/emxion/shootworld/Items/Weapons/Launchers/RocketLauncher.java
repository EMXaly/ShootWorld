package me.emxion.shootworld.Items.Weapons.Launchers;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class RocketLauncher extends Launcher {
    public RocketLauncher() {
        this.name = "RocketLauncher";
        this.material = Material.COAL;
        this.item = new ItemStack(this.material, 1);
        this.damage = 6;
        this.power = 2.5f;
        this.fireRate = 20;
        this.magazineSize = 4;
        this.reloadTime = 45;
        this.accuracy = 0.0225;
        this.projectileVelocity = 1.75;
        this.volume = 3.5f;
        this.pitch = 0.75f;
        this.nbProjectile = 1;
        this.hasGravity = false;

        this.setup();
    }
}
