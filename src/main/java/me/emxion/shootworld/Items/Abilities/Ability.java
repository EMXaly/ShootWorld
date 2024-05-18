package me.emxion.shootworld.Items.Abilities;

import me.emxion.shootworld.Items.Item;
import me.emxion.shootworld.ShootWorld;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public abstract class Ability extends Item {
    protected int cooldown;
    protected Sound sound;
    protected float volume;
    protected float pitch;

    public int getCooldown() {
        return cooldown;
    }
    public void finishCooldown(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                player.playSound(player.getLocation(), sound, volume, pitch * 10);
            }
        }.runTaskLaterAsynchronously(ShootWorld.getPlugin(ShootWorld.class), this.cooldown).getTaskId();
    }

    public abstract List<Ability> getIncompatibleAbilities();
}
