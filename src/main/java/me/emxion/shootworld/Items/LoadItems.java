package me.emxion.shootworld.Items;

import me.emxion.shootworld.Items.Abilities.Ability;
import me.emxion.shootworld.Items.Abilities.Interfaces.*;
import me.emxion.shootworld.Items.Abilities.List.Dash;
import me.emxion.shootworld.Items.Abilities.List.DoubleJump;
import me.emxion.shootworld.Items.Abilities.List.JumpPad;
import me.emxion.shootworld.Items.Abilities.List.Slam;
import me.emxion.shootworld.Items.Weapons.Firearms.*;
import me.emxion.shootworld.Items.Weapons.Launchers.GrenadeLauncher;
import me.emxion.shootworld.Items.Weapons.Launchers.RocketLauncher;
import me.emxion.shootworld.Items.Weapons.Launchers.Shrapnel;
import me.emxion.shootworld.Items.Weapons.Weapon;

import java.util.ArrayList;
import java.util.List;

public class LoadItems {
    private final List<Item> items = new ArrayList<>();
    private final List<Ability> abilities = new ArrayList<>();
    private final List<Weapon> weapons = new ArrayList<>();
    private final List<Item> onLeftClickItems = new ArrayList<>();
    private final List<Item> onMoving = new ArrayList<>();
    private final List<Item> onSwaping = new ArrayList<>();
    private final List<Item> onFlying = new ArrayList<>();
    private final List<Item> onLanding = new ArrayList<>();
    private final List<Item> onSneaking = new ArrayList<>();

    public LoadItems() {
        this.items.add(new JumpPad());
        this.items.add(new Dash());
        this.items.add(new DoubleJump());
        this.items.add(new Slam());

        this.items.add(new SMG());
        this.items.add(new LMG());
        this.items.add(new AR());
        this.items.add(new Shotgun());
        this.items.add(new Sniper());

        this.items.add(new RocketLauncher());
        this.items.add(new GrenadeLauncher());
        this.items.add(new Shrapnel());

        this.storeItems(items);
    }

    private void storeItems(List<Item> items) {
        for (Item i: items) {
            if (i instanceof Ability)
                this.abilities.add((Ability) i);
            if (i instanceof Weapon)
                this.weapons.add((Weapon) i);
            if (i instanceof OnLeftClick)
                this.onLeftClickItems.add(i);
            if (i instanceof OnMoving)
                this.onMoving.add(i);
            if (i instanceof OnSwapingItem)
                this.onSwaping.add(i);
            if (i instanceof OnFlying)
                this.onFlying.add(i);
            if (i instanceof OnLanding)
                this.onLanding.add(i);
            if (i instanceof OnSneaking)
                this.onSneaking.add(i);
        }
    }

    public List<Item> getItems() {
        return this.items;
    }
    public List<Ability> getAbilities() {
        return this.abilities;
    }
    public List<Weapon> getWeapons() {
        return this.weapons;
    }
    public List<Item> getOnLeftClickItems() {
        return this.onLeftClickItems;
    }

    public List<Item> getOnMoving() {
        return this.onMoving;
    }
    public List<Item> getOnSwaping() {
        return this.onSwaping;
    }
    public List<Item> getOnFlying() {
        return this.onFlying;
    }
    public List<Item> getOnLanding() {
        return this.onLanding;
    }
    public List<Item> getOnSneaking() {
        return this.onSneaking;
    }
}
