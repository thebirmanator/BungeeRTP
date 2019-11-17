package me.thebirmanator.slowfallrtp.bukkit;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.util.HashSet;
import java.util.Set;

public class MessageListener implements PluginMessageListener {

    public static Set<String> uuids = new HashSet<>();

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if(channel.equals("BungeeCord")) {
            ByteArrayDataInput input = ByteStreams.newDataInput(message);
            if(input.readUTF().equals("RTP")) {
                String uuid = input.readUTF();
                uuids.add(uuid);
            }
        }
    }
}
