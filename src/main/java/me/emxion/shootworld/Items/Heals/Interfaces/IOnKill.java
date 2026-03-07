package me.emxion.shootworld.Items.Heals.Interfaces;

import org.bukkit.event.entity.PlayerDeathEvent;

public interface IOnKill {
    void onKill(PlayerDeathEvent event);
}
