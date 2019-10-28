package me.thebirmanator.slowfallrtp.bungee;

import me.thebirmanator.slowfallrtp.core.ServerItem;
import net.md_5.bungee.api.plugin.Plugin;

public class BungeeMain extends Plugin {

    public void onEnable() {
        getProxy().registerChannel(ServerItem.channel);
        getProxy().getPluginManager().registerListener(this, new PluginMessageReceiverListener());
    }
}
