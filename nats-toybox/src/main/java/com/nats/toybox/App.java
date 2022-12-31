package com.nats.toybox;
import org.bukkit.plugin.java.JavaPlugin;

import com.nats.toybox.events.CapybaraJoinListener;
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
    // @Override
    // public void onDisable() {
    // }
}