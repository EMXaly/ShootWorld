package me.emxion.shootworld.Items.Abilities.FlyMovements;

import me.emxion.shootworld.Items.Abilities.Abilities;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class FlyMovement implements Abilities {
    protected String name;
    protected Material material;
    protected ItemStack item;
    protected ItemMeta itemMeta;
    protected int customModelData = 1;
    protected int cooldown;

    protected void setup() {
        this.itemMeta = this.item.getItemMeta();
        this.itemMeta.displayName(Component.text(this.name));
        this.itemMeta.setUnbreakable(true);
        this.itemMeta.setCustomModelData(this.customModelData);
        this.item.setItemMeta(this.itemMeta);
    }

    public abstract void landing(Player player);

    @Override
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String getName() {
        return this.name;
    }
    public Material getMaterial() {
        return this.material;
    }
    @Override
    public ItemStack getItem() {
        return this.item;
    }
    @Override
    public void setCooldown(int cooldown) {this.cooldown = cooldown;}
}
