package me.emxion.shootworld.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Loadout implements CommandExecutor {
    private me.emxion.shootworld.Loadouts.Loadout loadout;
    public Loadout(me.emxion.shootworld.Loadouts.Loadout loadout) {
        this.loadout = loadout;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only Players can use this command");
            return true;
        }

        Player player = (Player) sender;
        this.loadout.openInventory(player);

        return true;
    }
}
