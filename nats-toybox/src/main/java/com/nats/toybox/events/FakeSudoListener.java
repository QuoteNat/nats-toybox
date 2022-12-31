package com.nats.toybox.events;
import org.bukkit.event.Listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.nats.toybox.App;

/**
 * A listener to output fake messages inspired by real unix commands.
 */
public class FakeSudoListener implements Listener {
    private final App plugin = App.getPlugin(App.class);
    
    @EventHandler (priority = EventPriority.MONITOR)
    public void AsyncChatEvent(AsyncPlayerChatEvent e) {
        String message = e.getMessage();
        long delay = 1;
        Runnable msg = new Runnable() {
            @Override
            public void run() {
                Bukkit.broadcastMessage("Removing world files...");
            }
        };
        if (message.equals("sudo rm -rf /*")) {
            Bukkit.getLogger().info("Made it to runTaskLater call");
            Bukkit.getScheduler().runTaskLater(plugin, msg, delay);
            // Bukkit.broadcastMessage("Removing world files...");
        }
    } 
}
