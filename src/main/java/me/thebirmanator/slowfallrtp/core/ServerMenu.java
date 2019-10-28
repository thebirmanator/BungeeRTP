package me.thebirmanator.slowfallrtp.core;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

public class ServerMenu {

    private static Inventory inventory;

    public static Inventory getInventory() {
        if(inventory == null) {
            inventory = Bukkit.createInventory(null, 27, "Servers");
            for(int i = 0; i < ServerItem.getServerItems().size(); i++) {
                inventory.setItem(i, ServerItem.getServerItems().get(i).getIcon());
            }
        }
        return inventory;
    }
}
