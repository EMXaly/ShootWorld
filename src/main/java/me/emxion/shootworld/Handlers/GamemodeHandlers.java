package me.emxion.shootworld.Handlers;

import me.emxion.shootworld.Gamemodes.Gamemode;
import me.emxion.shootworld.ShootWorld;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
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

        if (killer != null && killer != killed)
            if (gm != null) {
                gm.onPlayerDeath(killer, killed);
                if (gm.isWinning(killer)) {
                    Bukkit.broadcast(Component.text(killer.getName() + " à gagné !"));
                    List<Player> onlinePlayers = new ArrayList<>(Bukkit.getOnlinePlayers());
                    gm.onEnd(onlinePlayers);
                    this.plugin.setGamemode(null);
                }
            }
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
}
