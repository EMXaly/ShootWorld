package me.emxion.shootworld.Items.Heals.List;

import me.emxion.shootworld.Items.Heals.Heal;
import me.emxion.shootworld.Items.Heals.Interfaces.OnRightClick;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class QuickHeal extends Heal implements OnRightClick {
    public QuickHeal() {
        this.name = "QuickHeal";
        this.material = Material.POTION;
        this.item = new ItemStack(this.material, 1);
        this.healing = 12;
        this.cooldown = 200;

        this.setup();
    }

    @Override
    public void onRightClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (player.getCooldown(this.material) > 0)
            return;

        player.setHealth(Math.min(player.getHealthScale(), player.getHealth() + this.healing));
        player.setCooldown(this.material, this.cooldown);

    }
}
