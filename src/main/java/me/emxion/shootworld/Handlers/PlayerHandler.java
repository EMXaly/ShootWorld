package me.emxion.shootworld.Handlers;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import me.emxion.shootworld.Gamemodes.Gamemode;
import me.emxion.shootworld.Items.Abilities.Abilities;
import me.emxion.shootworld.Items.Abilities.CrouchMovements.CrouchingMovement;
import me.emxion.shootworld.Items.Abilities.FlyMovements.FlyMovement;
import me.emxion.shootworld.Items.Abilities.LeftClickMovements.LeftClickMovement;
import me.emxion.shootworld.Items.Abilities.SwapMovements.SwapMovement;
import me.emxion.shootworld.Items.Weapons.Explosifs.Explosif;
import me.emxion.shootworld.Items.Weapons.Guns.Gun;
import me.emxion.shootworld.Items.Weapons.Melees.Melee;
import me.emxion.shootworld.Items.Weapons.Weapon;
import me.emxion.shootworld.ShootWorld;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.block.data.type.Snow;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PlayerHandler implements Listener {
    private final ShootWorld plugin;
    private final List<Weapon> weapons;
    private final List<Melee> melees;
    private final List<Abilities> abilities;
    public PlayerHandler(ShootWorld plugin, List<Weapon> weapons, List<Melee> melees, List<Abilities> abilities) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
        this.weapons = weapons;
        this.melees = melees;
        this.abilities = abilities;
    }

    @EventHandler
    public void OnJoining(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.setGameMode(GameMode.SPECTATOR);
        player.getInventory().clear();
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        // Right Click
        Player player = event.getPlayer();

        if (event.getAction().isRightClick()) {
            for (Weapon weapon: weapons) {
                ItemStack item = weapon.getItem();
                if (player.getItemInHand().getType() == weapon.getItem().getType()) {
                    weapon.attacking(event);
                    return;
                }
            }
        }

        // Left Click
        if (event.getAction().isLeftClick()) {
            for (Abilities ability : abilities)
                if (ability instanceof LeftClickMovement)
                    if (player.getInventory().contains(ability.getMaterial())) {
                        event.setCancelled(true);
                        LeftClickMovement LCAbility = (LeftClickMovement) ability;
                        LCAbility.leftClick(event);
                    }
        }

        if (player.getGameMode() != GameMode.CREATIVE && player.getGameMode() != GameMode.SPECTATOR)
            event.setCancelled(true);
    }

    @EventHandler
    public void onMoving(PlayerMoveEvent event) {
        if (!event.hasChangedPosition())
            return;

        Player player = event.getPlayer();
        if (!player.getAllowFlight())
            player.setAllowFlight(true);

        for (Abilities ability : abilities) {
            if (ability instanceof LeftClickMovement)
                ability.movement(player);

            if (ability instanceof FlyMovement) // landing()
                if (player.getInventory().contains(ability.getMaterial()))
                    if (player.isOnGround()) {
                        FlyMovement fm = (FlyMovement) ability;
                        fm.landing(player);
                    }

            if (ability instanceof CrouchingMovement)
                if (player.getInventory().contains(ability.getMaterial()))
                    if (player.isSneaking())
                        ability.movement(player);
        }

    }

    @EventHandler
    public void OnSwapItem(PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();
        for (Abilities ability : abilities)
            if (ability instanceof SwapMovement)
                if (player.getInventory().contains(ability.getMaterial())) {
                    event.setCancelled(true);
                    ability.movement(player);
                }
    }

    @EventHandler
    public void OnCrouching(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();

        for (Abilities ability: abilities) {
            if (ability instanceof CrouchingMovement)
                if (player.getInventory().contains(ability.getMaterial())) {
                    CrouchingMovement cm = (CrouchingMovement) ability;
                    cm.crouching(player);
                }
        }
    }

    @EventHandler
    public void OnFlight(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();

        if (player.getGameMode() != GameMode.CREATIVE && player.getGameMode() != GameMode.SPECTATOR) {
            event.setCancelled(true);
            player.setAllowFlight(false);
            player.setFlying(false);
        }



        if(event.isFlying() && (player.getGameMode() != GameMode.CREATIVE || player.getGameMode() != GameMode.SPECTATOR)) {
            for (Abilities ability: abilities) {
                if (ability instanceof FlyMovement)
                    if (player.getInventory().contains(ability.getMaterial())) {
                        ability.movement(player);
                    }
            }
        }
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (event.getEntity() instanceof Snowball) {
            Snowball sb = (Snowball) event.getEntity();
            for (Weapon weapon : this.weapons) {
                if (weapon instanceof Explosif) {
                    Explosif explosif = (Explosif) weapon;
                    if (sb.getCustomName().equals(explosif.getName())) {
                        TNTPrimed tnt = sb.getWorld().spawn(sb.getLocation(), TNTPrimed.class);
                        tnt.setVisibleByDefault(false);
                        tnt.setFuseTicks(0);
                        tnt.customName(Component.text(weapon.getName()));
                        tnt.setSource((Entity) sb.getShooter());
                        tnt.setCustomNameVisible(false);
                    }
                }
            }
        }
        else {
            if(!(event.getHitEntity() instanceof LivingEntity)) {
                event.getEntity().remove();
                return;
            }


            Projectile projectile = event.getEntity();
            Player damager = (Player) event.getEntity().getShooter();
            LivingEntity damaged = (LivingEntity) event.getHitEntity();

            damaged.setNoDamageTicks(0);

            for (Weapon weapon : this.weapons) {
                if (weapon instanceof Gun) {
                    Gun gun = (Gun) weapon;
                    if (projectile.getCustomName().equals(gun.getName()))
                        damaged.damage(gun.getDamage(), damager);
                }
            }
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        event.setCancelled(true);

        if (event.getEntity() instanceof TNTPrimed) {
            TNTPrimed tnt = (TNTPrimed) event.getEntity();

            for (Weapon weapon : this.weapons) {
                if (weapon instanceof Explosif) {
                    Explosif explosif = (Explosif) weapon;
                    if (Objects.equals(tnt.getCustomName(), explosif.getName()))
                        explosif.exploding(event);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerAttack(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof LivingEntity) || !(event.getEntity() instanceof LivingEntity))
            return;

        LivingEntity attacker = (LivingEntity) event.getDamager();
        LivingEntity attacked = (LivingEntity) event.getEntity();

        ItemStack attackerItem = attacker.getEquipment().getItem(EquipmentSlot.HAND);

        if (attackerItem.lore() == null)
            return;

        for (Melee m: melees) {
            if (attackerItem.equals(m.getItem().getType())) {
                m.attacking(attacker, attacked);
                event.setCancelled(true);
            }
        }

    }


        @EventHandler
    public void onItemHeld(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        ItemStack oldItem = player.getInventory().getItem(event.getPreviousSlot());
        ItemStack newItem = player.getInventory().getItem(event.getNewSlot());

        for (Weapon w: this.weapons) {
            if (w instanceof Gun) {
                Gun g = (Gun) w;
                ItemStack item = g.getItem();
                if (oldItem != null)
                    if (oldItem.getType() == g.getItem().getType())
                        g.reloading(player, true);
                if (newItem != null)
                    if (newItem.getType() == g.getItem().getType())
                        g.reloading(player, false);
            }
            else if (w instanceof Explosif) {
                Explosif e = (Explosif) w;
                ItemStack item = e.getItem();
                if (oldItem != null)
                    if (oldItem.getType() == e.getItem().getType())
                        e.reloading(player, true);
                if (newItem != null)
                    if (newItem.getType() == e.getItem().getType())
                        e.reloading(player, false);
            }


        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player killed = event.getPlayer();
        Player killer = killed.getKiller();
        Gamemode gm = plugin.getGamemode();

        if (killer != null && killer != killed)
            if (gm != null) {
                gm.onPlayerDeath(killer, killed);
                if (gm.isWinning(killer)) {
                    Bukkit.broadcast(Component.text(killer.getName() + " à gagné !"));
                    List<Player> onlinePlayers = new ArrayList<>(Bukkit.getOnlinePlayers());
                    gm.onEnd(onlinePlayers);
                    this.plugin.setGamemode(null);
                }
            }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Gamemode gm = plugin.getGamemode();
        if (gm != null) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    gm.onPlayerRespawn(event.getPlayer());
                }
            }.runTaskLater(ShootWorld.getPlugin(ShootWorld.class), 1).getTaskId();
        }

    }

    /*@EventHandler
    public void onPlayerGainLevel(PlayerLevelChangeEvent event) {
        if (event.getOldLevel() > event.getNewLevel())
            return;

        Player player = event.getPlayer();
    }

    @EventHandler
    public void onClickInventory(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack itemStack = event.getCurrentItem();

        if (itemStack == null)
            return;
    }*/
}
