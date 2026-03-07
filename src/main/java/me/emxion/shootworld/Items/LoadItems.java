package me.emxion.shootworld.Items;

import me.emxion.shootworld.Items.Abilities.Ability;
import me.emxion.shootworld.Items.Abilities.Interfaces.*;
import me.emxion.shootworld.Items.Abilities.List.*;
import me.emxion.shootworld.Items.Heals.Heal;
import me.emxion.shootworld.Items.Heals.Interfaces.*;
import me.emxion.shootworld.Items.Heals.List.CrazedKiller;
import me.emxion.shootworld.Items.Heals.List.QuickHeal;
import me.emxion.shootworld.Items.Heals.List.SpeedDemon;
import me.emxion.shootworld.Items.Weapons.Firearms.*;
import me.emxion.shootworld.Items.Weapons.Flames.FlameThrower;
import me.emxion.shootworld.Items.Weapons.Flames.FlareGun;
import me.emxion.shootworld.Items.Weapons.Launchers.*;
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
    private final List<Item> onJumping = new ArrayList<>();
    private final List<Item> onDeath = new ArrayList<>();
    private final List<Item> onProjectileHit = new ArrayList<>();
    private final List<Heal> heals = new ArrayList<>();
    private final List<Item> onRightClick = new ArrayList<>();
    private final List<Item> onSpeed = new ArrayList<>();
    private final List<Item> onKill = new ArrayList<>();


    public LoadItems() {
        this.items.add(new JumpPad());
        this.items.add(new Dash());
        this.items.add(new DoubleJump());
        this.items.add(new Slam());
        this.items.add(new Slide());
        this.items.add(new AntiGravity());
        this.items.add(new Teleporter());

        this.items.add(new SMG());
        this.items.add(new LMG());
        this.items.add(new AR());
        this.items.add(new Shotgun());
        this.items.add(new Sniper());

        this.items.add(new RocketLauncher());
        this.items.add(new GrenadeLauncher());
        this.items.add(new Shrapnel());

        this.items.add(new FlameThrower());
        this.items.add(new FlareGun());

        this.items.add(new SpeedDemon());
        this.items.add(new CrazedKiller());
        this.items.add(new QuickHeal());

        this.storeItems(items);
    }

    private void storeItems(List<Item> items) {
        for (Item i: items) {
            if (i instanceof Ability)
                this.abilities.add((Ability) i);
            if (i instanceof Weapon)
                this.weapons.add((Weapon) i);
            if (i instanceof IOnLeftClick)
                this.onLeftClickItems.add(i);
            if (i instanceof IOnMoving)
                this.onMoving.add(i);
            if (i instanceof IOnSwapingItem)
                this.onSwaping.add(i);
            if (i instanceof IOnFlying)
                this.onFlying.add(i);
            if (i instanceof IOnLanding)
                this.onLanding.add(i);
            if (i instanceof IOnSneaking)
                this.onSneaking.add(i);
            if (i instanceof IOnJumping)
                this.onJumping.add(i);
            if (i instanceof IOnDeath)
                this.onDeath.add(i);
            if (i instanceof IOnProjectileHit)
                this.onProjectileHit.add(i);
            if (i instanceof Heal)
                this.heals.add((Heal) i);
            if (i instanceof IOnRightClick)
                this.onRightClick.add(i);
            if (i instanceof IOnSpeed)
                this.onSpeed.add(i);
            if (i instanceof IOnKill)
                this.onKill.add(i);
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
    public List<Item> getOnJumping() {
        return this.onJumping;
    }
    public List<Item> getOnDeath() {
        return this.onDeath;
    }
    public List<Item> getOnProjectileHit() {
        return this.onProjectileHit;
    }
    public List<Heal> getHeals() {
        return this.heals;
    }
    public List<Item> getOnRightClick() {
        return this.onRightClick;
    }
    public List<Item> getOnSpeed() {
        return this.onSpeed;
    }
    public List<Item> getOnKill() {
        return this.onKill;
    }
}
