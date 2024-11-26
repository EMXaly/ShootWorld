package me.emxion.shootworld.Gamemodes;

import me.emxion.shootworld.Items.LoadItems;
import me.emxion.shootworld.ShootWorld;
import me.emxion.shootworld.Stats.Stats;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.time.Duration;
import java.util.*;

public class Randomizer implements Gamemode{
    private final String name = "Randomizer";
    LoadItems loadItems;
    private final int lvlNeeded = 30;
    private final int nbWeapons = 2;
    private final int nbHeals = 1;
    private final int nbAbilities = 4;

    private final Stats stats = new Stats();
    public Randomizer(LoadItems loadItems) {
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

        BukkitRunnable visualTask = new BukkitRunnable() {
            int i = 3;
            @Override
            public void run() {

                for (Player player: players) {
                    if (i > 0)
                        player.showTitle(Title.title(Component.text(i), Component.empty()));
                    else {
                        player.showTitle(Title.title(Component.text("Go!"), Component.empty(), Title.Times.times(Duration.ZERO, Duration.ofMillis(500), Duration.ofMillis(100))));


                        player.setHealth(20);
                        player.setFoodLevel(20);
                        player.setExp(0);
                        player.setLevel(0);
                        player.getInventory().clear();
                        player.setGameMode(GameMode.ADVENTURE);
                        randomStuff(player, loadItems, nbWeapons, nbHeals, nbAbilities);
                        player.addPotionEffect(PotionEffectType.DAMAGE_RESISTANCE.createEffect(40, 1));

                        player.setPlayerListName(player.getName() + " [Lvl: " + 0 + "]");
                    }

                }
                i--;

                if (i < 0) {
                    this.cancel();
                    return;
                }
            }
        };
        visualTask.runTaskTimer(ShootWorld.getPlugin(ShootWorld.class), 0, 20);

        this.stats.onStart(this, players, this.loadItems);
    }

    @Override
    public void onPlayerDeath(Player killer, Player killed) {
        if (killer != null && killer != killed) {
            killer.giveExpLevels(1); //killer.giveExp(7 + killed.getLevel());

            Bukkit.broadcastMessage(killer.getName() + " (LVL" + killer.getLevel() + ") a tué " + killed.getName() + " (LVL " + killed.getLevel() + ")");
        }

        this.stats.onPlayerDeath(killer, killed);
    }

    @Override
    public void onPlayerRespawn(Player player) {
        player.getInventory().clear();
        this.randomStuff(player, this.loadItems, this.nbWeapons, this.nbHeals, this.nbAbilities);
        player.addPotionEffect(PotionEffectType.DAMAGE_RESISTANCE.createEffect(60, 2));
    }

    @Override
    public boolean isWinning(Player player) {
        return player.getLevel() >= this.lvlNeeded;
    }

    @Override
    public void onEnd(List<Player> players) {
        try {
            int i = 1;
            players.sort(Comparator.comparingInt(Player::getLevel).reversed());
            for (Player player: players) {
                player.setGameMode(GameMode.SPECTATOR);
                player.getInventory().clear();
                Bukkit.broadcastMessage(i + ". " + player.getName() + " (LVL " + player.getLevel() + ")");

                if (i == 1)
                    player.showTitle(Title.title(Component.text("1er"), Component.empty(), Title.Times.times(Duration.ZERO, Duration.ofMillis(1500), Duration.ofMillis(500))));
                else
                    player.showTitle(Title.title(Component.text(i + "ème"), Component.empty(), Title.Times.times(Duration.ZERO, Duration.ofMillis(1500), Duration.ofMillis(500))));

                player.setPlayerListName(player.getName());

                i++;
            }
        } catch (Exception ignored) {

        }

        try {
            this.stats.onEnd();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
