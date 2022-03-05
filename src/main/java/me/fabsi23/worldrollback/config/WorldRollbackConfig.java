package me.fabsi23.worldrollback.config;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import me.fabsi23.worldrollback.WorldRollback;
import me.fabsi23.worldrollback.utils.Logging;

public class WorldRollbackConfig {

	private static final JavaPlugin plugin = WorldRollback.getReference();
	private static final String CONFIG = "Config.";
	private static final String PREFIX = "Prefix.";
	private static final String SETTING = "Settings.";
	
	private static File file;
	private static FileConfiguration cfg = plugin.getConfig();

	public static String getConsolePrefix() {
		return cfg.getString(CONFIG + PREFIX + "console-prefix");
	}

	public static Map<String, String> getResetWorlds() {
		List<Map<?, ?>> mapList = cfg.getMapList(CONFIG + SETTING + "reset-worlds");
		Map<String, String> returnMap = new HashMap<>();
		for (Map<?, ?> map : mapList) {
			for (Entry<?, ?> entry : map.entrySet()) {
				try {
					returnMap.put((String) entry.getKey(), (String) entry.getValue());					
				} catch(Exception e) {
					Logging.logWarning("Error reading configuration file - please check if all values are Strings");
					e.printStackTrace();
				}
			}
		}
		return returnMap;
	}

	public static void configReload() {
		file = new File(WorldRollback.getReference().getDataFolder().getAbsolutePath(), "config.yml");
		cfg = YamlConfiguration.loadConfiguration(file);
	}
}
