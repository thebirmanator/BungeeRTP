# BungeeRTP
Let players open a menu, choose a world, and teleport randomly within it in style! They float downwards from above the world, allowing a nice view of their surroundings. This is a cross-server (Bungee) friendly version of another older plugin of mine.

## Configuration
There is one configuration file found in the plugins folder. This file contains options for available worlds to teleport in, how far out they can teleport, and an inner zone where they can't teleport, as well as blocks players should not teleport above. It also has options for the teleport menu -- a world name and description.

Configuration files:
- [config.yml](https://github.com/thebirmanator/BungeeRTP/blob/master/src/main/resources/config.yml "Config.yml")

## Commands and Permissions
There are two commands and permissions for this plugin, for basic teleporting access and the ability to reload the plugin's configuration file.

Permission | Description
--- | ---
`rtp.command` | Main command for the plugin; gives access to `/rtp`
`rtp.command.reload` | Allows a player to reload the config using `/rtp reload`
