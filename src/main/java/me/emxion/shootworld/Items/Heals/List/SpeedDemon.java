package me.emxion.shootworld.Items.Heals.List;

import me.emxion.shootworld.Items.Heals.Heal;
import me.emxion.shootworld.Items.Heals.Interfaces.OnSpeed;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class SpeedDemon extends Heal implements OnSpeed {
    private double minVelocity = 0.75;
    public SpeedDemon() {
        this.name = "SpeedDemon";
        this.material = Material.LINGERING_POTION;
        this.item = new ItemStack(this.material, 1);
        this.healing = 0.2f;
        this.cooldown = 0;

        this.setup();
    }

    @Override
    public void onSpeed(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (player.getCooldown(this.material) > 0)
            return;

        Vector playerVelocity = player.getVelocity();
        playerVelocity.setY(playerVelocity.getY()/1.75);

        if (playerVelocity.length() > this.minVelocity) {
            player.setHealth(Math.min(player.getHealthScale(), player.getHealth() + this.healing));
            player.setCooldown(this.material, this.cooldown);
        }
    }
}
