package me.emxion.shootworld.Loadouts;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.emxion.shootworld.ShootWorld;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class PlayersLoadouts {
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private HashMap<Player, PlayerLoadout> playersLoadouts = new HashMap<>();

    public PlayersLoadouts() {
    }

    public PlayerLoadout getPlayerLoadout(Player player) {
        return this.playersLoadouts.get(player);
    }

    public void onPlayerJoin(Player player) throws IOException {
        this.loadPlayerLoadout(player);
    }
    // + quand une partie commence (reload confirm)

    private void createNewPlayerLoadout(Player player) throws IOException {
        FileWriter writer = new FileWriter(ShootWorld.getPlugin(ShootWorld.class).getDataFolder().getAbsolutePath() + "/playersLoadouts/" + player.getUniqueId() + ".json");
        PlayerLoadout playerLoadout = new PlayerLoadout(this.createNullMap("weapon", 2), this.createNullMap("heal", 1), this.createNullMap("ability", 4));
        this.playersLoadouts.put(player, playerLoadout);
        gson.toJson(this.playersLoadouts.get(player), writer);
        writer.close();
    }

    private Map<String, String> createNullMap(String name, int nb) {
        LinkedHashMap<String, String> hashMap = new LinkedHashMap<>();
        for (int i = 1; i <= nb; i++)
            hashMap.put(name+i, "null");


        return hashMap;
    }

    private void loadPlayerLoadout(Player player) throws IOException {
        try {
            FileReader reader = new FileReader(ShootWorld.getPlugin(ShootWorld.class).getDataFolder().getAbsolutePath() + "/playersLoadouts/" + player.getUniqueId() + ".json");
            this.playersLoadouts.put(player, gson.fromJson(reader, PlayerLoadout.class));
            reader.close();
        } catch (IOException exception) {
            this.createNewPlayerLoadout(player);
        }
    }

    public void changeWeapon(Player player, int slot, String weapon) {
        try {
            if (!this.playersLoadouts.get(player).addWeapon(slot, weapon)) {
                player.sendMessage("arme déjà sélectionnée");
                return;
            }
            player.sendMessage("vous avez selectionnez : " + weapon + " comme arme n°" + slot);

            FileWriter writer = new FileWriter(ShootWorld.getPlugin(ShootWorld.class).getDataFolder().getAbsolutePath() + "/playersLoadouts/" + player.getUniqueId() + ".json");
            gson.toJson(this.playersLoadouts.get(player), writer);
            writer.close();
        } catch (IOException e) {
            return;
        }
    }

    public void changeHeal(Player player, int slot, String heal) {
        try {
            this.playersLoadouts.get(player).addHeal(slot, heal);

            FileWriter writer = new FileWriter(ShootWorld.getPlugin(ShootWorld.class).getDataFolder().getAbsolutePath() + "/playersLoadouts/" + player.getUniqueId() + ".json");
            gson.toJson(this.playersLoadouts.get(player), writer);
            writer.close();
        } catch (IOException e) {
            return;
        }
    }

    public void changeAbility(Player player, int slot, String abilty) {
        try {
            if (!this.playersLoadouts.get(player).addAbility(slot, abilty)) {
                player.sendMessage("capacité déjà sélectionnée/incompatible");
                return;
            }
            player.sendMessage("vous avez selectionnez : " + abilty + " comme capacité n°" + slot);

            FileWriter writer = new FileWriter(ShootWorld.getPlugin(ShootWorld.class).getDataFolder().getAbsolutePath() + "/playersLoadouts/" + player.getUniqueId() + ".json");
            gson.toJson(this.playersLoadouts.get(player), writer);
            writer.close();
        } catch (IOException e) {
            return;
        }
    }
}
