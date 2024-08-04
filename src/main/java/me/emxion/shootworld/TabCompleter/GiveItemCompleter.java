package me.emxion.shootworld.TabCompleter;

import me.emxion.shootworld.Items.Item;
import me.emxion.shootworld.Items.LoadItems;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class GiveItemCompleter implements TabCompleter {
    private final ArrayList items = new ArrayList<>();

    public GiveItemCompleter(LoadItems items) {
        for (Item item: items.getItems())
            this.items.add(item.getName());
    }


    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length == 2)
            return this.items;

        return null;
    }
}
