package me.emxion.shootworld.Handlers;

import me.emxion.shootworld.Gamemodes.Gamemode;
import me.emxion.shootworld.ShootWorld;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class GamemodeHandlers implements Listener {
    private final ShootWorld plugin;

    public GamemodeHandlers(ShootWorld plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player killed = event.getPlayer();
        Player killer = killed.getKiller();
        Gamemode gm = plugin.getGamemode();

        if (gm != null)
            gm.onPlayerDeath(killer, killed);
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Gamemode gm = plugin.getGamemode();
        if (gm != null) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    gm.onPlayerRespawn(event.getPlayer());
                }
            }.runTaskLater(ShootWorld.getPlugin(ShootWorld.class), 1).getTaskId();
        }

    }

    @EventHandler
    public void onPlayerGainLVL(PlayerLevelChangeEvent event) {
        if (event.getNewLevel() > event.getOldLevel()) {
            Gamemode gm = plugin.getGamemode();
            Player player = event.getPlayer();
            gm.onPlayerGainLvl(player);
        }
    }

    @EventHandler
    public void onPlayerThrowItem(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }
}
