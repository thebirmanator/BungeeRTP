package me.thebirmanator.slowfallrtp.bukkit;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.util.UUID;

public class MessageListener implements PluginMessageListener {

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if(channel.equals("BungeeCord")) {
            ByteArrayDataInput input = ByteStreams.newDataInput(message);
            if(input.readUTF().equals("RTP")) {
                String uuid = input.readUTF();
                Player p = Bukkit.getPlayer(UUID.fromString(uuid));
                if(p != null) {
                    p.performCommand(input.readUTF());
                }
            }
        }
    }
}
