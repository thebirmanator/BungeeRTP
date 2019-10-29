package me.thebirmanator.slowfallrtp.bungee;

import net.md_5.bungee.api.plugin.Plugin;

public class BungeeMain extends Plugin {

    public void onEnable() {
        getProxy().registerChannel("BungeeCord");
        getProxy().getPluginManager().registerListener(this, new PluginMessageReceiverListener());
    }
}
