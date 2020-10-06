package me.thebirmanator.slowfallrtp.core;

import me.thebirmanator.slowfallrtp.bukkit.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;

import java.util.concurrent.atomic.AtomicInteger;

public class ServerMenu {

    private static Inventory inventory;

    public static Inventory getInventory() {
        if (inventory == null) {
            inventory = Bukkit.createInventory(null, 27, ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("menu-name")));

            AtomicInteger index = new AtomicInteger();
            ServerItem.getServerItems().forEach(serverItem -> inventory.setItem(serverItem.getSlot(), ServerItem.getServerItems().get(index.getAndIncrement()).getIcon()));
        }
        return inventory;
    }

    public static void reload() {
        inventory = null;
    }
}
