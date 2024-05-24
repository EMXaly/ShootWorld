package me.emxion.shootworld.Items.Heals.List;

import me.emxion.shootworld.Items.Heals.Heal;
import me.emxion.shootworld.Items.Heals.Interfaces.OnKill;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class CrazedKiller extends Heal implements OnKill {
    public CrazedKiller() {
        this.name = "CrazedKiller";
        this.material = Material.POTION;
        this.item = new ItemStack(this.material, 1);
        this.healing = 10;
        this.cooldown = 0;

        this.setup();
    }

    @Override
    public void onKill(PlayerDeathEvent event) {
        Player player = event.getPlayer();

        if (player.getCooldown(this.material) > 0)
            return;

        player.setHealth(Math.min(player.getHealthScale(), player.getHealth() + this.healing));
        player.setCooldown(this.material, this.cooldown);
    }
}
