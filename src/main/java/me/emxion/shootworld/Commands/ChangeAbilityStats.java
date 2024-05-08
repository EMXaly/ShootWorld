package me.emxion.shootworld.Commands;

import me.emxion.shootworld.Items.Abilities.Abilities;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ChangeAbilityStats implements CommandExecutor {
    private final List<Abilities> abilities;
    public ChangeAbilityStats(List<Abilities> abilities) {
        this.abilities = abilities;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String [] args) {
        if (args.length == 3) {
            Abilities a = searchAbility(args[0]);
            if (a == null)
                return false;

            this.applyAbilityNewStat(a, args[1], args[2]);
            return true;
            }

        return false;
    }

    private Abilities searchAbility(String name) {
        for (Abilities ability : this.abilities)
            if (name.equals(ability.getName()))
                return ability;

        return null;
    }

    private void applyAbilityNewStat(Abilities a, String stats, String value) {
        switch (stats) {
            case "cooldown":
                a.setCooldown(Integer.parseInt(value));
                break;
        }
    }
}
