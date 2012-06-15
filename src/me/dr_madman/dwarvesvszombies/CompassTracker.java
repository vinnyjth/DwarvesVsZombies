package me.dr_madman.dwarvesvszombies;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;


public class CompassTracker implements Listener{
	public final Logger logger = Logger.getLogger("Minecraft");
	public static ArrayList<String> toggleActivated = new ArrayList<String>();
	private static DwarvesVsZombies plugin;
	private static ClassHandler classhandler;

	public CompassTracker(DwarvesVsZombies instance) {
		plugin = instance;
		classhandler = new ClassHandler(plugin);
	}
	@EventHandler 
	public void onCompassRightClick(PlayerInteractEvent event){
		Player player = event.getPlayer();
		String pname = player.getName();
		if(player.getInventory().getItemInHand().getType() == Material.COMPASS){
			if (toggleActivated.contains(pname)){	
				toggleActivated.remove(pname);
				player.sendMessage(DwarvesVsZombies.prefix + "You are no longer tracking the nearest player");
			}
			else{	
				if(plugin.playerMob.containsKey(player)){
					if(plugin.playerMob.get(player) == Class.Builder ||plugin.playerMob.get(player) == Class.Blacksmith || plugin.playerMob.get(player) == Class.Blacksmith  ){
						track(player, pname, "You are now tracking ", "You are now being tracked");
					}
					else{
						track(player, pname, "You are on the scent of ", "Your scent has been picked up");
					}
				}
			}
		}
	}
	
	public void track(Player player, String pname, String messagetracking, String messagetracked){
		Player[] listOfPlayers;
		listOfPlayers = Bukkit.getServer().getOnlinePlayers();
		plugin.log.info(listOfPlayers + "");
		List<String> players = new ArrayList<String>();
		for (Player par : listOfPlayers){
			String parname = par.getName();
			players.add(parname);
		}
		players.remove(player.getName());
		Iterator<String> itr = players.iterator();
		while(itr.hasNext()){
			String element = itr.next();
			if (plugin.playerMob.containsKey(Bukkit.getServer().getPlayer(element))){
				Player p = Bukkit.getServer().getPlayer(element);
				if(classhandler.isDwarf(p)){
						itr.remove();
				}
			}
		}
		if(players.size() == 0){
			player.sendMessage(DwarvesVsZombies.prefix + "There are no players to track!");
		}
		else {
			toggleActivated.add(pname);
			Player newplayer = null;
			String newplayername = null;
			double lowvalue = 100000000;
			for (String p : players){
				Player pl = Bukkit.getServer().getPlayer(p);
				double tempval = player.getLocation().distance(pl.getLocation());
				if(tempval < lowvalue){
					lowvalue = tempval;
					newplayer = pl;
					newplayername = p;
				}		
			}
			player.sendMessage(DwarvesVsZombies.prefix + messagetracking + newplayername);
			newplayer.sendMessage(DwarvesVsZombies.prefix + messagetracked);
			CompassTrackerUpdater.setWatcher(player, newplayer);
		}
	}
}
