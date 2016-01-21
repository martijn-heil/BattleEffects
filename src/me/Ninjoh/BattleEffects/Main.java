package me.Ninjoh.BattleEffects;

import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin
{
    public static Main plugin;

   // public static boolean useWorldguard = false;


    @Override
    public void onEnable()
    {
        // Fired when the server enables the plugin

        // Used to use JavaPlugin methods in listener classes.
        plugin = this;

        //Plugin worldguard = getWorldGuard();

//        if(worldguard == null)
//        {
//            plugin.getLogger().info("Successfully hooked into worldguard");
//            useWorldguard = true;
//        }

        // Save config.
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        saveConfig();


        // Register events
        getServer().getPluginManager().registerEvents(new MainListener(), this);

    }

    @Override
    public void onDisable()
    {
        //Fired when the server stops and disables all plugins

        // Prevent memory leaks.
        // Make sure this is executed as last thing on disable!
        plugin = null;
    }


//    public WorldGuardPlugin getWorldGuard()
//    {
//        Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");
//        if ((plugin == null) || (!(plugin instanceof WorldGuardPlugin)))
//        {
//            return null;
//        }
//        return (WorldGuardPlugin)plugin;
//    }
}