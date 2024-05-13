package me.emxion.shootworld.Commands;

import me.emxion.shootworld.Items.Abilities.Ability;
import me.emxion.shootworld.Items.Item;
import me.emxion.shootworld.Items.LoadItems;
import me.emxion.shootworld.Items.Weapons.Weapon;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public class GiveItem implements CommandExecutor {
    private final LoadItems items;
    public GiveItem(LoadItems items) {
        this.items = items;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only Players can use this command");
            return true;
        }

        if (args.length <= 1)
            return false;

        Player player = (Player) sender;
        Inventory inv = player.getInventory();

        if (args[1].equalsIgnoreCase("all")) {
            if (args[0].equalsIgnoreCase("item"))
                return this.giveAllItems(inv);
            if (args[0].equalsIgnoreCase("weapons"))
                return this.giveAllWeapon(inv);
            if (args[0].equalsIgnoreCase("ability"))
                return this.giveAllAbilities(inv);
            if (args[0].equalsIgnoreCase("gun"))
                return this.giveAllGuns(inv);
            if (args[0].equalsIgnoreCase("melee"))
                return this.giveAllMelees(inv);
        }

        return false;
    }

    private boolean giveAllItems(Inventory inv) {
        for (Item item: this.items.getItems())
            inv.addItem(item.getItem());

        return true;
    }

    private boolean giveAllWeapon(Inventory inv) {
        for (Weapon weapon : this.items.getWeapons())
            inv.addItem(weapon.getItem());

        return true;
    }

    private boolean giveAllAbilities(Inventory inv) {
        for (Ability ability : this.items.getAbilities())
            inv.addItem(ability.getItem());

        return true;
    }

    private boolean giveAllGuns(Inventory inv) {
        return true;
    }

    private boolean giveAllLaunchers(Inventory inv) {
        return true;
    }

    private boolean giveAllMelees(Inventory inv) {
        return true;
    }
}
