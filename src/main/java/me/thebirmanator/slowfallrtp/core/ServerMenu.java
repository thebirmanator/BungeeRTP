package me.thebirmanator.slowfallrtp.core;

import me.thebirmanator.slowfallrtp.bukkit.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;

public class ServerMenu {

    private static Inventory inventory;

    public static Inventory getInventory() {
        if(inventory == null) {
            inventory = Bukkit.createInventory(null, 27, ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("menu-name")));
            for(int i = 0; i < ServerItem.getServerItems().size(); i++) {
                inventory.setItem(i, ServerItem.getServerItems().get(i).getIcon());
            }
        }
        return inventory;
    }
}
