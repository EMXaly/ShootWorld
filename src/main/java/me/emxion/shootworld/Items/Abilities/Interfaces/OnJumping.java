package me.emxion.shootworld.Items.Abilities.Interfaces;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public interface OnJumping {
    public void OnJumping(PlayerJumpEvent event);
}
