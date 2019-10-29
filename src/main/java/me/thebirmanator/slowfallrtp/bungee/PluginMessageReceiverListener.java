package me.thebirmanator.slowfallrtp.bungee;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PluginMessageReceiverListener implements Listener {

    @EventHandler
    public void onReceive(PluginMessageEvent event) {
        if(event.getTag().equalsIgnoreCase("BungeeCord")) {
            ByteArrayDataInput input = ByteStreams.newDataInput(event.getData());
            String subChannel = input.readUTF();
            if(subChannel.equalsIgnoreCase("RTP")) {
                if(event.getReceiver() instanceof ProxiedPlayer) {
                    ProxiedPlayer player = (ProxiedPlayer) event.getReceiver();
                    // read twice to get to the server name
                    input.readUTF();
                    String server = input.readUTF();
                    player.connect(ProxyServer.getInstance().getServerInfo(server));
                }
            }

        }
    }
}
