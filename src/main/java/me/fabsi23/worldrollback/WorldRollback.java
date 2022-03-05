package me.fabsi23.worldrollback;

import java.io.File;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import me.fabsi23.worldrollback.config.WorldRollbackConfig;
import me.fabsi23.worldrollback.utils.FileUtils;
import me.fabsi23.worldrollback.utils.Logging;

public class WorldRollback extends JavaPlugin {

	/**
	 * author: Fabsi23 Date: 04.03.2022 - 05.03.2022 (DMY) Last edited: 05.03.2022
	 */

	private static WorldRollback instance;

	@Override
	public void onEnable() {

		long current = System.currentTimeMillis();
		WorldRollback.instance = this;
		loadConfigurations();
		if (Bukkit.getWorlds().isEmpty()) // won't be empty if started by /reload
			resetAllWorlds();
		Logging.logInfo("Plugin activated! Starting took " + (System.currentTimeMillis() - current) + " ms.");
	}

	private void resetAllWorlds() {
		Map<String, String> resetMap = WorldRollbackConfig.getResetWorlds();
		for (Entry<String, String> entry : resetMap.entrySet()) {
			String world = entry.getKey();
			String resetWith = entry.getValue();
			resetWorld(world, resetWith);
		}
	}

	private void resetWorld(String world, String resetWith) {
		if (resetWith.equals("WIPE"))
			Logging.logInfo("Starting reset of " + world);
		else
			Logging.logInfo("Starting reset of " + world + " and loading " + resetWith);

		File worldFolder = new File(world);
		if (!worldFolder.isDirectory()) {
			Logging.logWarning("Could not reset " + world + " because no world with the given name exists");
			return;
		}
		FileUtils.deleteFilesFromFolder(worldFolder);
		Logging.logInfo("Finished wiping data of " + world);

		if (resetWith.equals("WIPE"))
			return; // no need to copy existing world - so step out

		File resetFolder = new File(resetWith);
		FileUtils.copyFolder(resetFolder, worldFolder);
		Logging.logInfo("Finished copying data from " + resetFolder + " to " + world);
	}

	@Override
	public void onDisable() {
		Logging.logInfo("Plugin deactivated!");
	}

	private void loadConfigurations() {
		saveDefaultConfig();
	}

	public static WorldRollback getReference() {
		return instance;
	}
}