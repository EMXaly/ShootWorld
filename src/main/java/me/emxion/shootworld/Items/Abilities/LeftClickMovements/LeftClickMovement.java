package me.emxion.shootworld.Items.Abilities.LeftClickMovements;

import me.emxion.shootworld.Items.Abilities.Abilities;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class LeftClickMovement implements Abilities {
    protected String name;
    protected Material material;
    protected ItemStack item;
    protected ItemMeta itemMeta;
    protected int customModelData = 1;
    protected int cooldown;

    @Override
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String getName() {
        return this.name;
    }
    @Override
    public Material getMaterial() {
        return this.material;
    }
    @Override
    public ItemStack getItem() {
        return this.item;
    }
    @Override
    public void setCooldown(int cooldown) {this.cooldown = cooldown;}

    public void setup() {
        this.itemMeta = this.item.getItemMeta();
        this.itemMeta.displayName(Component.text(this.name));
        this.itemMeta.setCustomModelData(this.customModelData);
        this.itemMeta.setUnbreakable(true);
        this.item.setItemMeta(this.itemMeta);
    }

    public abstract void leftClick(PlayerInteractEvent event);
}
