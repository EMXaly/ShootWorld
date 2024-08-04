package me.emxion.shootworld.Items.Abilities.List;

import me.emxion.shootworld.Items.Abilities.Ability;
import me.emxion.shootworld.Items.Abilities.Interfaces.OnLeftClick;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;

public class GravityPropulsion extends Ability implements OnLeftClick {
    public GravityPropulsion() {
        // playerLocation.distance(ProjectileLocation)
    }

    @Override
    public void setPower(double power) {

    }

    @Override
    public void OnLeftClick(PlayerInteractEvent event) {

    }

    @Override
    public List<Ability> getIncompatibleAbilities() {
        return null;
    }
}
