package me.emxion.shootworld.Items.Weapons.Melees;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Hoe implements Melee{
    private int damage = 4;
    private int minHPNeeded = 5;
    private double bonusDamage = 1.50;
    private String name;
    private Material material;
    private ItemStack item;
    private ItemMeta itemMeta;


    public Hoe() {
        this.name = "Finisher";
        this.material = Material.IRON_HOE;
        this.item = new ItemStack(this.material, 1);
        this.itemMeta = this.item.getItemMeta();
        this.itemMeta.displayName(Component.text(this.name));
        this.itemMeta.setUnbreakable(true);
        this.item.setItemMeta(itemMeta);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public ItemStack getItem() {
        return this.item;
    }

    @Override
    public void attacking(LivingEntity attacker, LivingEntity attacked) {
        double damageDealt = this.damage;
        if (attacked.getHealth() <= this.minHPNeeded)
            damageDealt *= this.bonusDamage;

        attacked.damage(damageDealt, attacker);
    }
}
