package me.emxion.shootworld.Items.Abilities.List;

import me.emxion.shootworld.Items.Abilities.Ability;
import me.emxion.shootworld.Items.Abilities.Interfaces.IOnLeftClick;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;

public class GravityPropulsion extends Ability implements IOnLeftClick {
    public GravityPropulsion() {
        // playerLocation.distance(ProjectileLocation)
    }

    @Override
    public void setPower(double power) {

    }

    @Override
    public void onLeftClick(PlayerInteractEvent event) {

    }

    @Override
    public List<Ability> getIncompatibleAbilities() {
        return null;
    }
}
