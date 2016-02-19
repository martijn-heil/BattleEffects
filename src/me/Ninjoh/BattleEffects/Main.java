package me.Ninjoh.BattleEffects;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin
{
    public static Main plugin;


    @Override
    public void onEnable()
    {
        // Fired when the server enables the plugin

        // Used to use JavaPlugin methods in listener classes.
        plugin = this;

        FileConfiguration config = this.getConfig();


        // Save config.
        saveDefaultConfig();


        // Register events
        getServer().getPluginManager().registerEvents(new MainListener(), this);

        if(!config.getString("skulls.howToLeaveBehindSkulls").equals("block") &&
                !config.getString("skulls.howToLeaveBehindSkulls").equals("dropItem") &&
                !config.getString("skulls.howToLeaveBehindSkulls").equals("putInKillersInventory"))
        {
            this.getLogger().severe("Invalid value (" + config.getString("skulls.howToLeaveBehindSkulls") +
                    ") in config for key: howToLeaveBehindSkulls");
            this.getLogger().severe("Terminating plugin..");
            this.setEnabled(false);
        }

    }

    @Override
    public void onDisable()
    {
        //Fired when the server stops and disables all plugins

        // Prevent memory leaks.
        // Make sure this is executed as last thing on disable!
        plugin = null;
    }
}