package me.emxion.shootworld.Gamemodes;

import me.emxion.shootworld.Items.Abilities.Abilities;
import me.emxion.shootworld.Items.Weapons.Melees.Melee;
import me.emxion.shootworld.Items.Weapons.Weapon;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public class LegacyRandomStart implements Gamemode {
    private final List<Weapon> weapons;
    private final List<Melee> melees;
    private final List<Abilities> abilities;
    private final int lvlNeeded = 20;
    private final int nbWeapons = 2;
    private final int nbMelee = 1;
    private final int nbAbilities = 4;
    private final float heal = 4f;
    public LegacyRandomStart(List<Weapon> weapons, List<Melee> melees, List<Abilities> abilities) {
        this.weapons = weapons;
        this.melees = melees;
        this.abilities = abilities;
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
            this.randomStuff(player);
            player.addPotionEffect(PotionEffectType.DAMAGE_RESISTANCE.createEffect(40, 1));
        }
    }

    @Override
    public void onPlayerDeath(Player killer, Player killed) {
        killer.giveExpLevels(1);
        if (!killer.isDead())
            killer.setHealth(Math.min(killer.getHealthScale(), killer.getHealth() + this.heal));

        Bukkit.broadcastMessage(killer.getName() + " (LVL" + killer.getLevel() + ") a tué " + killed.getName() + " (LVL " + killed.getLevel() + ")");
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
    }

    private void randomStuff(Player player) {
        Inventory playerInv = player.getInventory();
        playerInv.clear();
        List<Weapon> listAvailableWeapons = new ArrayList<>(this.weapons);
        List<Melee> listAvailableMelees = new ArrayList<>(this.melees);
        List<Abilities> listAvailableAbilities = new ArrayList<>(this.abilities);

        for (int i = 0; i < this.nbWeapons; i++) {
            Random rand = new Random();
            int index = rand.nextInt(listAvailableWeapons.size());
            playerInv.addItem(listAvailableWeapons.get(index).getItem());
            listAvailableWeapons.get(index).addPlayer(player);
            listAvailableWeapons.remove(index);
        }

        for (int i = 0; i < this.nbMelee; i++) {
            Random rand = new Random();
            int index = rand.nextInt(listAvailableMelees.size());
            playerInv.addItem(listAvailableMelees.get(index).getItem());
            listAvailableMelees.remove(index);
        }

        for (int i = 0; i < this.nbAbilities; i++) {
            Random rand = new Random();
            int index = rand.nextInt(listAvailableAbilities.size());
            playerInv.addItem(listAvailableAbilities.get(index).getItem());
            listAvailableAbilities.remove(index);
        }
    }
}
