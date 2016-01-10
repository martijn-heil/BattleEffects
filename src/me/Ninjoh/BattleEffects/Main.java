package me.Ninjoh.BattleEffects;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin {
    public static Main plugin;

    @Override
    public void onEnable(){
        // Fired when the server enables the plugin

        // Used to use JavaPlugin methods in listener classes.
        plugin = this;


        // Generate config.
        getConfig().addDefault("enableBloodParticleEffect", true);
        getConfig().addDefault("enableLeavingBehindPlayerHeadOnDeath", true);
        getConfig().addDefault("enableLeavingBehindBlood", true);
        getConfig().addDefault("chanceToLeaveBehindBloodOnHit", 50);

        // Save config.
        getConfig().options().copyDefaults(true);
        saveConfig();

        // Register events

        // Register PlayerDeathEvent listener if enableLeavingBehindPlayerHeadOnDeath is set to true in config.
        if(getConfig().getBoolean("enableLeavingBehindPlayerHeadOnDeath"))
        {
            getServer().getPluginManager().registerEvents(new me.Ninjoh.BattleEffects.PlayerDeathEventListener(), this);
        }

        if(getConfig().getBoolean("enableLeavingBehindBlood") || getConfig().getBoolean("enableBloodParticleEffect"))
        {
            getServer().getPluginManager().registerEvents(new me.Ninjoh.BattleEffects.bloodListener(), this);
        }


    }

    @Override
    public void onDisable(){
        //Fired when the server stops and disables all plugins

        // Prevent memory leaks.
        // Make sure this is executed as last thing on disable!
        plugin = null;
    }

}