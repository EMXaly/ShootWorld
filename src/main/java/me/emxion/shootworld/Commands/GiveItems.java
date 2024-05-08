package me.emxion.shootworld.Commands;

import me.emxion.shootworld.Items.Abilities.Abilities;
import me.emxion.shootworld.Items.Weapons.Melees.Melee;
import me.emxion.shootworld.Items.Weapons.Weapon;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GiveItems implements CommandExecutor {
    private final List<Weapon> weapons;
    private final List<Melee> melees;
    private final List<Abilities> abilities;

    public GiveItems(List<Weapon> weapons, List<Melee> melees, List<Abilities> abilities) {
        this.weapons = weapons;
        this.melees = melees;
        this.abilities = abilities;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String [] args) {
        if (args.length == 0)
            return false;

        if (!(sender instanceof Player)) {
            sender.sendMessage("Only Players can use this command");
            return true;
        }

        Player player = (Player) sender;
        Inventory inv = player.getInventory();

        for (Weapon weapon : this.weapons) {
            if (args[0].equals(weapon.getName())) {
                inv.addItem(weapon.getItem());
                return true;
            }
        }
        for (Melee melee : this.melees) {
            if (args[0].equals(melee.getName())) {
                inv.addItem(melee.getItem());
                return true;
            }
        }
        for (Abilities ability: this.abilities) {
            if (args[0].equals(ability.getName())) {
                inv.addItem(ability.getItem());
                return true;
            }
        }
        return true;
    }
}
