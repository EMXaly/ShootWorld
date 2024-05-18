package me.emxion.shootworld.Items.Weapons.Launchers;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class GrenadeLauncher extends Launcher {
    public GrenadeLauncher() {
        this.name = "GrenadeLauncher";
        this.material = Material.CHARCOAL;
        this.item = new ItemStack(this.material, 1);
        this.damage = 8;
        this.power = 2f;
        this.fireRate = 25;
        this.magazineSize = 6;
        this.reloadTime = 55;
        this.accuracy = 0.025;
        this.projectileVelocity = 1.25;
        this.volume = 2.5f;
        this.pitch = 0.85f;
        this.nbProjectile = 1;
        this.hasGravity = true;

        this.setup();
    }
}
