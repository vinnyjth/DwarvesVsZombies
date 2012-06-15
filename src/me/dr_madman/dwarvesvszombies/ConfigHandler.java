package me.dr_madman.dwarvesvszombies;

import org.bukkit.configuration.file.FileConfiguration;

public class ConfigHandler {
	private DwarvesVsZombies plugin;
	public ConfigHandler(DwarvesVsZombies plugin) {
		this.plugin = plugin;
	}
	private FileConfiguration cfg = plugin.getConfig();
	
	public void initDefualts(){
		cfg.addDefault("options.arenaname", "arena");
		cfg.options().copyDefaults();
		plugin.saveConfig();
		
	}

}
