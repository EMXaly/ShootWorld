package me.emxion.shootworld.Loadouts;

import me.emxion.shootworld.Items.Abilities.Ability;
import me.emxion.shootworld.Items.LoadItems;

import java.util.List;
import java.util.Map;

public class PlayerLoadout {
    private Map<String, String> weapons;
    private Map<String, String> heals;
    private Map<String, String> abilities;

    public PlayerLoadout(Map<String, String> weapons, Map<String, String> heals, Map<String, String> abilities) {
        this.weapons = weapons;
        this.heals = heals;
        this.abilities = abilities;
    }

    public Map<String, String> getWeapons() {
        return weapons;
    }

    public Map<String, String> getHeals() {
        return heals;
    }

    public Map<String, String> getAbilities() {
        return abilities;
    }

    public boolean addWeapon(int slot, String weapon) {
        for (String weaponName: this.weapons.values())
            if (weaponName.equals(weapon))
                return false;

        this.weapons.put("weapon"+slot, weapon);
        return true;
    }

    public void addHeal(int slot, String heal) {
        this.heals.put("heal"+slot, heal);
    }

    public boolean addAbility(int slot, String ability) {
        Ability addAbility = null;
        for (Ability ability1: new LoadItems().getAbilities())
            if (ability1.getName().equals(ability))
                addAbility = ability1;

        for (String abilityName: this.abilities.values()) {
            if (abilityName.equals(ability))
                return false;
            if (addAbility.getIncompatibleAbilities() != null)
                for (Ability incompatibleAbility: addAbility.getIncompatibleAbilities())
                    if (incompatibleAbility.getName().equals(abilityName) && !this.abilities.get("ability"+slot).equals(incompatibleAbility.getName()))
                        return false;
        }

        this.abilities.put("ability"+slot, ability);
        return true;
    }
}
