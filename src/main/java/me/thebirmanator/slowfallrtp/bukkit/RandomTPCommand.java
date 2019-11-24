package me.thebirmanator.slowfallrtp.bukkit;

import java.util.Random;

import games.indigo.cooldownapi.Cooldown;
import me.thebirmanator.slowfallrtp.core.RTPWorld;
import me.thebirmanator.slowfallrtp.core.ServerMenu;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class RandomTPCommand implements CommandExecutor {
	
	public String randomtp = "randomtp";
	
	private Main main;
	public RandomTPCommand(Main main) {
		this.main = main;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(args.length == 0 || !args[0].equalsIgnoreCase("reload")) {
				if(player.hasPermission("rtp.command")) {
					if(RTPWorld.getRTPWorld(player.getWorld()) != null) {
						Cooldown cooldown = Cooldown.getCooldown(player, "tpCooldown");
						if(cooldown == null || cooldown.isExpired()) { // if player has no set cooldown, or their cooldown time has expired
							// in the spawn world/server, open menu
							if(player.getWorld().getName().equalsIgnoreCase("spawn")) {
								player.openInventory(ServerMenu.getInventory());
								return true;
							} else {
								RTPWorld rtpworld = RTPWorld.getRTPWorld(player.getWorld());
								Location loc = findLocation(rtpworld).add(0.5, 0, 0.5);

								int cooldownLength = RTPWorld.getCooldown();
								int cooldownTicks = cooldownLength * 20;
								player.teleport(loc);
								player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, cooldownTicks + 60, 2, true, false, false), true);
								player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, cooldownTicks + 60, 100, true, false, false), true);

								player.sendMessage(ChatColor.GREEN + "You have teleported to the coords: " + loc.getBlockX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ());
								new Cooldown(player, "tpCooldown", cooldownLength);
								return true;
							}
						} else {
							player.sendMessage(ChatColor.RED + "Teleport is on cooldown. Time remaining: " + cooldown.getFormattedTimeLeft());
							return true;
						}
					} else {
						player.sendMessage(ChatColor.RED + "Random teleporting is not enabled in this world.");
						return true;
					}
				} else {
					player.sendMessage(ChatColor.RED + "You do not have permission to run this command.");
					return true;
				}
			} else if(args[0].equalsIgnoreCase("reload")) {
				if(player.hasPermission("rtp.command.reload")) {
					main.reloadConfig();
					RTPWorld.getBlacklistedMaterials().clear();
					RTPWorld.getRTPWorlds().clear();
					main.loadTheConfig();
					ServerMenu.reload();
					player.sendMessage(ChatColor.GREEN + "RandomTP reloaded succesfully!");
					return true;
				} else {
					player.sendMessage(ChatColor.RED + "You do not have permission to run this command.");
					return true;
				}
			}
		} else {
			sender.sendMessage(ChatColor.RED + "Only players can use this command.");
			return true;
		}
		return false;
	}

	private Location findLocation(RTPWorld rtpworld) {
		Location loc;
		while(true) {
			Random random = new Random();
			int[] coords = new int[3];
			for(int i = 0; i < 3; i++) {
				if(i == 1) {
					coords[i] = rtpworld.getMaxHeight();
				} else {
					boolean isHigherRange = random.nextBoolean();
					if(isHigherRange) {
						coords[i] = random.nextInt((rtpworld.getTpMax() - rtpworld.getTpExcludedMax()) + 1) + rtpworld.getTpExcludedMax();
					} else {
						coords[i] = random.nextInt((rtpworld.getTpExcludedMin() - rtpworld.getTpMin()) + 1) + rtpworld.getTpMin();
					}
				}
			}
			int height = coords[1];
			loc = new Location(rtpworld.getBukkitWorld(), coords[0], height, coords[2]);

			boolean checkSolid = loc.getBlock().getType() != Material.AIR;
			while (loc.getY() > 0) {
				loc.subtract(0, 1, 0);
				// trying to get to the bottom of a roof
				if (checkSolid) {
					if (loc.getBlock().getType() == Material.AIR) { // got to the bottom; found air
						height = loc.getBlockY();
						checkSolid = false;
					}
				} else {
					if (loc.getBlock().getType() != Material.AIR) { // found floor
						int groundLevel = loc.getBlockY();
						int openSpace = height - groundLevel;
						if(openSpace > 2) { // player can fit between the roof and floor
							if(openSpace < 20) {
								height--;
							} else {
								height = groundLevel + 20;
							}
							break;
						}
					}
				}
			}

			// if the block isn't blacklisted, keep going/return the location
			if(loc.getY() > 0 && !RTPWorld.getBlacklistedMaterials().contains(loc.getBlock().getType())) {
				loc.setY(height);
				break;
			}
		}
		return loc;
	}

}
