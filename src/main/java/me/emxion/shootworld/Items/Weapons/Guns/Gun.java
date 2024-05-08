package me.emxion.shootworld.Items.Weapons.Guns;

import me.emxion.shootworld.Items.Weapons.Weapon;
import me.emxion.shootworld.ShootWorld;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.HashMap;

public abstract class Gun implements Weapon {
    protected String name;
    protected Material material;
    protected ItemStack item;
    protected ItemMeta itemMeta;
    protected float damage;
    protected int cooldown;
    protected int bullets;
    protected int magazineSize;
    protected HashMap<Player, Integer> curentAmmo = new HashMap<Player, Integer>();
    protected int reloadTime;
    protected double accuracy;
    protected double bulletVelocity;
    protected float volume;
    protected float pitch;
    protected HashMap<Player, Integer> reloads = new HashMap<>();

    protected void setup() {
        this.itemMeta = this.item.getItemMeta();
        this.itemMeta.displayName(Component.text(this.name));
        this.itemMeta.setUnbreakable(true);
        this.item.setItemMeta(this.itemMeta);
    }
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
        this.setup();
        return this.item;
    }

    public float getDamage() {
        return this.damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }
    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }
    public void setBullets(int bullets) {
        this.bullets = bullets;
    }
    public void setMagazineSize(int magazineSize) {
        this.magazineSize = magazineSize;
    }
    public void setReloadTime(int reloadTime) {
        this.reloadTime = reloadTime;
    }
    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }
    public void setBulletVelocity(double bulletVelocity) {
        this.bulletVelocity = bulletVelocity;
    }


    @Override
    public void attacking(PlayerInteractEvent event) {
        if (!event.getAction().isRightClick())
            return;

        Player player = event.getPlayer();

        if (player.getCooldown(this.material) > 0)
            return;

        if (this.curentAmmo.get(player) == null)
            this.addPlayer(player);

        for (int i = 0; i < this.bullets; i++) {
            @NotNull Vector playerLocation = player.getEyeLocation().getDirection();
            Arrow arrow = player.launchProjectile(org.bukkit.entity.Arrow.class);
            playerLocation.add(new Vector(this.getRandomNumber(this.accuracy),this.getRandomNumber(this.accuracy),this.getRandomNumber(this.accuracy)));
            arrow.setVelocity(playerLocation.multiply(this.bulletVelocity));

            arrow.setCustomName(this.name);
            arrow.setGravity(false);
            arrow.setPickupStatus(AbstractArrow.PickupStatus.DISALLOWED);
        }

        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS,this.volume, this.pitch);
        player.setCooldown(this.material, this.cooldown);

        this.curentAmmo.replace(player, this.curentAmmo.get(player)-1);
        this.gunChangeAmmo(player);

        if (this.curentAmmo.get(player) <= 0)
            this.reloading(player, true);
    }

    private double getRandomNumber(double accuracy) {
        return (Math.random() * (accuracy - (-accuracy))) - accuracy;
    }

    protected void gunChangeAmmo(Player player) {
        ItemStack playerItem = null;
        for(ItemStack i : player.getInventory().getContents()) {
            if (i != null) {
                if (i.getType() == this.material) {
                    playerItem = i;
                    break;
                }
            }
        }
        if (playerItem != null) {
            ItemMeta playerItemMeta = playerItem.getItemMeta();
            playerItemMeta.displayName(Component.text(String.format("%s (%d/%d)", this.name, this.curentAmmo.get(player), this.magazineSize)));
            playerItem.setItemMeta(playerItemMeta);
        }
    }

    public void reloading(Player player, boolean reload) {
        if (this.curentAmmo.get(player) == null)
            return;

        if (reload) {
            if (this.curentAmmo.get(player) == this.magazineSize)
                return;
            if (this.reloads.get(player) != null)
                return;

            player.setCooldown(this.material, this.reloadTime);

            int reloading = new BukkitRunnable() {
                @Override
                public void run() {
                    curentAmmo.replace(player, magazineSize);
                    gunChangeAmmo(player);
                    reloads.remove(player);
                }
            }.runTaskLater(ShootWorld.getPlugin(ShootWorld.class), this.reloadTime).getTaskId();
            this.reloads.put(player, reloading);
        }
        else {
            if (this.curentAmmo.get(player) <= 0)
                return;
            if (this.reloads.get(player) == null)
                return;

            player.setCooldown(this.material, 0);
            Bukkit.getServer().getScheduler().cancelTask(this.reloads.get(player));
            this.reloads.remove(player);
        }
    }

    public void addPlayer(Player player) {
        this.curentAmmo.put(player, this.magazineSize);
    }
}
