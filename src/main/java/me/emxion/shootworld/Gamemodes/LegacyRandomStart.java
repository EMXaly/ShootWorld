package me.emxion.shootworld.Gamemodes;

import me.emxion.shootworld.Items.LoadItems;
import me.emxion.shootworld.Stats.Stats;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.io.IOException;
import java.util.*;

public class LegacyRandomStart implements Gamemode {
    private final String name = "LegacyRandomStart";
    LoadItems loadItems;
    private final int lvlNeeded = 20;
    private final int nbWeapons = 2;
    private final int nbHeals = 1;
    private final int nbAbilities = 4;
    private final float heal = 4f;
    private final Stats stats = new Stats();
    public LegacyRandomStart(LoadItems loadItems) {
        this.loadItems = loadItems;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void onStart(List<Player> players) {
        World world = Bukkit.getServer().getWorld("World");
        world.setGameRule(GameRule.FALL_DAMAGE, false);
        world.setGameRule(GameRule.NATURAL_REGENERATION, false);
        world.setDifficulty(Difficulty.PEACEFUL);


        for (Player player: players) {
            player.setHealth(20);
            player.setFoodLevel(20);
            player.setExp(0);
            player.setLevel(0);
            player.setGameMode(GameMode.ADVENTURE);
            this.randomStuff(player, this.loadItems, this.nbWeapons, this.nbHeals, this.nbAbilities);
            player.addPotionEffect(PotionEffectType.DAMAGE_RESISTANCE.createEffect(40, 1));
        }

        this.stats.onStart(this, players, this.loadItems);
    }

    @Override
    public void onPlayerDeath(Player killer, Player killed) {
        if (killer != null && killer != killed) {
            killer.giveExpLevels(1);

            /*if (!killer.isDead())
                killer.setHealth(Math.min(killer.getHealthScale(), killer.getHealth() + this.heal));*/

            Bukkit.broadcastMessage(killer.getName() + " (LVL" + killer.getLevel() + ") a tué " + killed.getName() + " (LVL " + killed.getLevel() + ")");
        }

        this.stats.onPlayerDeath(killer, killed);
    }

    @Override
    public void onPlayerRespawn(Player player) {
        player.addPotionEffect(PotionEffectType.DAMAGE_RESISTANCE.createEffect(40, 1));
    }

    @Override
    public boolean isWinning(Player player) {
        return player.getLevel() >= this.lvlNeeded;
    }

    @Override
    public void onEnd(List<Player> players) {
        int i = 1;
        Collections.sort(players, Comparator.comparingInt(Player::getLevel).reversed());
        for (Player player: players) {
            player.setGameMode(GameMode.SPECTATOR);
            player.getInventory().clear();
            Bukkit.broadcastMessage(i + ". " + player.getName() + " (LVL " + player.getLevel() + ")");
            i++;
        }

        try {
            this.stats.onEnd();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
