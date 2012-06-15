package me.dr_madman.dwarvesvszombies;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Random;


import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.World.Environment;

public class WorldHandler {
	private DwarvesVsZombies plugin;

	
	public WorldHandler(DwarvesVsZombies instance) {
		plugin = instance;
		
	}
	
	public void generateWorld(){
		String worldname = plugin.getConfig().getString("option.arenaname");
		String worldnameraw = worldname.substring(0, worldname.length()-1);
		int i = Integer.parseInt(worldname.substring(worldname.length()-1, worldname.length()));
		i++;
		String newworldname = worldnameraw + i;
		plugin.getConfig().set("option.arenaname", newworldname);
		plugin.saveConfig();
		final WorldCreator newworld = new WorldCreator(newworldname);
		Random randomGenerator = new Random();
		Long seed = randomGenerator.nextLong();
		newworld.type(WorldType.NORMAL);
		newworld.environment(Environment.NORMAL);
		newworld.generateStructures(true);
		newworld.seed(seed);
		Bukkit.getServer().createWorld(newworld);
		Bukkit.getServer().broadcastMessage("World created!");
		
	}
	public boolean checkForWorld(String world){
		List<World> worlds = plugin.getServer().getWorlds();
		World worldname = plugin.getServer().getWorld(world);
		if (worlds.contains(worldname)){
			return true;
		}
		else{
			return false;
		}
		
	}
	public void deleteWorld(String world){
        String line = "rm -rf " + world;
        try {
            Process child = Runtime.getRuntime().exec(line);
            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(child.getInputStream()));
 
            BufferedReader stdError = new BufferedReader(new
                    InputStreamReader(child.getErrorStream()));
 
            // read the output from the command
            System.out.println("Here is the standard output of the command:\n");
            String s;
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }
 
            // read any errors from the attempted command
            System.out.println("Here is the standard error of the command (if any):\n");
                while ((s = stdError.readLine()) != null) {
            System.out.println(s);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
 
 
    }
}
