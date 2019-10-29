package me.thebirmanator.slowfallrtp.bukkit;

import java.util.List;
import java.util.Set;

import me.thebirmanator.slowfallrtp.core.RTPWorld;
import me.thebirmanator.slowfallrtp.core.ServerItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	private RandomTPCommand rtpcmd = new RandomTPCommand(this);

	private static Main instance;
	
	public void onEnable() {
		instance = this;
		saveDefaultConfig();
		loadTheConfig();

		ServerItem.loadFromConfig();

		getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		getServer().getPluginManager().registerEvents(new InventoryClickListener(), this);
		getCommand(rtpcmd.randomtp).setExecutor(rtpcmd);

		getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "SlowFallRTP enabled!");
	}
	
	public void loadTheConfig() {
		int cooldown = getConfig().getInt("cooldown-in-seconds");
		RTPWorld.setCooldown(cooldown);
		List<String> materialStrings = getConfig().getStringList("blacklisted-blocks");
		for(String materialString : materialStrings) {
			if(Material.getMaterial(materialString) != null) {
				Material material = Material.getMaterial(materialString);
				RTPWorld.getBlacklistedMaterials().add(material);
			} else {
				getServer().getConsoleSender().sendMessage(ChatColor.RED + "Material name " + ChatColor.GRAY + materialString + ChatColor.RED + " not found.");
				break;
			}
		}
		Set<String> worldStrings = getConfig().getConfigurationSection("worlds").getKeys(false);
		for(String worldString : worldStrings) {
			if(Bukkit.getWorld(worldString) != null) {
				World world = Bukkit.getWorld(worldString);
				int maxHeight = getConfig().getInt("worlds." + worldString + ".max-height");
				int min = getConfig().getInt("worlds." + worldString + ".spawn-range.min");
				int max = getConfig().getInt("worlds." + worldString + ".spawn-range.max");
				int exmin = getConfig().getInt("worlds." + worldString + ".spawn-range.exclude-min");
				int exmax = getConfig().getInt("worlds." + worldString + ".spawn-range.exclude-max");
				
				RTPWorld rtpWorld = new RTPWorld(world, maxHeight, min, max, exmin, exmax);
				rtpWorld.addRTPWorld();
				
			} else {
				getServer().getConsoleSender().sendMessage(ChatColor.RED + "World name " + ChatColor.GRAY + worldString + ChatColor.RED + " not found.");
				break;
			}
		}
	}

	public static Main getInstance() {
		return instance;
	}
}
