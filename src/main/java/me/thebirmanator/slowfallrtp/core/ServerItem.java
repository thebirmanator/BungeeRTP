package me.thebirmanator.slowfallrtp.core;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import me.thebirmanator.slowfallrtp.bukkit.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ServerItem {

    private ItemStack icon;
    private int slot;
    private String server;

    private static List<ServerItem> serverItems;

    //public static String channel = "BungeeCord";
    //public static String subChannel = "RTP";

    public ServerItem(ItemStack icon, int slot, String server) {
        this.icon = icon;
        this.slot = slot;
        this.server = server;

        serverItems.add(this);
    }

    public ItemStack getIcon() {
        return icon;
    }

    public int getSlot() {
        return slot;
    }

    public String getServer() {
        return server;
    }

    public void sendConnectRequest(Player player) {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        // the channel
        output.writeUTF("RTP");
        // who this is affecting
        output.writeUTF(player.getUniqueId().toString());
        // which realm to send them to
        output.writeUTF(server);
        player.sendPluginMessage(Main.getInstance(), "BungeeCord", output.toByteArray());
    }

    public static ServerItem getServerItem(ItemStack item) {
        for (ServerItem serverItem : serverItems) {
            if (serverItem.getIcon().equals(item)) {
                return serverItem;
            }
        }
        return null;
    }

    public static List<ServerItem> getServerItems() {
        return serverItems;
    }

    public static void loadFromConfig() {
        serverItems = new ArrayList<>();
        ConfigurationSection config = Main.getInstance().getConfig().getConfigurationSection("server-selectors");
        for (String server : config.getKeys(false)) {
            ConfigurationSection serverSection = config.getConfigurationSection(server);
            int slot = serverSection.getInt("slot");
            Material material = Material.getMaterial(serverSection.getString("material"));
            String name = ChatColor.translateAlternateColorCodes('&', serverSection.getString("display-name"));
            List<String> description = serverSection.getStringList("description");
            for (String line : description) {
                description.set(description.indexOf(line), ChatColor.translateAlternateColorCodes('&', line));
            }
            ItemStack item = new ItemStack(material);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(name);
            meta.setLore(description);
            item.setItemMeta(meta);
            new ServerItem(item, slot, server);
        }
    }
}
