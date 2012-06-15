package me.dr_madman.dwarvesvszombies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class DwarvesVsZombies extends JavaPlugin{

	
	Logger log = Logger.getLogger("Minecraft");
	
	public HashMap<Player,Class> playerMob = new HashMap<Player,Class>();
	public HashMap<Player, Boolean> isDisguised = new HashMap<Player, Boolean>();
	public HashMap<Player, Class> nextMob = new HashMap<Player, Class>();
	public HashMap<Player, Integer> playerRank = new HashMap<Player, Integer>();
	public HashMap<Player, Location> playerRespawn = new HashMap<Player, Location>();
	public HashMap<Player, Location> playerPortal = new HashMap<Player, Location>();
	public HashMap<Player, Boolean> hasVoted = new HashMap<Player, Boolean>();
	public HashMap<Player, Long> cooldownTime = new HashMap<Player, Long>();
	public HashMap<Player, String> spiderPosionSelected = new HashMap<Player, String>();
	public Location monsterSpawn = null;
	
	public List<Player> jumping = new ArrayList<Player>();
	public List<Player> wantsToBePortal = new ArrayList<Player>();
	public List<Player> leftgame = new ArrayList<Player>();
	
	public Location portalLocation;
	
	public int tostart = 0;
	public int voted = 0;
	

	public boolean setup = false;
	public boolean isactive = false;
	public boolean isPortalOpen = false;
	
	public String motd = "Game not active";
	public static String prefix = ChatColor.RED + "[DvZ] " + ChatColor.WHITE; 
	
	public DwarvesVsZombiesListener mobsandminerslistener; 
	public ClassHandler mobhandler;
	public GameHandler gamehandler;
	public ItemHandler itemhandler;
	public WorldHandler worldhandler;
	public CompassTracker compasstracker;
	public KitHandler kithandler;
	//public ConfigHandler confighandler;
	private DwarvesVsZombiesCommandExecutor myExecutor;
	
	@Override
	public void onEnable() {
		log.info("Mobs and Miners is now enabled");
		
		mobsandminerslistener = new DwarvesVsZombiesListener(this);
		mobhandler = new ClassHandler(this);
		myExecutor = new DwarvesVsZombiesCommandExecutor(this);
		gamehandler = new GameHandler(this);
		itemhandler = new ItemHandler(this);
		worldhandler = new WorldHandler(this);
		compasstracker = new CompassTracker(this);
		kithandler = new KitHandler(this);
		//confighandler = new ConfigHandler(this);
		Plugin serverevents = this.getServer().getPluginManager().getPlugin("ServerEvents");
		if (serverevents != null) {
			if (!serverevents.isEnabled()) {
				getServer().getPluginManager().enablePlugin(serverevents);
			}
		} else {
			log.info("ServerEvents plugin not installed. Disabling plugin.");
			this.getServer().getPluginManager().disablePlugin(this);
		}
		getCommand("info").setExecutor(myExecutor);
		getCommand("votestart").setExecutor(myExecutor);
		getCommand("kill").setExecutor(myExecutor);
		getCommand("start").setExecutor(myExecutor);
		getCommand("md").setExecutor(myExecutor);
		getCommand("dwarf").setExecutor(myExecutor);
		getCommand("zombie").setExecutor(myExecutor);


        getServer().getPluginManager().registerEvents(mobsandminerslistener, this);
        getServer().getPluginManager().registerEvents(compasstracker, this);
        //confighandler.initDefualts();
        getConfig().addDefault("option.arenaname", "arena1");
        getConfig().addDefault("option.arenasize", 500);
        getConfig().addDefault("option.borderkill", true);
		getConfig().options().copyDefaults();
		saveConfig();
		kithandler.checkFolder();
		kithandler.checkVIPFolder();
		try {
			kithandler.initKits();
			kithandler.initVIP();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		getServer().createWorld(new WorldCreator(getConfig().getString("option.arenaname")));
		gamehandler.clearLists();
	}
	@Override
	public void onDisable() {
		myExecutor = new DwarvesVsZombiesCommandExecutor(this);
		log.info("Mobs and Miners is now disabled");
	}

}
