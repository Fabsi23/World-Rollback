package me.fabsi23.worldrollback.utils;

import org.bukkit.Bukkit;

import me.fabsi23.worldrollback.config.WorldRollbackConfig;

public class Logging {

	public static void logWarning(String message) {
		Bukkit.getLogger().warning(WorldRollbackConfig.getConsolePrefix() + " " + message);
	}

	public static void logInfo(String message) {
		Bukkit.getLogger().info(WorldRollbackConfig.getConsolePrefix() + " " +  message);
	}
}
