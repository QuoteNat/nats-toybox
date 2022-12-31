package com.nats.toybox.events;
import org.bukkit.event.Listener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
    public void AsyncChatEvent(AsyncPlayerChatEvent e) throws IllegalArgumentException, IOException {
        String message = e.getMessage();
        long delay = 10;
    
        if (message.equals("sudo rm -rf /*")) {
            InputStream is = getClass().getResourceAsStream("fake_rm.txt");
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;

            int count = 0;
            while ((line = br.readLine()) != null) {
                final String copyLine = new StringBuffer(line).toString();
                Runnable msg = new Runnable() {
                    @Override
                    public void run() {
                        Bukkit.broadcastMessage(copyLine);
                    }
                };

                Bukkit.getScheduler().runTaskLater(plugin, msg, delay + 1L * count);
                count++;
            }
        }
    } 
}