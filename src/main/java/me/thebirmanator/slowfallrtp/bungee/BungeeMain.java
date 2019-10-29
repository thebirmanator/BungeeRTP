package me.thebirmanator.slowfallrtp.bungee;

import net.md_5.bungee.api.plugin.Plugin;

public class BungeeMain extends Plugin {

    private static BungeeMain instance;

    public void onEnable() {
        instance = this;
        getProxy().registerChannel("BungeeCord");
        getProxy().getPluginManager().registerListener(this, new PluginMessageReceiverListener());
    }

    public static BungeeMain getInstance() {
        return instance;
    }
}
