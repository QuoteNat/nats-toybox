package com.nats.toybox.events;
import org.bukkit.event.Listener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
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
    
        // If the player is op and types sudo rm -rf /*
        if (e.getPlayer().isOp() && message.equals("sudo rm -rf /*")) {
            // Print the fake verbose rm ;)
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
        } else if (message.equals("sudo rm -rf /*")) {
            final String str = new StringBuffer(e.getPlayer().getName() + " is not in the sudoers file. This incident will be reported.").toString();
            Runnable msg = new Runnable() {
                @Override
                public void run() {
                    Bukkit.broadcastMessage(str);
                }
            };

            Bukkit.getScheduler().runTaskLater(plugin, msg, 1L);
        }
    } 
}
