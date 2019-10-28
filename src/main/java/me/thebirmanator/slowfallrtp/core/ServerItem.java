package me.thebirmanator.slowfallrtp.core;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import me.thebirmanator.slowfallrtp.bukkit.Main;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ServerItem {

    private ItemStack icon;
    private String server;

    private static List<ServerItem> serverItems = new ArrayList<>();

    public static String channel = "BungeeCord";
    public static String subChannel = "RTP";

    public ServerItem(ItemStack icon, String server) {
        this.icon = icon;
        this.server = server;

        serverItems.add(this);
    }

    public ItemStack getIcon() {
        return icon;
    }

    public String getServer() {
        return server;
    }

    public void sendConnectRequest(Player player) {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        // the channel
        output.writeUTF(subChannel);
        // who this is affecting
        output.writeUTF(player.getUniqueId().toString());
        // which realm to send them to
        output.writeUTF(server);
        player.sendPluginMessage(Main.getInstance(), "BungeeCord", output.toByteArray());
    }

    public static ServerItem getServerItem(ItemStack item) {
        for(ServerItem serverItem : serverItems) {
            if(serverItem.getIcon().equals(item)) {
                return serverItem;
            }
        }
        return null;
    }

    public static List<ServerItem> getServerItems() {
        return serverItems;
    }
}
