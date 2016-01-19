package me.Ninjoh.BattleEffects;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Skull;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MainListener implements Listener
{
    FileConfiguration config = me.Ninjoh.BattleEffects.Main.plugin.getConfig();


    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent e)
    {
        if (config.getBoolean("enableLeavingBehindPlayerHeadOnDeath"))
        {

            // Get player
            Player p = e.getEntity();


            String skullOwner = p.getName();

            Location deathLocation = p.getLocation();

            // List of facing directions.
            List<BlockFace> list = new ArrayList<BlockFace>();
            list.add(BlockFace.NORTH);
            list.add(BlockFace.NORTH_NORTH_EAST);
            list.add(BlockFace.NORTH_EAST);
            list.add(BlockFace.EAST_NORTH_EAST);
            list.add(BlockFace.EAST);
            list.add(BlockFace.EAST_SOUTH_EAST);
            list.add(BlockFace.SOUTH_EAST);
            list.add(BlockFace.SOUTH_SOUTH_EAST);
            list.add(BlockFace.SOUTH);
            list.add(BlockFace.SOUTH_SOUTH_WEST);
            list.add(BlockFace.SOUTH_WEST);
            list.add(BlockFace.WEST_SOUTH_WEST);
            list.add(BlockFace.WEST);
            list.add(BlockFace.WEST_NORTH_WEST);
            list.add(BlockFace.NORTH_WEST);
            list.add(BlockFace.NORTH_NORTH_WEST);

            // Get random facing direction.
            BlockFace blockFace = list.get(new Random().nextInt(list.size()));

            // Place the player's skull on player's death location...
            deathLocation.getBlock().setType(Material.SKULL);
            deathLocation.getBlock().setData((byte) 1);
            Skull s = (Skull) deathLocation.getBlock().getState();
            s.setOwner(skullOwner);
            s.setRotation(blockFace);
            s.update();
        }
    }

    @EventHandler(priority= EventPriority.MONITOR)
    public void onEntityDamageByEntityEvent (EntityDamageByEntityEvent e)
    {
        if (e.getEntity() instanceof Player && !e.isCancelled())
        {
            // Cast Event Entitiy to Player object.
            Player p = (Player)e.getEntity();


            // Show blood particle effect if enabled in config.
            if(config.getBoolean("enableBloodParticleEffect"))
            {
                // Show redstone block blood effect.
                p.getWorld().playEffect(p.getLocation(), Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
            }


            // Leave behind blood if enabled in config.
            if(config.getBoolean("enableLeavingBehindBlood"))
            {
                // Chance to leave blood behind, percentage in int.
                if (Chance(config.getInt("chanceToLeaveBehindBloodOnHit"))) {
                    // Prepare blockdata
                    Byte blockData = 0x0;

                    // If random chance is true leave blood behind
                    p.getLocation().getWorld().spawnFallingBlock(p.getLocation(), Material.REDSTONE_WIRE, blockData).setDropItem(false);
                }
            }
        }
    }

    // Random chance generator.
    public boolean Chance(int Chance)
    {
        int number = (int) (Math.random() * 100);
        if(number < Chance)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}