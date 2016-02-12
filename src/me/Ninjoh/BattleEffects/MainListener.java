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


            // List of blocks a skull shouldn't be placed on top of.
            List<Material> blockList = new ArrayList<Material>();
            blockList.add(Material.AIR);
            blockList.add(Material.WATER);
            blockList.add(Material.STATIONARY_WATER);
            blockList.add(Material.LAVA);
            blockList.add(Material.STATIONARY_LAVA);
            blockList.add(Material.LONG_GRASS);
            blockList.add(Material.YELLOW_FLOWER);
            blockList.add(Material.RED_ROSE);
            blockList.add(Material.SUGAR_CANE_BLOCK);
            blockList.add(Material.DEAD_BUSH);
            blockList.add(Material.DOUBLE_PLANT);
            blockList.add(Material.FIRE);
            blockList.add(Material.SNOW);
            blockList.add(Material.RED_MUSHROOM);
            blockList.add(Material.TORCH);
            blockList.add(Material.TRIPWIRE);
            blockList.add(Material.RAILS);
            blockList.add(Material.ACTIVATOR_RAIL);
            blockList.add(Material.DETECTOR_RAIL);
            blockList.add(Material.POWERED_RAIL);


            // Loop through all blocks below the player's death location.
            for (Location loc = p.getLocation(); loc.getY() > 0; loc.subtract(0, 1, 0))
            {
                // If the block isn't a block a skull shouldn't be placed on top of,
                // get the block above that block and place the skull there.
                if (!blockList.contains(loc.getBlock().getType()))
                {
                    Location finalLoc = loc.add(0, 1, 0);

                    // Break the block naturally, if it's a rails or something for example.
                    if(finalLoc.getBlock().getType() != Material.AIR)
                    {
                        finalLoc.getBlock().breakNaturally();
                    }

                    // Place the player's skull on the location.
                    finalLoc.getBlock().setType(Material.SKULL);
                    finalLoc.getBlock().setData((byte) 1);
                    Skull s = (Skull) finalLoc.getBlock().getState();
                    s.setOwner(skullOwner);
                    s.setRotation(blockFace);
                    s.update();

                    break;
                }
            }
        }
    }


    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent e)
    {
        if (e.getEntity() instanceof Player && !e.isCancelled())
        {
            // Cast Event Entitiy to Player object.
            Player p = (Player) e.getEntity();


            // Show blood particle effect if enabled in config.
            if (config.getBoolean("enableBloodParticleEffect"))
            {
                // Show redstone block blood effect.
                p.getWorld().playEffect(p.getLocation(), Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
            }


            // Leave behind blood if enabled in config.
            if (config.getBoolean("enableLeavingBehindBlood"))
            {
                // Chance to leave blood behind, percentage in int.
                if (Chance(config.getInt("chanceToLeaveBehindBloodOnHit")))
                {
                    // Prepare blockdata
                    Byte blockData = 0x0;

                    // If random chance is true leave blood behind
                    p.getLocation().getWorld().spawnFallingBlock(p.getLocation(), Material.REDSTONE_WIRE, blockData).setDropItem(false);
                }
            }
        }
    }


    // Random chance generator.
    private boolean Chance(int Chance)
    {
        int number = (int) (Math.random() * 100);
        if (number < Chance)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}