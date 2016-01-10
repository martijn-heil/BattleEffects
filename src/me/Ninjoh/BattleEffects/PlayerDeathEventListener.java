package me.Ninjoh.BattleEffects;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Skull;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;


public class PlayerDeathEventListener implements Listener
{
    FileConfiguration config = me.Ninjoh.BattleEffects.Main.plugin.getConfig();


    @EventHandler(priority= EventPriority.NORMAL)
    public void onPlayerDeathEvent(PlayerDeathEvent e)
    {
            // Get player
            Player p = e.getEntity();


            String skullOwner = p.getName();

            Location deathLocation = p.getLocation();

            // Place the player's skull on player's death location...
            deathLocation.getBlock().setType(Material.SKULL);
            deathLocation.getBlock().setData((byte) 1);
            Skull s = (Skull) deathLocation.getBlock().getState();
            s.setOwner(skullOwner);
            s.update();
    }
}