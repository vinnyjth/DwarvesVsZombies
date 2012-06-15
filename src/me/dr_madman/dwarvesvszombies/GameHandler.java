package me.dr_madman.dwarvesvszombies;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import me.desmin88.mobdisguise.utils.Disguise.MobType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.wimbli.serverevents.ServerEvents;

public class GameHandler {
	private DwarvesVsZombies plugin;
	private ClassHandler classhandler;
	@SuppressWarnings("unused")
	private WorldHandler worldhandler;
	private KitHandler kithandler;
	private int id;


	public GameHandler(DwarvesVsZombies instance) {
		plugin = instance;
		classhandler = new ClassHandler(plugin);
		worldhandler = new WorldHandler(plugin);
		kithandler = new KitHandler(plugin);
		
	}
	
	public void setUpNextGame(){
		plugin.setup = true;
		clearLists();
		plugin.motd = "Setting up next game";
		for(Player player : Bukkit.getServer().getOnlinePlayers()){
			player.kickPlayer(DwarvesVsZombies.prefix + "Setting up next game");
			
		}
		Bukkit.getServer().unloadWorld(Bukkit.getServer().getWorld(plugin.getConfig().getString("option.arenaname")), false);
		worldhandler.deleteWorld(plugin.getConfig().getString("option.arenaname"));
		 plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

				public void run() {
					worldhandler.generateWorld();
					plugin.setup = false;
					plugin.motd = "Waiting for players";
		        }
			}, 200L);
	
	}
	
	public void clearLists(){
		plugin.isDisguised.clear();
		plugin.playerMob.clear();
		plugin.playerRank.clear();
		plugin.isactive = false;
		plugin.playerRespawn.clear();
		plugin.nextMob.clear();
		plugin.voted = 0;
		plugin.tostart = 0;
		plugin.hasVoted.clear();
		for(Player p : Bukkit.getServer().getOnlinePlayers()){
			plugin.playerMob.put(p, Class.Nothing);
		}
		
	}
	
	public void startGame(){
		List<Player> players = new ArrayList<Player>();
		List<String> messages = startGameMessage();
		Random rand = new Random();
		int choice = rand.nextInt(messages.size());
		
		if(Bukkit.getServer().getOnlinePlayers().length < 3){
			plugin.log.info(DwarvesVsZombies.prefix + "Too few players to start");
			return;
		}
		if(!classhandler.checkForPickedClasses()){
			plugin.log.info(DwarvesVsZombies.prefix + "Too few players to start");
			return;
		}
		ServerEvents.displayMessage(messages.get(choice));
		plugin.motd = "Game is active";
		plugin.isactive = true;
		setupGame();
		players.clear();
		for(Player p : Bukkit.getServer().getOnlinePlayers()){
			players.add(p);
		}
		teleportPlayers(players);
		//forttickthread.run(); 
		for(World world: Bukkit.getServer().getWorlds()){
			world.setTime(0);
		}
	}
		/**
		plugin.getServer().getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {

			   public void run() {
			       Bukkit.getServer().broadcastMessage(MobsAndMiners.prefix + "The monsters have entered the world");
			       teleportPlayers(mobs);
			   }
			}, 3600L);
		}
		*/
	
	/**public void setupGame(){
		playerlist = getPlayerList();
		int mobamount;
		if (Bukkit.getServer().getOnlinePlayers().length < 6){
			 mobamount = 1;
		}
		else{
			mobamount = (int) (playerlist.size() * .17);
		}

		Collections.shuffle(playerlist);
		plugin.log.info(playerlist + "");
		int i = 0;
		for(Player player : playerlist){
			if(i <  mobamount){
				mobs.add(player);
			}
			if(i >= mobamount){
				players.add(player);
			}
			i++;
		}
		int j = 0;
		int k = 0;
		plugin.log.info(players + "");
		plugin.log.info(mobs + "");
		for(Player mob : mobs){
			if(j == 0){
				mobhandler.setHerobrine(mob);
				j++;
			}
			else{
				mobhandler.setCaveSpider(mob);
			}
		}
		for(Player player : players){
			if(k == 0){
				mobhandler.setCleanser(player);
				k++;
			}
			else{
				mobhandler.setPlayer(player);
			}
		}
		
	}
	*/

	public void setupGame(){
		Bukkit.getWorld((plugin.getConfig().getString("option.arenaname"))).getSpawnLocation().getBlock().setType(Material.SPONGE);
		plugin.monsterSpawn = Bukkit.getWorld(plugin.getConfig().getString("option.arenaname")).getHighestBlockAt(Bukkit.getWorld(plugin.getConfig().getString("option.arenaname")).getSpawnLocation().add(200, 0, 200)).getLocation();
		List<Player> players = new ArrayList<Player>();
		players.clear();
		for(Player p : Bukkit.getServer().getOnlinePlayers()){
			players.add(p);
		}
		Collections.shuffle(players);
		plugin.log.info(players + "");
		for(Player player : players){

		}
	}
	
	public void teleportPlayers(List<Player> players){
		for(Player p : players){
			p.teleport(Bukkit.getServer().getWorld(plugin.getConfig().getString("option.arenaname")).getSpawnLocation().add(0, 64, 0));
			p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 1200, 100));
		}
	}
	
	public List<Player> getPlayerList(){
		List<Player> plist = new ArrayList<Player>();
		for (Player players : Bukkit.getServer().getOnlinePlayers()){
			plist.add(players);
		}
		return plist;
	}
	
	public boolean checkWinner(){
		List<Player> alivelist = new ArrayList<Player>();
		for (Player player : Bukkit.getServer().getOnlinePlayers()){
			if(plugin.playerMob.get(player).isDwarf()){
				alivelist.add(player);
			}
	
		}
		if (alivelist.size() < 1){
			winMessage();
			return true;
		}
		/**if (alivelist.size() < 1){
			nowinMessage();
			return true;
		}
		if (alivelist.size() == Bukkit.getServer().getOnlinePlayers().length){
			allwinMessage();
			return true;
			
		}
		*/
		else {
			Bukkit.getServer().broadcastMessage(DwarvesVsZombies.prefix + alivelist.size() + " players remain");
			return false;
		}
		
	}
	public void winMessage(){
		Bukkit.getServer().broadcastMessage(DwarvesVsZombies.prefix + "The zombies have won the game!");
		List<String> messages = endGameMessage();
		Random rand = new Random();
		int choice = rand.nextInt(messages.size());
		ServerEvents.displayMessage(messages.get(choice));
		setUpNextGame();
	}
	public void nowinMessage(){
    	final String[] messagelist = {(ChatColor.GOLD + "We have our winner!"),(ChatColor.GOLD + "And the winner is:"),(ChatColor.GOLD + "There is no winner!!!!11!!1!1"),("Prepare to get kicked")};
		this.id = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			int times = 0;

		    public void run() {
		    	Bukkit.getServer().broadcastMessage(messagelist[times]);
		    	times ++;
		    	if (times == 4){
		    		setUpNextGame();
		    		Bukkit.getScheduler().cancelTask(id);
		    	}
		    	

		    }
		}, 1L, 20L);
	}
	public void allwinMessage(){
    	final String[] messagelist = {(ChatColor.GOLD + "We have our winner!"),(ChatColor.GOLD + "And the winner is:"),(ChatColor.GOLD + "EVERYONE!!!!!11!!1!1"),("Prepare to get kicked")};
		this.id = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			int times = 0;

		    public void run() {
		    	Bukkit.getServer().broadcastMessage(messagelist[times]);
		    	times ++;
		    	if (times == 4){
		    		setUpNextGame();
		    		Bukkit.getScheduler().cancelTask(id);
		    	}
		    	

		    }
		}, 1L, 20L);
	}
	public void checkActivePlayer(final Player player){
		if(plugin.isactive){
			plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

				public void run() {
					if(plugin.leftgame.contains(player)){
						plugin.leftgame.remove(player);
						if (plugin.playerMob.containsKey(player)){
							plugin.playerMob.remove(player);
						}
						String pname = player.getName();
						Bukkit.broadcastMessage(DwarvesVsZombies.prefix + pname + " Has forfeited!");
						checkWinner();
					}
		        	
		        }
			}, 1200L);
		}
	}
	public void checkVotes(Player player){
		if(plugin.voted >= plugin.tostart){
			Bukkit.getServer().broadcastMessage(DwarvesVsZombies.prefix + "Enough votes have been recieved! Game is starting!");
			startGame();
		}
		else{
			int votesleft = plugin.tostart-plugin.voted;
			Bukkit.getServer().broadcastMessage(DwarvesVsZombies.prefix + votesleft + " votes are needed to start the game");
		}
	}
	
	public List<String> startGameMessage(){
		List<String> gamemessage = new ArrayList<String>();
		gamemessage.add("[M&M] The game has begun with " +  Bukkit.getServer().getOnlinePlayers().length + " players." );
		gamemessage.add("[M&M] The battle has begun!  " +  Bukkit.getServer().getOnlinePlayers().length + " players are participating." );
		gamemessage.add("[M&M] HOLY GOSH!  " +  Bukkit.getServer().getOnlinePlayers().length + " players are now playing Mobs and Miners." );
		return gamemessage;
	}
	public List<String> endGameMessage(){
		List<String> gamemessage = new ArrayList<String>();
		gamemessage.add("[M&M] HOLY GOSH THE ZOMBIES HAVE  WON THE GAME" );
		gamemessage.add("[M&M] THe battle has concluded. All praise the zombies");
		gamemessage.add("[M&M] Good work zombies you have defeated your advisary" );
		return gamemessage;
	}

}
