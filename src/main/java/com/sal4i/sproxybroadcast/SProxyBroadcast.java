package com.sal4i.sproxybroadcast;

import org.bukkit.plugin.java.JavaPlugin;

public class SProxyBroadcast extends JavaPlugin {

    private BroadcastManager broadcastManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        broadcastManager = new BroadcastManager(this);
        getCommand("tellmsg").setExecutor(new CommandTellMsg(this, broadcastManager));
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", broadcastManager);
    }

    @Override
    public void onDisable() {
        getServer().getMessenger().unregisterOutgoingPluginChannel(this, "BungeeCord");
        getServer().getMessenger().unregisterIncomingPluginChannel(this, "BungeeCord");
    }
}
