package com.nats.toybox;
import org.bukkit.plugin.java.JavaPlugin;

import com.nats.toybox.events.CapybaraJoinListener;
import com.nats.toybox.events.FakeCommandListener;
/**
 * Hello world!
 *
 */
public class App extends JavaPlugin
{
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new CapybaraJoinListener(), this);
        getServer().getPluginManager().registerEvents(new FakeCommandListener(), this);
    }
    // @Override
    // public void onDisable() {
    // }
}