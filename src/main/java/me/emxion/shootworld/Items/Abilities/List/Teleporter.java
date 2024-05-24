package me.emxion.shootworld.Items.Abilities.List;

import me.emxion.shootworld.Items.Abilities.Ability;
import me.emxion.shootworld.Items.Abilities.Interfaces.OnFlying;
import org.bukkit.*;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Teleporter extends Ability implements OnFlying {
    private HashMap<Player, Item> playerTp = new HashMap<>();
    public Teleporter() {
        this.name = "Teleporter";
        this.material = Material.ENDER_PEARL;
        this.item = new ItemStack(this.material, 1);
        this.cooldown = 50;
        this.sound = Sound.ENTITY_ENDERMAN_TELEPORT;
        this.volume = 3f;
        this.pitch = 0.5f;

        this.setup();
    }

    @Override
    public void OnFlying(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();

        if (player.getCooldown(this.material) > 0 && player.getCooldown(this.material) <= this.cooldown)
            return;

        if (this.playerTp.get(player) != null) {
            Item item = this.playerTp.get(player);
            player.teleport(item.getLocation());
            player.getWorld().playSound(player.getLocation(), this.sound, SoundCategory.PLAYERS, this.volume, this.pitch);
            player.getWorld().spawnParticle(Particle.PORTAL, player.getLocation(), 25, 0.5, 0.5, 0.5);
            item.remove();
            this.playerTp.remove(player);
            player.setCooldown(this.material, this.cooldown);
            this.finishCooldown(player);
        }
        else {
            player.setCooldown(this.material, 999999999);
            player.getWorld().playSound(player.getLocation(), this.sound, SoundCategory.PLAYERS, this.volume, this.pitch +0.5f);
            Item item = player.getWorld().dropItem(player.getLocation().add(0, 1, 0), this.item);
            item.setGravity(false);
            item.setVelocity(new Vector());
            item.setCanMobPickup(false);
            item.setCanPlayerPickup(false);
            this.playerTp.put(player, item);
        }
    }

    @Override
    public List<Ability> getIncompatibleAbilities() {
        List<Ability> incompatibleAbilities = new ArrayList<>();
        incompatibleAbilities.add(new DoubleJump());
        return incompatibleAbilities;
    }
}
