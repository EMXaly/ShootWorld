package me.emxion.shootworld.TabCompleter;

import me.emxion.shootworld.Items.Weapons.Weapon;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ChangeWeaponStatsCompleter implements TabCompleter {
    private List<String> weapons = new ArrayList<>();
    private List<String> stats = new ArrayList<>();
    public ChangeWeaponStatsCompleter(List<Weapon> weapons) {
        for (Weapon w: weapons)
            this.weapons.add(w.getName());
        this.stats.add("damage");
        this.stats.add("cooldown");
        this.stats.add("bullets");
        this.stats.add("magazineSize");
        this.stats.add("reloadTime");
        this.stats.add("accuracy");
        this.stats.add("bulletVelocity");
        this.stats.add("power");
        this.stats.add("projectileVelocity");
        this.stats.add("hasGravity");

    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length == 1)
            return this.weapons;
        if (strings.length == 2)
            return this.stats;


        return null;
    }
}
