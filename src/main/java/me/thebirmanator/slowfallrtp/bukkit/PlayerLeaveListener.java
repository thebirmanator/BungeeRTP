package me.thebirmanator.slowfallrtp.bukkit;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeaveListener implements Listener {

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        // to prevent the set from filling up if syncing somehow fails
        MessageListener.uuids.remove(event.getPlayer().getUniqueId().toString());
    }
}
