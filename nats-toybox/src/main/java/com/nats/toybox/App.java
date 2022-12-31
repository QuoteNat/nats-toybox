package com.nats.toybox;
import org.bukkit.plugin.java.JavaPlugin;
/**
 * Hello world!
 *
 */
public class App extends JavaPlugin
{
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new CapybaraJoinListener(), this);
    }
    @Override
    public void onDisable() {
        getLogger().info("See you again, SpigotMC!");
    }
}