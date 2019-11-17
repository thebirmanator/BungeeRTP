package me.thebirmanator.slowfallrtp.bukkit;

import net.craftersland.data.bridge.api.events.SyncCompleteEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class SyncListener implements Listener {

    @EventHandler
    public void onSync(SyncCompleteEvent event) {
        Player player = event.getPlayer();
        if(MessageListener.uuids.contains(player.getUniqueId().toString())) {
            player.performCommand("rtp");
            MessageListener.uuids.remove(player.getUniqueId().toString());
        }
    }
}
