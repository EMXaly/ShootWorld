package me.emxion.shootworld.TabCompleter;

import me.emxion.shootworld.Gamemodes.Gamemode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class StartCompleter implements TabCompleter {
    private final List<String> gamemodes = new ArrayList<>();
    public StartCompleter(List<Gamemode> gamemodes) {
        for (Gamemode gm: gamemodes)
            this.gamemodes.add(gm.getName());
    }
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return this.gamemodes;
    }
}
