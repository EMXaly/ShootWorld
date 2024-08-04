package me.emxion.shootworld.Gamemodes;

import me.emxion.shootworld.Handlers.LoadoutHandlers;
import me.emxion.shootworld.Items.Abilities.Ability;
import me.emxion.shootworld.Items.Heals.Heal;
import me.emxion.shootworld.Items.LoadItems;
import me.emxion.shootworld.Items.Weapons.Weapon;
import me.emxion.shootworld.Loadouts.PlayerLoadout;
import me.emxion.shootworld.Loadouts.PlayersLoadouts;
import me.emxion.shootworld.ShootWorld;
import me.emxion.shootworld.Stats.Stats;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LunaticDeathMatch implements Gamemode {

    private final String name = "LunaticDeathMatch";
    LoadItems loadItems;
    LoadoutHandlers loadoutHandlers;
    PlayersLoadouts playersLoadouts;
    private final int lvlNeeded = 30;
    private final Stats stats = new Stats();

    private final float weaponDamage = 1.5f;
    private final double weaponReloadTime = 0.75;
    private final double abilityPower = 1.5;
    private final double abilityCooldown = 0.5;


    public LunaticDeathMatch(LoadItems loadItems, LoadoutHandlers loadoutHandlers) {
        this.loadItems = loadItems;
        this.loadoutHandlers = loadoutHandlers;
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

        this.playersLoadouts = this.loadoutHandlers.getPlayersLoadouts();

        for (Weapon weapon: this.loadItems.getWeapons()) {
            weapon.setDamage(weapon.getDamage() * this.weaponDamage);
            weapon.setReloadTime((int) (weapon.getReloadTime() * this.weaponReloadTime));
        }

        for (Ability ability: this.loadItems.getAbilities()) {
            ability.setPower(this.abilityPower);
            ability.setCooldown((int) (ability.getCooldown() * abilityCooldown));
        }


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
                        player.setGameMode(GameMode.ADVENTURE);
                        givePlayerLoadout(player);
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

    private void givePlayerLoadout(Player player) {
        Inventory playerInv = player.getInventory();
        playerInv.clear();
        PlayerLoadout playerLoadout = this.playersLoadouts.getPlayerLoadout(player);

        for (String weaponName: playerLoadout.getWeapons().values()) {
            if (weaponName.equals("null"))
                randomStuff(player, this.loadItems, 1, 0, 0);
            else
                for (Weapon weapon: this.loadItems.getWeapons())
                    if (weapon.getName().equals(weaponName))
                        playerInv.addItem(weapon.getItem());
        }

        for (String healName: playerLoadout.getHeals().values()) {
            if (healName.equals("null"))
                randomStuff(player, this.loadItems, 0, 1, 0);
            else
                for (Heal heal: this.loadItems.getHeals())
                    if (heal.getName().equals(healName))
                        playerInv.addItem(heal.getItem());
        }

        for (String abilityName: playerLoadout.getAbilities().values()) {
            if (abilityName.equals("null"))
                randomStuff(player, this.loadItems, 0, 0, 1);
            else
                for (Ability ability: this.loadItems.getAbilities())
                    if (ability.getName().equals(abilityName))
                        playerInv.addItem(ability.getItem());
        }
    }

    @Override
    public void onPlayerDeath(Player killer, Player killed) {
        if (killer != null && killer != killed) {
            killer.giveExpLevels(1);

            Bukkit.broadcastMessage(killer.getName() + " (LVL" + killer.getLevel() + ") a tué " + killed.getName() + " (LVL " + killed.getLevel() + ")");
        }

        this.stats.onPlayerDeath(killer, killed);
    }

    @Override
    public void onPlayerRespawn(Player player) {
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

        for (Weapon weapon: this.loadItems.getWeapons()) {
            weapon.setDamage(weapon.getDamage() / this.weaponDamage);
            weapon.setReloadTime((int) (weapon.getReloadTime() / this.weaponReloadTime));
        }

        for (Ability ability: this.loadItems.getAbilities()) {
            ability.setPower(1);
            ability.setCooldown((int) (ability.getCooldown() / abilityCooldown));
        }
    }
}
