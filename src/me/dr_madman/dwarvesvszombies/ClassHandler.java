package me.dr_madman.dwarvesvszombies;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.desmin88.mobdisguise.MobDisguise;
import me.desmin88.mobdisguise.api.MobDisguiseAPI;
import me.dr_madman.dwarvesvszombies.ItemHandler;


public class ClassHandler  {
	private DwarvesVsZombies plugin;
	private ItemHandler itemhandler;
	private PowerHandler powerhandler;
	
	public ClassHandler(DwarvesVsZombies instance) {
		plugin = instance;
		itemhandler = new ItemHandler(plugin);
		powerhandler = new PowerHandler(plugin);
	}

	public boolean isDwarf(Player player){
		if(plugin.playerMob.containsKey(player)){
			if(plugin.playerMob.equals(Class.Baker) || plugin.playerMob.equals(Class.Blacksmith) || plugin.playerMob.equals(Class.Tailor) || plugin.playerMob.equals(Class.Alchemist) ||plugin.playerMob.equals(Class.Builder) ){
				return true;
			}
			else{
				return false;
			}
		}
		else{
			return false;
		}
		
	}
	
	public void distributeDwarfClasses(Player player){
		itemhandler.clearInventory(player);
		List<Class> classes = new ArrayList<Class>();
		for(Class allclasses : Class.values()){
			if(allclasses.isDwarf()){
				classes.add(allclasses);
			}
		}
		for(Class c : classes){
			if(powerhandler.checkOdds(c.chance())){
				player.getInventory().addItem(new ItemStack(c.item()));
			}
		}
		
	}
	public void distributeZombieClasses(Player player){
		itemhandler.clearInventory(player);
		List<Class> classes = new ArrayList<Class>();
		for(Class allclasses : Class.values()){
			if(!allclasses.isDwarf()){
				classes.add(allclasses);
			}
		}
		for(Class c : classes){
			if(powerhandler.checkOdds(c.chance())){
				player.getInventory().addItem(new ItemStack(c.item()));
			}
		}
		
	}
	public boolean checkForPickedClasses(){
		for(Player p : Bukkit.getServer().getOnlinePlayers()){
			if(plugin.playerMob.get(p) == Class.Nothing){
				return false;
			}
		}
		return true;
	}
	
	public void setAlchemist(Player player){
		MobDisguiseAPI.undisguisePlayer(player);
		itemhandler.initAlchemist(player);
		plugin.playerMob.put(player, Class.Alchemist);
		player.sendMessage(DwarvesVsZombies.prefix + "You are now an Alchemist");
		
	}
	public void setBuilder(Player player){
		MobDisguiseAPI.undisguisePlayer(player);
		player.sendMessage(DwarvesVsZombies.prefix + "You are now a Builder");
		itemhandler.initBuilder(player);
		plugin.playerMob.put(player, Class.Builder);
		
	}
	public void setBlacksmith(Player player){
		MobDisguiseAPI.undisguisePlayer(player);
		player.sendMessage(DwarvesVsZombies.prefix + "You are now a Blacksmith");
		itemhandler.initBlacksmith(player);
		plugin.playerMob.put(player, Class.Blacksmith);
		
	}
	public void setBaker(Player player){
		MobDisguiseAPI.undisguisePlayer(player);
		player.sendMessage(DwarvesVsZombies.prefix + "You are now a baker");
		itemhandler.initBaker(player);
		plugin.playerMob.put(player, Class.Baker);
		
	}
	public void setTailor(Player player){
		MobDisguiseAPI.undisguisePlayer(player);
		player.sendMessage(DwarvesVsZombies.prefix + "You are now a tailor!");
		itemhandler.initTailor(player);
		plugin.playerMob.put(player, Class.Tailor);
		
	}
	public void setZombie(Player player){
		MobDisguiseAPI.undisguisePlayer(player);
		MobDisguiseAPI.disguisePlayer(player, "zombie");
		player.sendMessage(DwarvesVsZombies.prefix + "You are now a zombie!");
		itemhandler.initZombie(player);
		plugin.playerMob.put(player, Class.Zombie);
		
	}
	public void setSkeleton(Player player){
		MobDisguiseAPI.undisguisePlayer(player);
		MobDisguiseAPI.disguisePlayer(player, "skeleton");
		player.sendMessage(DwarvesVsZombies.prefix + "You are now a skeleton!");
		itemhandler.initSkeleton(player);
		plugin.playerMob.put(player, Class.Skeleton);
		
	}
	public void setCreeper(Player player){
		MobDisguiseAPI.undisguisePlayer(player);
		MobDisguiseAPI.disguisePlayer(player, "creeper");
		player.sendMessage(DwarvesVsZombies.prefix + "You are now a creeper!");
		itemhandler.initCreeper(player);
		plugin.playerMob.put(player, Class.Creeper);
		
	}
	public void setSpider(Player player){
		MobDisguiseAPI.undisguisePlayer(player);
		MobDisguiseAPI.disguisePlayer(player, "cavespider");
		player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 10000000, 4));
		player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 10000000, 2));
		player.sendMessage(DwarvesVsZombies.prefix + "You are now a spider!");
		itemhandler.initSpider(player);
		plugin.playerMob.put(player, Class.Spider);
		plugin.spiderPosionSelected.put(player, "dizzy");
		
	}
	public void setWolf(Player player){
		MobDisguiseAPI.undisguisePlayer(player);
		MobDisguiseAPI.disguisePlayer(player, "wolf");
		player.sendMessage(DwarvesVsZombies.prefix + "You are now a wolf!");
		itemhandler.initWolf(player);
		plugin.playerMob.put(player, Class.Wolf);
		
	}
	public void setSnowGolem(Player player){
		MobDisguiseAPI.undisguisePlayer(player);
		MobDisguiseAPI.disguisePlayer(player, "snowgolem");
		player.sendMessage(DwarvesVsZombies.prefix + "You are now a Snow Golem!");
		itemhandler.initSnowgolem(player);
		plugin.playerMob.put(player, Class.SnowGolem);
		
	}
	public void setBroodmother(Player player){
		MobDisguiseAPI.undisguisePlayer(player);
		MobDisguiseAPI.disguisePlayer(player, "silverfish");
		player.sendMessage(DwarvesVsZombies.prefix + "You are now a Broodmother!");
		itemhandler.initBroodmother(player);
		plugin.playerMob.put(player, Class.Broodmother);
		
	}
	public void setBlaze(Player player){
		MobDisguiseAPI.undisguisePlayer(player);
		MobDisguiseAPI.disguisePlayer(player, "blaze");
		player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 10000000, -4));
		player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 10000000, 5));
		player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 10000000, -50));
		player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 10000000, 2));
		player.sendMessage(DwarvesVsZombies.prefix + "You are now a blaze!");
		itemhandler.initBlaze(player);
		plugin.playerMob.put(player, Class.Blaze);		
	}
	public void setCougar(Player player){
		MobDisguiseAPI.undisguisePlayer(player);
		MobDisguiseAPI.disguisePlayer(player, "ocelot");
		player.sendMessage(DwarvesVsZombies.prefix + "You are now a Cougar!");
		itemhandler.initCougar(player);
		plugin.playerMob.put(player, Class.Cougar);
		
	}
	public void setBabyHungryPig(Player player){
		MobDisguiseAPI.undisguisePlayer(player);
		MobDisguiseAPI.disguisePlayer(player, "pig");
		player.sendMessage(DwarvesVsZombies.prefix + "You are now a baby Hungry Pig!");
		itemhandler.initBabyHungryPig(player);
		plugin.playerMob.put(player, Class.HungryBabyPig);
		
	}
	public void setHungryPig(Player player){
		MobDisguiseAPI.undisguisePlayer(player);
		MobDisguiseAPI.disguisePlayer(player, "pig");
		player.sendMessage(DwarvesVsZombies.prefix + "You are now a Hungry Pig!");
		itemhandler.initHungryPig(player);
		plugin.playerMob.put(player, Class.HungryPig);
		
	}
	public void setPigman(Player player){
		MobDisguiseAPI.undisguisePlayer(player);
		MobDisguiseAPI.disguisePlayer(player, "pigman");
		player.sendMessage(DwarvesVsZombies.prefix + "You are now a Zombie Pigman");
		itemhandler.initPigman(player);
		plugin.playerMob.put(player, Class.Pigman);
		
	}
	public void setEnderman(Player player){
		MobDisguiseAPI.undisguisePlayer(player);
		MobDisguiseAPI.disguisePlayer(player, "enderman");
		player.sendMessage(DwarvesVsZombies.prefix + "You are now an Enderman");
		itemhandler.initEnderman(player);
		plugin.playerMob.put(player, Class.Enderman);
	}
	public void setEndermanPortal(Player player){
		MobDisguiseAPI.undisguisePlayer(player);
		MobDisguiseAPI.disguisePlayer(player, "enderman");
		player.sendMessage(DwarvesVsZombies.prefix + "You are now an Ender Portal");
		itemhandler.initEndermanPortal(player);
		plugin.playerMob.put(player, Class.EndermanPortal);
	}
	public void setSpy(Player player){
		player.sendMessage(DwarvesVsZombies.prefix + "You are now the spy! Cause as much chaos as you can");
		plugin.playerMob.put(player, Class.Spy);
	}

}
