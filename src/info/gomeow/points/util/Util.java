package info.gomeow.points.util;

import info.gomeow.points.Points;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Util {

	private static Points plugin;
	private static File pointsFile;
	private static FileConfiguration pointsConfig;
	
	public static void setPlugin(Points p) {
		Util.plugin = p;
	}
	

	public static void reloadPoints() {
	    if (pointsFile == null) {
	    	pointsFile = new File(plugin.getDataFolder(), "points.yml");
	    }
	    pointsConfig = YamlConfiguration.loadConfiguration(pointsFile);
	}

	
	public static FileConfiguration getPoints() {
	    if (pointsConfig == null) {
	        reloadPoints();
	    }
	    return pointsConfig;
	}
	
	public static void savePoints() {
	    if (pointsConfig == null || pointsFile == null) {
	    return;
	    }
	    try {
	        getPoints().save(pointsFile);
	    } catch (IOException ex) {
	        plugin.getLogger().log(Level.SEVERE, "Could not save config to " + pointsConfig, ex);
	    }
	}
	
}
