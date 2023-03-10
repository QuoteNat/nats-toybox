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
 * A listener to output fake/joke responses to easter egg strings. 
 * None of the commands do anything, they're just for humor.
 */
public class FakeCommandListener implements Listener {
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
     * @param e The chat event
     * @throws IllegalArgumentException
     * @throws IOException
     */
    @EventHandler (priority = EventPriority.MONITOR)
    public void AsyncChatEvent(AsyncPlayerChatEvent e) throws IllegalArgumentException, IOException {
        String message = e.getMessage();
        long delay = 40;
        
        // Check for easter egg messages in chat
        switch(message) {
            case "sudo rm -rf /*":
                // If the player is op
                if (e.getPlayer().isOp()) {
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
                } else {
                    // Print fake incident will be reported message if user is not op and tries to do the fake command
                    String str = e.getPlayer().getName() + " is not in the sudoers file. This incident will be reported.";
                    
                    MessageHelper(str, 1L);
                }
                break;
        }

    } 
}
