package com.sal4i.sproxybroadcast;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

/**
 * Manages sending and receiving broadcast messages via the BungeeCord channel.
 */
public class BroadcastManager implements PluginMessageListener {

    private final SProxyBroadcast plugin;

    public BroadcastManager(SProxyBroadcast plugin) {
        this.plugin = plugin;
    }

    /**
     * Sends a broadcast message to all connected servers.
     *
     * @param message The message to send.
     * @return True if a player was available to send the message, false otherwise.
     */
    public boolean sendMessageToAllServers(String message) {
        Player player = Bukkit.getOnlinePlayers().stream().findAny().orElse(null);
        if (player == null) {
            return false;
        }

        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Forward");
        out.writeUTF("ALL");
        out.writeUTF("TellMsgChannel");

        ByteArrayDataOutput messageData = ByteStreams.newDataOutput();
        messageData.writeUTF(message);

        byte[] messageBytes = messageData.toByteArray();
        out.writeShort(messageBytes.length);
        out.write(messageBytes);

        player.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
        return true;
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("BungeeCord")) {
            return;
        }

        try (DataInputStream in = new DataInputStream(new ByteArrayInputStream(message))) {
            String subChannel = in.readUTF();
            if (!subChannel.equals("TellMsgChannel")) {
                return;
            }

            short length = in.readShort();
            byte[] msgBytes = new byte[length];
            in.readFully(msgBytes);

            String receivedMessage = new DataInputStream(new ByteArrayInputStream(msgBytes)).readUTF();
            broadcastMessage(receivedMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Broadcasts a message to all players on the current server.
     *
     * @param message The message to broadcast.
     */
    public void broadcastMessage(String message) {
        boolean allowColors = plugin.getConfig().getBoolean("allow-colors", true);
        Bukkit.getOnlinePlayers()
                .stream()
                .filter(p -> p.hasPermission(plugin.getConfig().getString("permission", "key.seemsg")))
                .forEach(p -> p.sendMessage(allowColors
                ? ChatColor.translateAlternateColorCodes('&', message)
                : message));
    }
}
