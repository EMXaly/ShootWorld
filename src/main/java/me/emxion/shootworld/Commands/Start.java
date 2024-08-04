package me.emxion.shootworld.Commands;

import me.emxion.shootworld.Gamemodes.Gamemode;
import me.emxion.shootworld.ShootWorld;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Start implements CommandExecutor {
    private ShootWorld plugin;
    private final List<Gamemode> gamemodes;

    public Start(ShootWorld plugin, List<Gamemode> gamemodes) {
        this.plugin = plugin;
        this.gamemodes = gamemodes;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length == 1) {
            for (Gamemode gm: this.gamemodes)
                if (gm.getName().equals(args[0])) {
                    if (this.plugin.getGamemode() != null)
                        this.plugin.getGamemode().onEnd((List<Player>) Bukkit.getOnlinePlayers());

                    this.plugin.setGamemode(gm);
                    gm.onStart((List<Player>) Bukkit.getOnlinePlayers());
                    return true;
                }
        }
        return false;
    }
}
