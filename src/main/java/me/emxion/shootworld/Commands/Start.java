package me.emxion.shootworld.Commands;

import me.emxion.shootworld.Gamemodes.Gamemode;
import me.emxion.shootworld.Gamemodes.LegacyRandomStart;
import me.emxion.shootworld.Gamemodes.LegacyRandomizer;
import me.emxion.shootworld.Items.Abilities.Abilities;
import me.emxion.shootworld.Items.Weapons.Melees.Melee;
import me.emxion.shootworld.Items.Weapons.Weapon;
import me.emxion.shootworld.ShootWorld;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;

public class Start implements CommandExecutor {
    private ShootWorld plugin;
    private HashMap<String, Gamemode> gamemodes = new HashMap<>();
    public Start(ShootWorld plugin, List<Weapon> weapons, List<Melee> melees, List<Abilities> abilities) {
        this.plugin = plugin;
        this.gamemodes.put("LegacyRandomizer", new LegacyRandomizer(weapons, melees, abilities));
        this.gamemodes.put("LegacyRandomStart", new LegacyRandomStart(weapons, melees, abilities));
    }
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length == 1) {
            if (this.gamemodes.containsKey(args[0])) {
                Gamemode gm = this.gamemodes.get(args[0]);
                this.plugin.setGamemode(gm);
                gm.onStart((List<Player>) Bukkit.getOnlinePlayers());
                return true;
            }
        }
        return false;
    }
}
