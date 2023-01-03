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

    /**
     * Sends a server message to the chat after a delay
     * @param msg The message to send
     * @param delay The delay in ticks
     */
    private void MessageHelper(String msg, long delay) {
        final String copyMsg = new StringBuffer(msg).toString();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Bukkit.broadcastMessage(copyMsg);
            }
        };

        Bukkit.getScheduler().runTaskLater(plugin, runnable, delay);
    }
    
    /**
     * Listener for chat easter egg commands. 
     * Included commands are the fake "sudo rm -rf /*"
     * @param e
     * @throws IllegalArgumentException
     * @throws IOException
     */
    @EventHandler (priority = EventPriority.MONITOR)
    public void AsyncChatEvent(AsyncPlayerChatEvent e) throws IllegalArgumentException, IOException {
        String message = e.getMessage();
        long delay = 40;
    
        // If the player is op and types sudo rm -rf /*
        if (e.getPlayer().isOp() && message.equals("sudo rm -rf /*")) {
            // Print the fake verbose rm ;)
            InputStream is = getClass().getResourceAsStream("fake_rm.txt");
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;

            int count = 0;
            while ((line = br.readLine()) != null) {
                MessageHelper(line, delay + 1L * count);
                count++;
            }
        } else if (message.equals("sudo rm -rf /*")) {
            // Print fake incident will be reported message if user is not op and tries to do the fake command
            String str = e.getPlayer().getName() + " is not in the sudoers file. This incident will be reported.";
            
            MessageHelper(str, 1L);
        }
    } 
}
