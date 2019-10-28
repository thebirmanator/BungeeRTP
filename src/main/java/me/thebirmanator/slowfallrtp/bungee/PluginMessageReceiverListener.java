package me.thebirmanator.slowfallrtp.bungee;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import me.thebirmanator.slowfallrtp.core.ServerItem;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PluginMessageReceiverListener implements Listener {

    @EventHandler
    public void onReceive(PluginMessageEvent event) {
        if(event.getTag().equalsIgnoreCase(ServerItem.channel)) {
            ByteArrayDataInput input = ByteStreams.newDataInput(event.getData());
            String subChannel = input.readUTF();
            if(subChannel.equalsIgnoreCase(ServerItem.subChannel)) {
                if(event.getReceiver() instanceof ProxiedPlayer) {
                    ProxiedPlayer player = (ProxiedPlayer) event.getReceiver();
                    //TODO: make this not hard coded
                    player.connect(ProxyServer.getInstance().getServerInfo("crimson"));
                }
            }

        }
    }
}
