package me.dr_madman.dwarvesvszombies;

import me.dr_madman.dwarvesvszombies.ClassHandler;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DwarvesVsZombiesCommandExecutor implements CommandExecutor{
	private DwarvesVsZombies plugin;
	private ClassHandler classhandler;
	private GameHandler gamehandler;
	@SuppressWarnings("unused")
	private DwarvesVsZombiesListener mobsandminerslistener;
	@SuppressWarnings("unused")
	private KitHandler kithandler;


	public DwarvesVsZombiesCommandExecutor(DwarvesVsZombies instance) {
		plugin = instance;
		classhandler = new ClassHandler(plugin);
		gamehandler = new GameHandler(plugin);
		mobsandminerslistener = new DwarvesVsZombiesListener(plugin);
		kithandler = new KitHandler(plugin);
		
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = null;
		if (sender instanceof Player) {
			player = (Player) sender;
		}
		if(cmd.getName().equalsIgnoreCase("info")){
			player.sendMessage(DwarvesVsZombies.prefix  + "++++++++++++ Mobs and Miners INFO ++++++++++++" );
			player.sendMessage(DwarvesVsZombies.prefix  + "Plugin created by Dr_MadMan");
			player.sendMessage(DwarvesVsZombies.prefix  + "This server is owned by Dr_MadMan");
			player.sendMessage(DwarvesVsZombies.prefix  + "Bugs? Report to @Dr_MadMan on twitter");
			player.sendMessage(DwarvesVsZombies.prefix  + "The game is played much like a traditional Minecraft tournament, with one key difference, when you die you become a mob. Before the round a sixth of the players are selected to be mobs. When enough votes are recieved or the server is full, the round will start. The players are dropped into the world a set amount of time before the mobs. Each time a mob kills a player, a player becomes infected and turns into a mob. The amount of kills you recieve as a mob determines the level of mob you can be. This is team based, so it is encouraged that players work together in order to survive the longest. The last player alive wins. Good luck and have fun! ");
			
		}
		if (cmd.getName().equalsIgnoreCase("votestart")){
			if(Bukkit.getServer().getOnlinePlayers().length < 6){
				player.sendMessage(DwarvesVsZombies.prefix + "You can't vote to start, not enough players");
				return true;
			}
			if(!classhandler.checkForPickedClasses()){
				player.sendMessage(DwarvesVsZombies.prefix + "You can't vote to start, not all players have selected a class");
				return true;
			}
			if(plugin.hasVoted.containsKey(player)){
				if(plugin.hasVoted.get(player) == true){
					player.sendMessage(DwarvesVsZombies.prefix + "You have already voted!");
					return false;
				}
			}
			player.sendMessage(DwarvesVsZombies.prefix + "You have voted to start");
			plugin.voted++;
			gamehandler.checkVotes(player);	
			plugin.hasVoted.put(player, true);
			return true;
		}
		if (cmd.getName().equalsIgnoreCase("kill")){
			if(plugin.playerMob.containsKey(player)){
				if(!classhandler.isDwarf(player)){
					player.setHealth(0);
					return true;
				}
				return true;
			}
			
		}
		if(cmd.getName().equalsIgnoreCase("start")){
			gamehandler.startGame();
		}
		if (cmd.getName().equalsIgnoreCase("md")){
			player.sendMessage("nice try");
			return true;
			
		}
		if(cmd.getName().equalsIgnoreCase("dwarf")){
			plugin.playerMob.put(player, Class.Nothing);
			classhandler.distributeDwarfClasses(player);
			return true;
		}
		if(cmd.getName().equalsIgnoreCase("zombie")){
			plugin.playerMob.put(player, Class.Nothing);
			classhandler.distributeZombieClasses(player);
			return true;
		}
		
		return false;
	}

}
