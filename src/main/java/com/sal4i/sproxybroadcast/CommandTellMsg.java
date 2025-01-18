package com.sal4i.sproxybroadcast;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Handles the /tellmsg command to broadcast messages across servers.
 */
public class CommandTellMsg implements CommandExecutor {

    private final SProxyBroadcast plugin;
    private final BroadcastManager broadcastManager;

    public CommandTellMsg(SProxyBroadcast plugin, BroadcastManager broadcastManager) {
        this.plugin = plugin;
        this.broadcastManager = broadcastManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        FileConfiguration config = plugin.getConfig();
        if (args.length == 0) {
            sender.sendMessage(config.getString("messages.usage", "Usage: /tellmsg <message>"));
            return true;
        }

        String message = String.join(" ", args);
        broadcastManager.broadcastMessage(message);

        if (broadcastManager.sendMessageToAllServers(message)) {
            sender.sendMessage(config.getString("messages.sent", "Message sent to all servers."));
        } else {
            sender.sendMessage(config.getString("messages.no-players", "No players online. Unable to send the message to other servers."));
        }

        return true;
    }
}
