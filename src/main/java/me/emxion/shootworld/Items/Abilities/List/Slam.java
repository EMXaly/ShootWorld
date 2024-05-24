package me.emxion.shootworld.Items.Abilities.List;

import me.emxion.shootworld.Items.Abilities.Ability;
import me.emxion.shootworld.Items.Abilities.Interfaces.OnLanding;
import me.emxion.shootworld.Items.Abilities.Interfaces.OnSneaking;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class Slam extends Ability implements OnSneaking, OnLanding {
    protected HashMap<Player, Double> yVelocity = new HashMap<Player, Double>();
    private final int damage = 5;

    public Slam() {
        this.name = "Slam";
        this.material = Material.ANVIL;
        this.item = new ItemStack(this.material, 1);
        this.cooldown = 80;
        this.sound = Sound.BLOCK_ANVIL_LAND;
        this.volume = 0.5f;
        this.pitch = 1f;

        this.setup();
    }
    @Override
    public void OnSneaking(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        Vector playerVelocity = player.getVelocity();
        if (!player.isOnGround()) {
            playerVelocity.setY(playerVelocity.getY() - 0.1);
            player.setVelocity(playerVelocity);
            this.yVelocity.put(player, playerVelocity.getY());
        }
    }

    @Override
    public void OnLanding(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (player.getCooldown(this.material) > 0)
            return;

        if (yVelocity.get(player) == null)
            return;

        if (yVelocity.get(player) > - 1.4)
            return;

        Location playerLocation = player.getLocation();
        Collection<Entity> inRadius = playerLocation.getWorld().getNearbyEntities(playerLocation, 3, 1, 3);
        int totalDamage = 0;
        for (Entity e: inRadius) {
            if (e instanceof LivingEntity && !e.equals(player)) {
                LivingEntity le = (LivingEntity) e;
                le.damage(this.damage, player);
                totalDamage += this.damage;
            }
        }

        this.yVelocity.remove(player);
        player.setCooldown(this.material, this.cooldown);
        player.getWorld().playSound(player.getLocation(), this.sound, SoundCategory.PLAYERS, this.volume, this.pitch);
        //player.sendMessage("+1 slam +" + totalDamage + " damage");
        this.finishCooldown(player);
    }

    @Override
    public List<Ability> getIncompatibleAbilities() {
        List<Ability> incompatibleAbilities = new ArrayList<>();
        //incompatibleAbilities.add(new Slide());
        return incompatibleAbilities;
    }
}
