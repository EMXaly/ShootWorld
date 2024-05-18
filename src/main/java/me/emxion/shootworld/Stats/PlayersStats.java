package me.emxion.shootworld.Stats;

import me.emxion.shootworld.Items.Abilities.Ability;
import me.emxion.shootworld.Items.LoadItems;
import me.emxion.shootworld.Items.Weapons.Weapon;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class PlayersStats {
    private final String name;
    private int deaths;
    private float damageTaken;
    private HashMap<String, WeaponStats> weaponStats = new HashMap<>();
    private HashMap<String, AbilityStats> abilityStats = new HashMap<>();

    public PlayersStats(Player player, LoadItems loadItems) {
        this.name = player.getName();
        this.deaths = 0;
        this.damageTaken = 0;

        for (Weapon weapon: loadItems.getWeapons())
            this.weaponStats.put(weapon.getName(), new WeaponStats());

        for (Ability ability: loadItems.getAbilities())
            this.abilityStats.put(ability.getName(), new AbilityStats());
    }

    public String getName() {
        return name;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public float getDamageTaken() {
        return damageTaken;
    }

    public void setDamageTaken(float damageTaken) {
        this.damageTaken = damageTaken;
    }

    public HashMap<String, WeaponStats> getWeaponStats() {
        return weaponStats;
    }

    public void setWeaponStats(HashMap<String, WeaponStats> weaponStats) {
        this.weaponStats = weaponStats;
    }

    public HashMap<String, AbilityStats> getAbilityStats() {
        return abilityStats;
    }

    public void setAbilityStats(HashMap<String, AbilityStats> abilityStats) {
        this.abilityStats = abilityStats;
    }
}
