# sProxyBroadcast for Spigot 1.20+

sProxyBroadcast is a Minecraft plugin for Paper servers that enables broadcasting messages across all servers connected
via BungeeCord. The plugin supports customizable messages and the option to use color codes.

## Features

- Send messages to all servers in the network using a single command.
- Receive messages and broadcast them to all players on the server.
- Supports color codes (`&a`, `&b`, etc.) for messages.
- Customizable messages and settings through `config.yml`.

## Configuration

After the first launch, the plugin will generate a `config.yml` file in the plugin folder. Here are the default
settings:

```yaml
# Determines whether color codes (e.g., &a, &b) are allowed in messages
allow-colors: true

# Messages displayed to the user
messages:
  usage: "Usage: /tellmsg <message>"
  sent: "Message sent to all servers."
  no-players: "No players online. Unable to send the message to other servers."
```

### Options:

- `allow-colors`: If `true`, color codes (e.g., `&a`) will be translated into Minecraft colors. If `false`, the message
  will appear as plain text.
- `messages.usage`: Message displayed when the command is used incorrectly.
- `messages.sent`: Message displayed when the broadcast is successfully sent.
- `messages.no-players`: Message displayed when no players are online to send the message via the BungeeCord channel.

## Commands

### `/tellmsg <message>`

Broadcast a message to all servers and players in the network.

- **Usage:** `/tellmsg Hello, World!`
- **Permission:** `key.sendmsg`
    - Default: `OP`

## Examples

   ```
   /tellmsg Hello, everyone!
   ```

This will send the message `Hello, everyone!` to all players on all servers.

## Troubleshooting

### Message appears empty or with strange characters

1. Ensure that all servers have the plugin installed and running.
2. Verify that the `bungeecord` setting in `spigot.yml` is set to `true` on all servers.
3. Check server logs for any errors during message handling.

### Colors are not displayed

- Ensure `allow-colors` is set to `true` in `config.yml`.