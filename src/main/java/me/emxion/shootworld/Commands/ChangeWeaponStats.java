package me.emxion.shootworld.Commands;

import me.emxion.shootworld.Items.Weapons.Explosifs.Explosif;
import me.emxion.shootworld.Items.Weapons.Guns.Gun;
import me.emxion.shootworld.Items.Weapons.Weapon;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ChangeWeaponStats implements CommandExecutor {
    private final List<Weapon> weapons;
    public ChangeWeaponStats(List<Weapon> weapons) {
        this.weapons = weapons;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String [] args) {
        if (args.length == 3) {
            Weapon w = searchWeapon(args[0]);
            if (w == null)
                return false;
            if (w instanceof Gun) {
                Gun g = (Gun) w;
                this.applyGunNewStats(g, args[1], args[2]);
                return true;
            }
            else if (w instanceof Explosif) {
                Explosif e = (Explosif) w;
                this.applyExplosiveNewStats(e, args[1], args[2]);
                return true;
            }
        }

        return false;
    }

    private Weapon searchWeapon(String name) {
        for (Weapon weapon : weapons)
            if (name.equals(weapon.getName()))
                return weapon;

        return null;
    }

    private void applyGunNewStats(Gun g, String stats, String value) {
        switch (stats) {
            case "damage":
                g.setDamage(Float.parseFloat(value));
                break;
            case "cooldown":
                g.setCooldown(Integer.parseInt(value));
                break;
            case "bullets":
                g.setBullets(Integer.parseInt(value));
                break;
            case "magazineSize":
                g.setMagazineSize(Integer.parseInt(value));
                break;
            case "reloadTime":
                g.setReloadTime(Integer.parseInt(value));
                break;
            case "accuracy":
                g.setAccuracy(Double.parseDouble(value));
                break;
            case "bulletVelocity":
                g.setBulletVelocity(Double.parseDouble(value));
                break;
        }
    }

    private void applyExplosiveNewStats(Explosif e, String stats, String value) {
        switch (stats) {
            case "damage":
                e.setDamage(Integer.parseInt(value));
                break;
            case "power":
                e.setPower(Integer.parseInt(value));
                break;
            case "cooldown":
                e.setCooldown(Integer.parseInt(value));
                break;
            case "magazineSize":
                e.setMagazineSize(Integer.parseInt(value));
                break;
            case "reloadTime":
                e.setReloadTime(Integer.parseInt(value));
                break;
            case "accuracy":
                e.setAccuracy(Double.parseDouble(value));
                break;
            case "projectileVelocity":
                e.setProjectileVelocity(Double.parseDouble(value));
                break;
            case "hasGravity":
                e.setHasGravity(Boolean.parseBoolean(value));
        }
    }
}
