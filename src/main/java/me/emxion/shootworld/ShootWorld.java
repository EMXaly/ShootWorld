package me.emxion.shootworld;

import me.emxion.shootworld.Commands.ChangeAbilityStats;
import me.emxion.shootworld.Commands.ChangeWeaponStats;
import me.emxion.shootworld.Commands.GiveItems;
import me.emxion.shootworld.Commands.Start;
import me.emxion.shootworld.Gamemodes.Gamemode;
import me.emxion.shootworld.Handlers.PlayerHandler;
import me.emxion.shootworld.Items.Abilities.Abilities;
import me.emxion.shootworld.Items.Abilities.CrouchMovements.Slam;
import me.emxion.shootworld.Items.Abilities.FlyMovements.DoubleJump;
import me.emxion.shootworld.Items.Abilities.LeftClickMovements.JumpPad;
import me.emxion.shootworld.Items.Abilities.SwapMovements.Dash;
import me.emxion.shootworld.Items.Weapons.Explosifs.GrenadeLauncher;
import me.emxion.shootworld.Items.Weapons.Explosifs.RocketLauncher;
import me.emxion.shootworld.Items.Weapons.Guns.*;
import me.emxion.shootworld.Items.Weapons.Melees.Axe;
import me.emxion.shootworld.Items.Weapons.Melees.Hoe;
import me.emxion.shootworld.Items.Weapons.Melees.Melee;
import me.emxion.shootworld.Items.Weapons.Weapon;
import me.emxion.shootworld.TabCompleter.ChangeWeaponStatsCompleter;
import me.emxion.shootworld.TabCompleter.GiveItemsCompleter;
import me.emxion.shootworld.TabCompleter.StartCompleter;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class ShootWorld extends JavaPlugin {
    private Gamemode gamemode = null;

    @Override
    public void onEnable() {
        List<Weapon> weapons  = new ArrayList<>();
        weapons.add(new SMG());
        weapons.add(new AR());
        weapons.add(new Sniper());
        weapons.add(new LMG());
        weapons.add(new Shotgun());
        weapons.add(new RocketLauncher());
        weapons.add(new GrenadeLauncher());

        List<Melee> melees = new ArrayList<>();
        melees.add(new Hoe());
        melees.add(new Axe());

        List<Abilities> abilities = new ArrayList<>();
        abilities.add(new Dash());
        abilities.add(new JumpPad());
        abilities.add(new DoubleJump());
        abilities.add(new Slam());

        // Handlers
        new PlayerHandler(this, weapons, melees, abilities);

        //Commands
        getCommand("SWGive").setExecutor(new GiveItems(weapons, melees, abilities));
        getCommand("SWGive").setTabCompleter(new GiveItemsCompleter(weapons, melees, abilities));
        getCommand("SWCWS").setExecutor(new ChangeWeaponStats(weapons));
        getCommand("SWCWS").setTabCompleter(new ChangeWeaponStatsCompleter(weapons));
        getCommand("SWCAS").setExecutor(new ChangeAbilityStats(abilities));
        getCommand("SWStart").setExecutor(new Start(this, weapons, melees, abilities));
        getCommand("SWStart").setTabCompleter(new StartCompleter());

        // Gamerules
        World world = Bukkit.getServer().getWorld("World");
        world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
        world.setGameRule(GameRule.SHOW_DEATH_MESSAGES, false);
        world.setGameRule(GameRule.DO_MOB_SPAWNING, false);

        getLogger().info("ShootWorld Enable");
    }

    public void setGamemode(Gamemode gm) {
        this.gamemode = gm;
    }

    public Gamemode getGamemode() {
        return this.gamemode;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("ShootWorld Disable");
    }
}
