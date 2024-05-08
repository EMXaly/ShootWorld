package me.emxion.shootworld.TabCompleter;

import me.emxion.shootworld.Items.Abilities.Abilities;
import me.emxion.shootworld.Items.Weapons.Melees.Melee;
import me.emxion.shootworld.Items.Weapons.Weapon;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class GiveItemsCompleter implements TabCompleter {
    private List<String> itemNames = new ArrayList<>();

    public GiveItemsCompleter(List<Weapon> weapons, List<Melee> melees, List<Abilities> abilities) {
        for (Weapon w: weapons)
            itemNames.add(w.getName());

        for (Melee m: melees)
            itemNames.add(m.getName());

        for (Abilities a: abilities)
            itemNames.add(a.getName());
    }
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return itemNames;
    }
}
