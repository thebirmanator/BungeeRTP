package me.thebirmanator.slowfallrtp.bukkit;

import me.thebirmanator.slowfallrtp.core.ServerItem;
import me.thebirmanator.slowfallrtp.core.ServerMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if(event.getClickedInventory() != null && event.getClickedInventory().equals(ServerMenu.getInventory())) {
            event.setCancelled(true);
            if(event.getCurrentItem() != null && event.getCurrentItem().getType() != Material.AIR) {
                 ServerItem serverItem = ServerItem.getServerItem(event.getCurrentItem());
                 if(serverItem != null && event.getWhoClicked() instanceof Player) {
                     serverItem.sendConnectRequest((Player) event.getWhoClicked());
                 }
            }
        }
    }
}
