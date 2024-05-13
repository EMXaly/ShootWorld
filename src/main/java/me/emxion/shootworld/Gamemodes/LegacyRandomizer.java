package me.emxion.shootworld.Gamemodes;

import me.emxion.shootworld.Items.LoadItems;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public class LegacyRandomizer implements Gamemode{
    private final String name = "LegacyRandomizer";
    LoadItems loadItems;
    private final int lvlNeeded = 30;
    private final int nbWeapons = 2;
    private final int nbAbilities = 4;
    private final float heal = 4f;
    public LegacyRandomizer(LoadItems loadItems) {
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
            this.randomStuff(player, this.loadItems, this.nbWeapons, this.nbAbilities);
            player.addPotionEffect(PotionEffectType.DAMAGE_RESISTANCE.createEffect(40, 1));
        }
    }

    @Override
    public void onPlayerDeath(Player killer, Player killed) {
        killer.giveExpLevels(1);
        if (!killer.isDead())
            killer.setHealth(Math.min(killer.getHealthScale(), killer.getHealth() + this.heal));
        Bukkit.broadcastMessage(killer.getName() + " (LVL" + killer.getLevel() + ") a tué " + killed.getName() + " (LVL " + killed.getLevel() + ")");
        //killer.giveExp(7 + killed.getLevel());
    }

    @Override
    public void onPlayerRespawn(Player player) {
        this.randomStuff(player, this.loadItems, this.nbWeapons, this.nbAbilities);
        player.addPotionEffect(PotionEffectType.DAMAGE_RESISTANCE.createEffect(40, 1));
        player.sendMessage("oui");
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
    }
}
