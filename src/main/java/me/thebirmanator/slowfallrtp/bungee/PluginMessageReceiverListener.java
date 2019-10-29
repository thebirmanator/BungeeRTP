package me.thebirmanator.slowfallrtp.bungee;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.concurrent.TimeUnit;

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
                    String uuid = input.readUTF();
                    ServerInfo server = ProxyServer.getInstance().getServerInfo(input.readUTF());
                    player.connect(server);

                    // send message to the server they're connecting to
                    ByteArrayDataOutput output = ByteStreams.newDataOutput();
                    // subchannel
                    output.writeUTF("RTP");
                    // player this affects
                    output.writeUTF(uuid);
                    // command to do
                    output.writeUTF("rtp");
                    ProxyServer.getInstance().getScheduler().schedule(BungeeMain.getInstance(), new Runnable() {
                        @Override
                        public void run() {
                            server.sendData("BungeeCord", output.toByteArray());
                        }
                    }, 20, TimeUnit.MILLISECONDS);
                }
            }

        }
    }
}
