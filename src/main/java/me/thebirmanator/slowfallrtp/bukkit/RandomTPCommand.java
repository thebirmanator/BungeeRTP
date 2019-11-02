package me.thebirmanator.slowfallrtp.bukkit;

import java.util.Random;

import games.indigo.cooldownapi.Cooldown;
import me.thebirmanator.slowfallrtp.core.RTPWorld;
import me.thebirmanator.slowfallrtp.core.ServerMenu;
import org.bukkit.Bukkit;
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
								Location loc = findLocation(rtpworld);

								int cooldownLength = RTPWorld.getCooldown();
								int cooldownTicks = cooldownLength * 20;
								player.teleport(loc);
								Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
									@Override
									public void run() {
										player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, cooldownTicks + 60, 2, true, false, false), true);
										player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, cooldownTicks + 60, 100, true, false, false), true);
									}
								}, 20);

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
			int x;
			int z;
			boolean isHigherRange;
			
			isHigherRange = random.nextBoolean();
			if(isHigherRange) {
				x = random.nextInt((rtpworld.getTpMax() - rtpworld.getTpExcludedMax()) + 1) + rtpworld.getTpExcludedMax();
			} else {
				x = random.nextInt((rtpworld.getTpExcludedMin() - rtpworld.getTpMin()) + 1) + rtpworld.getTpMin();
			}

			int y = 255;
			
			isHigherRange = random.nextBoolean();
			if(isHigherRange) {
				z = random.nextInt((rtpworld.getTpMax() - rtpworld.getTpExcludedMax()) + 1) + rtpworld.getTpExcludedMax();
			} else {
				z = random.nextInt((rtpworld.getTpExcludedMin() - rtpworld.getTpMin()) + 1) + rtpworld.getTpMin();
			}

			// move down the y value until you reach a solid block
			loc = new Location(rtpworld.getBukitWorld(), x, y, z);
			while(loc.getBlock().getType() == Material.AIR) {
				loc.setY(loc.getY() - 1);
			}
			
			// if the block isn't blacklisted, keep going/return the location
			if(!RTPWorld.getBlacklistedMaterials().contains(loc.getBlock().getType())) {
				loc.setY(loc.getY() + 20);
				break;
			}
		}
		return loc;
	}

}
