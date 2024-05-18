package me.emxion.shootworld.Stats;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.emxion.shootworld.Gamemodes.Gamemode;
import me.emxion.shootworld.Items.LoadItems;
import me.emxion.shootworld.Items.Weapons.Weapon;
import me.emxion.shootworld.ShootWorld;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Stats {
    private GamesStats gamesStats;
    private LoadItems loadItems;
    public void onStart(Gamemode gm, List<Player> players, LoadItems loadItems) {
        this.gamesStats = new GamesStats(gm, players, loadItems);
        this.loadItems = loadItems;
    }

    public void onPlayerDeath(Player killer, Player killed) {
        if (killer != null)
            for (Weapon weapon: this.loadItems.getWeapons()) {
                if (killer.getItemInHand().getType() == weapon.getItem().getType()) {
                    WeaponStats weaponStats = this.gamesStats.getPlayersStats().get(killer.getUniqueId()).getWeaponStats().get(weapon.getName());
                    weaponStats.setKills(weaponStats.getKills() + 1);
                }
            }

        PlayersStats killedStats = this.gamesStats.getPlayersStats().get(killed.getUniqueId());
        killedStats.setDeaths(killedStats.getDeaths() + 1);
    }
    public void onEnd() throws IOException {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss-nn");

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        File file = new File(ShootWorld.getPlugin(ShootWorld.class).getDataFolder().getAbsolutePath() + "/" + localDateTime.format(dateTimeFormatter) + ".json");
        file.getParentFile().mkdir();
        file.createNewFile();
        Writer writer = new FileWriter(file, false);
        gson.toJson(this.gamesStats, writer);

        writer.flush();
        writer.close();
    }
}
