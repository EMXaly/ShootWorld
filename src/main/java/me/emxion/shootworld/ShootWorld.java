package me.emxion.shootworld;


import me.emxion.shootworld.Commands.GiveItem;
import me.emxion.shootworld.Commands.Start;
import me.emxion.shootworld.Gamemodes.DeathMatch;
import me.emxion.shootworld.Gamemodes.Gamemode;
import me.emxion.shootworld.Gamemodes.RandomStart;
import me.emxion.shootworld.Gamemodes.Randomizer;
import me.emxion.shootworld.Handlers.*;
import me.emxion.shootworld.Items.LoadItems;
import me.emxion.shootworld.Loadouts.Loadout;
import me.emxion.shootworld.TabCompleter.StartCompleter;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class ShootWorld extends JavaPlugin {
    private Gamemode gamemode;
    @Override
    public void onEnable() {
        LoadItems loadItems = new LoadItems();

        // Handlers
        AbilitiesHandlers abilitiesHandlers = new AbilitiesHandlers(loadItems);
        Bukkit.getPluginManager().registerEvents(abilitiesHandlers, this);
        WeaponsHandlers weaponsHandlers = new WeaponsHandlers(loadItems);
        Bukkit.getPluginManager().registerEvents(weaponsHandlers, this);
        GamemodeHandlers gamemodeHandlers = new GamemodeHandlers(this);
        Bukkit.getPluginManager().registerEvents(gamemodeHandlers, this);
        HealsHandlers healsHandlers = new HealsHandlers(loadItems);
        Bukkit.getPluginManager().registerEvents(healsHandlers, this);

        Loadout loadout = new Loadout();
        LoadoutHandlers loadoutHandlers = new LoadoutHandlers();
        Bukkit.getPluginManager().registerEvents(loadoutHandlers, this);

        //Gamemodes
        List<Gamemode> gamemodes = new ArrayList<>();
        gamemodes.add(new Randomizer(loadItems));
        gamemodes.add(new RandomStart(loadItems));
        gamemodes.add(new DeathMatch(loadItems, loadoutHandlers));

        //Commands
        getCommand("SWGive").setExecutor(new GiveItem(loadItems));
        getCommand("SWStart").setExecutor(new Start(this, gamemodes));
        getCommand("SWStart").setTabCompleter(new StartCompleter(gamemodes));
        getCommand("loadout").setExecutor(new me.emxion.shootworld.Commands.Loadout(loadout));

        //Gamerules
        World world = Bukkit.getServer().getWorld("World");
        world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
        world.setGameRule(GameRule.SHOW_DEATH_MESSAGES, false);
        world.setGameRule(GameRule.DO_MOB_SPAWNING, false);

        getLogger().info("ShootWorld Enable");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("ShootWorld Disable");
    }

    public void setGamemode(Gamemode gm) {
        this.gamemode = gm;
    }

    public Gamemode getGamemode() {
        return this.gamemode;
    }
}
