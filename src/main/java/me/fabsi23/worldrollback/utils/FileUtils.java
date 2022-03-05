package me.fabsi23.worldrollback.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.common.io.Files;

public class FileUtils {

	public static List<File> getFilesAndDirectoriesInFolder(File folder) {
		List<File> fileList = new ArrayList<>();

		for (File file : folder.listFiles()) {
			if (file.isDirectory())
				fileList.addAll(getFilesAndDirectoriesInFolder(file));
			else
				fileList.add(file);
		}
		return fileList;
	}

	public static void deleteFilesFromFolder(File folder) {
		List<File> files = FileUtils.getFilesAndDirectoriesInFolder(folder);
		for (File file : files) {
			if (!file.delete()) {
				Logging.logWarning("Couldn't delete " + file.getPath());
			}
		}
	}

	public static void copyFolder(File from, File to) {
		if (!from.isDirectory()) {
			Logging.logWarning("Could not copy content of " + from + " to " + to + " because there is no folder named " + from);
			return;
		}
		if (!to.isDirectory()) {
			Logging.logWarning("Could not copy content of " + from + " to " + to + " because there is no folder named " + to);
			return;
		}
		List<File> files = getFilesAndDirectoriesInFolder(from);
		for (File file : files) {
			String path = file.getPath();
			File copyTo = new File(to + path.substring(path.indexOf(from.getName()) + from.getName().length()));
			try {
				Files.copy(file, copyTo);
			} catch (IOException e) {
				Logging.logWarning("Couldn't copy " + file.getPath() + " to " + copyTo);
				e.printStackTrace();
			}
		}
	}

}
