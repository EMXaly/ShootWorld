package me.emxion.shootworld.Commands;

import me.emxion.shootworld.Items.Abilities.Ability;
import me.emxion.shootworld.Items.Item;
import me.emxion.shootworld.Items.LoadItems;
import me.emxion.shootworld.Items.Weapons.Weapon;
import org.bukkit.Bukkit;
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

        Player player = Bukkit.getPlayer(args[0]);

        if (player == null)
            return false;

        Inventory playerInv = player.getInventory();

        for (Item item: this.items.getItems())
            if (item.getName().equals(args[1])) {
                playerInv.addItem(item.getItem());
                return true;
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
