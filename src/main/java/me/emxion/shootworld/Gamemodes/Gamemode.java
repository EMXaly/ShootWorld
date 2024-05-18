package me.emxion.shootworld.Gamemodes;

import me.emxion.shootworld.Items.Abilities.Ability;
import me.emxion.shootworld.Items.LoadItems;
import me.emxion.shootworld.Items.Weapons.Weapon;
import me.emxion.shootworld.ShootWorld;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public interface Gamemode {
    public String getName();
    public void onStart(List<Player> players);
    public void onPlayerDeath(Player killer, Player killed);
    public void onPlayerRespawn(Player player);
    default void onPlayerGainLvl(Player player) {
        if (this.isWinning(player)) {
            Bukkit.broadcast(Component.text(player.getName() + " à gagné !"));
            List<Player> onlinePlayers = new ArrayList<>(Bukkit.getOnlinePlayers());
            this.onEnd(onlinePlayers);
            ShootWorld.getPlugin(ShootWorld.class).setGamemode(null);
        }
    }
    public boolean isWinning(Player player);
    public void onEnd(List<Player> players);

    default void randomStuff(Player player, LoadItems loadItems, int nbWeapons, int nbAbilities) {
        Inventory playerInv = player.getInventory();
        playerInv.clear();
        List<Weapon> listAvailableWeapons = new ArrayList<>(loadItems.getWeapons());
        List<Ability> listAvailableAbilities = new ArrayList<>(loadItems.getAbilities());

        for (int i = 0; i < nbWeapons; i++) {
            Random rand = new Random();
            int index = rand.nextInt(listAvailableWeapons.size());
            playerInv.addItem(listAvailableWeapons.get(index).getItem());
            listAvailableWeapons.get(index).addPlayerCurrentAmmo(player);
            listAvailableWeapons.remove(index);
        }

        for (int i = 0; i < nbAbilities; i++) {
            Random rand = new Random();
            int index = rand.nextInt(listAvailableAbilities.size());
            Ability pickedAbility = listAvailableAbilities.get(index);
            playerInv.addItem(pickedAbility.getItem());

            listAvailableAbilities.remove(index);

            if (pickedAbility.getIncompatibleAbilities() != null)
                for (Ability incompatibleAbility: pickedAbility.getIncompatibleAbilities())
                    listAvailableAbilities.remove(incompatibleAbility);
        }
    }
}
