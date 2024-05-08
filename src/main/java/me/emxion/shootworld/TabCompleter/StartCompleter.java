package me.emxion.shootworld.TabCompleter;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class StartCompleter implements TabCompleter {
    private final List<String> gamemodes = new ArrayList<>();
    public StartCompleter() {
        this.gamemodes.add("LegacyRandomizer");
        this.gamemodes.add("LegacyRandomStart");
    }
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return this.gamemodes;
    }
}
