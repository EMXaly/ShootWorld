package me.emxion.shootworld.Gamemodes;

import org.bukkit.entity.Player;

import java.util.List;

public interface Gamemode {
    public void onStart(List<Player> players);
    public void onPlayerDeath(Player killer, Player killed);
    public void onPlayerRespawn(Player player);
    public boolean isWinning(Player player);
    public void onEnd(List<Player> players);
}
