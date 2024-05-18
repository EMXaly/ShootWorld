package me.emxion.shootworld.Stats;

import me.emxion.shootworld.Gamemodes.Gamemode;
import me.emxion.shootworld.Items.LoadItems;
import org.bukkit.entity.Player;

import java.util.*;

public class GamesStats {
    private final String date;
    private final String gamemode;
    private HashMap<UUID,PlayersStats> playersStats = new HashMap<>();
    public GamesStats(Gamemode gm, List<Player> players, LoadItems loadItems) {
        this.date = new Date().toString();
        this.gamemode = gm.getName();

        for (Player player: players)
            this.playersStats.put(player.getUniqueId(), new PlayersStats(player, loadItems));
    }

    public String getGamemode() {
        return gamemode;
    }

    public String getDate() {
        return date;
    }

    public HashMap<UUID, PlayersStats> getPlayersStats() {
        return playersStats;
    }
}
