package me.dr_madman.dwarvesvszombies;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.tools.JavaFileManager.Location;

import me.desmin88.mobdisguise.utils.Disguise.MobType;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class DwarvesVsZombiesListener implements Listener {
	private DwarvesVsZombies plugin;
	private ClassHandler classhandler;
	private GameHandler gamehandler;
	private PowerHandler powerhandler;
	private CompassTrackerUpdater compasstrackerupdater;

	public DwarvesVsZombiesListener(DwarvesVsZombies plugin) {
		this.plugin = plugin;
		classhandler = new ClassHandler(plugin);
		gamehandler = new GameHandler(plugin);
		powerhandler = new PowerHandler(plugin);
		compasstrackerupdater = new CompassTrackerUpdater(plugin);

	}

	public void displayMessageDelayed(final Player player, final String string){
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

			public void run() {
				player.sendMessage(string);
				}
	        	
		}, 20);
	
	}
	public void checkForPlayerClassMovement(Player player, Event event){
		org.bukkit.Location toLocation =  ((PlayerMoveEvent) event).getTo();
		org.bukkit.Location fromLocation =  ((PlayerMoveEvent) event).getFrom();
		if(plugin.playerMob.get(player) == Class.Nothing){
			if(fromLocation.getBlockX() != toLocation.getBlockX() || fromLocation.getBlockZ() != toLocation.getBlockZ()){
				player.sendMessage(DwarvesVsZombies.prefix + "You need to pick a class! Right click with an item to select your class of choice.");
				((PlayerMoveEvent) event).setCancelled(true);
				player.teleport(fromLocation.getBlock().getLocation());
			}
		
		}
	}
	 public String shuffle(String input){
	        List<Character> characters = new ArrayList<Character>();
	        for(char c:input.toCharArray()){
	            characters.add(c);
	        }
	        StringBuilder output = new StringBuilder(input.length());
	        while(characters.size()!=0){
	            int randPicker = (int)(Math.random()*characters.size());
	            output.append(characters.remove(randPicker));
	        }
	        return output.toString();
	    }
	 public boolean checkTransmuteItem(Player player, Material transmuteItem, Event event, String message){
		 if(((PlayerInteractEvent) event).hasItem()){
				if(((PlayerInteractEvent) event).getMaterial().equals(transmuteItem)){
					 if(!powerhandler.checkHasUsedPower(player)){
						 return true;
					
					 }
					 else{
							player.sendMessage(message);
							return false;
						}
				}
				else{
					return false;
				}
				
			}
		 	else{
		 		return false;
		 	}
		
	 }
		 
	 public boolean checkTransmutePossibility(Player player, ItemStack itemNeeded, int amountNeeded, String message){
		if(itemNeeded == null && amountNeeded == 0){
			 return true;
		}
		if(player.getInventory().contains(itemNeeded, amountNeeded)){
			return true;
				
		}
		else{
			player.sendMessage(message);
			return false;
		}	
	 }
	
	@EventHandler
	public void playerMove(PlayerMoveEvent event){
		Player player = event.getPlayer();
		checkForPlayerClassMovement(player, event);
		if(plugin.isactive){

			if(player.getWorld() != Bukkit.getServer().getWorld(plugin.getConfig().getString("option.arenaname"))){
				player.teleport(Bukkit.getServer().getWorld(plugin.getConfig().getString("option.arenaname")).getSpawnLocation());

				
			}
		
			
		}
		else{
			if(player.getWorld() != Bukkit.getServer().getWorlds().get(0)){
				player.teleport(Bukkit.getServer().getWorlds().get(0).getSpawnLocation());
			}
		}
				
	}
		
	
	@EventHandler
	public void playerInteract(PlayerInteractEvent event){
		final Player player = event.getPlayer();
		Material item = null;
		if(event.hasItem()){
			 item = event.getMaterial();
		}
		if(!plugin.cooldownTime.containsKey(player)){
			plugin.cooldownTime.put(player, (long) 0);
		}
		
		if(plugin.playerMob.containsKey(player)){

			if(plugin.playerMob.get(player).equals(Class.Nothing)){
				if(event.hasItem()){
					plugin.log.info("Contains key");
					if(item.equals(Material.GLASS_BOTTLE)){
						
						classhandler.setAlchemist(player);
						event.setCancelled(true);
						return;
					}
					if(item.equals(Material.SMOOTH_BRICK)){
					
						classhandler.setBuilder(player);
						event.setCancelled(true);
						return;
					}
					if(item.equals(Material.FURNACE)){
						
						classhandler.setBlacksmith(player);
						event.setCancelled(true);
						return;
					}
					if(item.equals(Material.DIAMOND_CHESTPLATE)){
						classhandler.setTailor(player);
						event.setCancelled(true);
						return;
					}
					if(item.equals(Material.BREAD)){
						classhandler.setBaker(player);
						event.setCancelled(true);
						return;
					}
					if(item.equals(Material.ROTTEN_FLESH)){
						classhandler.setZombie(player);
						event.setCancelled(true);
						return;
					}
					if(item.equals(Material.BOW)){
						classhandler.setSkeleton(player);
						event.setCancelled(true);
						return;
					}
					if(item.equals(Material.TNT)){
						plugin.log.info("Contains tnt");
						classhandler.setCreeper(player);
						event.setCancelled(true);
						return;
					}
					if(item.equals(Material.WEB)){
						classhandler.setSpider(player);
						event.setCancelled(true);
						return;
					}
					if(item.equals(Material.BONE)){
						classhandler.setWolf(player);
						event.setCancelled(true);
						return;
					}
					if(item.equals(Material.SNOW_BALL)){
						classhandler.setSnowGolem(player);
						event.setCancelled(true);
						return;
					}
					if(item.equals(Material.EGG)){
						classhandler.setBroodmother(player);
						event.setCancelled(true);
						return;
					}
					if(item.equals(Material.BLAZE_ROD)){
						classhandler.setBlaze(player);
						event.setCancelled(true);
						return;
					}
					if(item.equals(Material.RAW_FISH)){
						classhandler.setCougar(player);
						event.setCancelled(true);
						return;
					}
					if(item.equals(Material.PORK)){
						classhandler.setBabyHungryPig(player);
						event.setCancelled(true);
						return;
					}
					if(item.equals(Material.EYE_OF_ENDER)){
						classhandler.setEnderman(player);
						event.setCancelled(true);
						return;
					}
					else{

						player.sendMessage(DwarvesVsZombies.prefix + "You need to pick a class");
						event.setCancelled(true);
					}
				}
			}
			if(!plugin.isactive){
				event.setCancelled(true);
				return;
			}
			if(!plugin.playerMob.get(player).isDwarf()){
				if(event.hasItem()){
					if(item == Material.GHAST_TEAR){
						player.setHealth(0);
					}
				}
				if(checkTransmuteItem(player, Material.MAP, event , DwarvesVsZombies.prefix + "You need to wait " + (( plugin.cooldownTime.get(player)) - System.currentTimeMillis())/1000 + " seconds" )){
					powerhandler.teleportToPortal(player);
				}
			}
			if(plugin.playerMob.get(player).equals(Class.Alchemist)){
				if(checkTransmuteItem(player, Material.BOOK, event , DwarvesVsZombies.prefix + "You need to wait " + (( plugin.cooldownTime.get(player)) - System.currentTimeMillis())/1000 + " seconds" )){
					if(checkTransmutePossibility(player, new ItemStack(Material.POTION, 1, (short) 64), 3, "You need at least three mundane potions!")){
						powerhandler.alchemistTransmute(player);
					}
				}
			}
			if(plugin.playerMob.get(player).equals(Class.Builder)){
				if(checkTransmuteItem(player, Material.BOOK, event, DwarvesVsZombies.prefix + "You need to wait " + (( plugin.cooldownTime.get(player)) - System.currentTimeMillis())/1000 + " seconds" )){
					powerhandler.builderTransmute(player);
					
				}
			}
			if(plugin.playerMob.get(player).equals(Class.Blacksmith)){
				if(checkTransmuteItem(player, Material.BOOK, event , DwarvesVsZombies.prefix + "You need to wait " + (( plugin.cooldownTime.get(player)) - System.currentTimeMillis())/1000 + " seconds" )){
					if(checkTransmutePossibility(player, new ItemStack(Material.WATCH, 3), 1, DwarvesVsZombies.prefix + "You need at least three watches! Remember, items must be in stacks of threes")){
						powerhandler.blacksmithTransmute(player);
					}
				}
			}
			if(plugin.playerMob.get(player).equals(Class.Baker)){
				if(checkTransmuteItem(player, Material.BOOK, event , DwarvesVsZombies.prefix + "You need to wait " + (( plugin.cooldownTime.get(player)) - System.currentTimeMillis())/1000 + " seconds" )){
					if(checkTransmutePossibility(player, new ItemStack(Material.CLAY_BRICK, 3), 1, DwarvesVsZombies.prefix + "You need at least three bricks! Remember, items must be in stacks of threes")){
						powerhandler.bakerTransmute(player);
					}
				}
			}
			if(plugin.playerMob.get(player).equals(Class.Tailor)){
				if(checkTransmuteItem(player, Material.BOOK, event , DwarvesVsZombies.prefix + "You need to wait " + (( plugin.cooldownTime.get(player)) - System.currentTimeMillis())/1000 + " seconds" )){
					if(checkTransmutePossibility(player, new ItemStack(Material.INK_SACK, 3, (short) 14), 1, DwarvesVsZombies.prefix + "You need at least three orange dyes! Remember, items must be in stacks of threes")){
						powerhandler.tailorTransmute(player);
					}
				}
			}
			if(plugin.playerMob.get(player).equals(Class.Creeper)){
				if(event.hasItem()){
					if(item.equals(Material.SULPHUR)){
						powerhandler.creeperExplode(player);
					}
				}
			}
			if(plugin.playerMob.get(player).equals(Class.Spider)){
				if(event.hasItem()){
					if(event.getAction() == Action.RIGHT_CLICK_AIR){
						if(item.equals(Material.SPIDER_EYE)){
							powerhandler.changePosion(player);
						}
					}
					
				}
			}
			if(plugin.playerMob.get(player).equals(Class.SnowGolem)){
				if(checkTransmuteItem(player, Material.SNOW_BALL, event , DwarvesVsZombies.prefix + "You need to wait " + (( plugin.cooldownTime.get(player)) - System.currentTimeMillis())/1000 + " seconds" )){
					if(event.getAction() == Action.RIGHT_CLICK_AIR){
						powerhandler.launchSnowBalls(player);
					}
				}
			}
			if(plugin.playerMob.get(player).equals(Class.Broodmother)){
				if(checkTransmuteItem(player, Material.COOKED_FISH, event , DwarvesVsZombies.prefix + "You need to wait " + (( plugin.cooldownTime.get(player)) - System.currentTimeMillis())/1000 + " seconds" )){
					powerhandler.broodMotherPlaceEggs(player);
				}
				if(event.hasItem()){
					if(checkTransmuteItem(player, Material.RAW_FISH, event , DwarvesVsZombies.prefix + "You need to wait " + (( plugin.cooldownTime.get(player)) - System.currentTimeMillis())/1000 + " seconds" )){
						powerhandler.broodMotherShout(player);
					}
				}
			}
			if(plugin.playerMob.get(player).equals(Class.Blaze)){
				if(checkTransmuteItem(player, Material.SLIME_BALL, event , DwarvesVsZombies.prefix + "You need to wait " + (( plugin.cooldownTime.get(player)) - System.currentTimeMillis())/1000 + " seconds" )){
					powerhandler.blazeJump(player);
				}
				
				if(event.hasItem()){
					if(item == Material.IRON_INGOT){
						if(event.getAction() == Action.LEFT_CLICK_BLOCK){
							Block block = event.getClickedBlock();
							block.breakNaturally();
							block.getWorld().createExplosion(block.getLocation(), 0F);
							
						}
					}
				}
			}
			if(plugin.playerMob.get(player).equals(Class.Cougar)){
				if(checkTransmuteItem(player, Material.STRING, event , DwarvesVsZombies.prefix + "You need to wait " + (( plugin.cooldownTime.get(player)) - System.currentTimeMillis())/1000 + " seconds" )){
					player.launchProjectile(Egg.class);
				}
				if(checkTransmuteItem(player, Material.INK_SACK, event , DwarvesVsZombies.prefix + "You need to wait " + (( plugin.cooldownTime.get(player)) - System.currentTimeMillis())/1000 + " seconds" )){
					powerhandler.stealFood(player, player);
				}
			}
			if(plugin.playerMob.get(player).equals(Class.HungryBabyPig)){
				if(event.hasItem()){
					if(item == Material.getMaterial(372)){
						if(player.getLevel() > 6){
							classhandler.setHungryPig(player);
							event.setCancelled(true);
							return;
						}
						else{
							player.sendMessage(DwarvesVsZombies.prefix + "You need to be at least level 7 to transform!");
						}
						
					}
				}
			}
			if(plugin.playerMob.get(player).equals(Class.HungryPig)){
				if(event.hasItem()){
					if(item == Material.getMaterial(372)){ 
						if(player.getLevel() > 13){
							classhandler.setPigman(player);
							event.setCancelled(true);
							return;
						}
						else{
							player.sendMessage(DwarvesVsZombies.prefix + "You need to be at least level 14 to transform!");
						}
						
					}
				}
			}
			if(plugin.playerMob.get(player).equals(Class.Enderman)){
				if(event.hasItem()){
					if(item == Material.BLAZE_ROD){
						if(plugin.wantsToBePortal.contains(player)){
							plugin.wantsToBePortal.remove(player);
							classhandler.setEndermanPortal(player);
							powerhandler.createPortalFrame(player);
							player.teleport(player.getWorld().getHighestBlockAt(plugin.portalLocation).getLocation());
							player.getWorld().strikeLightningEffect(player.getLocation());

						}
						else{
							plugin.wantsToBePortal.add(player);
							player.sendMessage(DwarvesVsZombies.prefix + "Are you sure you want to become a portal? Right or left click to confirm, wait to deny");
							plugin.getServer().getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {

								   public void run() {
								       if(plugin.wantsToBePortal.contains(player)){
								    	   plugin.wantsToBePortal.remove(player);
								       }
								   }
								}, 120L);
						}
					}
					if(item == Material.ENDER_PEARL){
						plugin.getServer().getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {

							   public void run() {
							       player.getInventory().addItem(new ItemStack(Material.ENDER_PEARL));
							   }
							}, 120L);
					}
				}
			}
			if(plugin.playerMob.get(player).equals(Class.EndermanPortal)){
				if(checkTransmuteItem(player, Material.BLAZE_ROD, event , DwarvesVsZombies.prefix + "You need to wait " + (( plugin.cooldownTime.get(player)) - System.currentTimeMillis())/1000 + " seconds" )){
					powerhandler.regenPortal(player);
				}
			}
			
			
		}
		else{
			plugin.playerMob.put(player, Class.Nothing);
			player.sendMessage(DwarvesVsZombies.prefix + "You can now pick a class");
		}
		
	}
	@EventHandler
	public void EntityTargetLivingEntityEvent(EntityTargetLivingEntityEvent event){
	}
	
	@EventHandler
	public void dropItemEvent(PlayerDropItemEvent event){
	}
	@EventHandler 
	public void blockBreakEvent(BlockBreakEvent event){
		Player player = event.getPlayer();
		if(event.getBlock().getType() == Material.SPONGE){
			if(plugin.playerMob.get(player).isDwarf()){
				event.setCancelled(true);
				player.sendMessage(DwarvesVsZombies.prefix + "You can not break the sponge, you need to protect it!");
			}
			if(!plugin.playerMob.get(player).isDwarf()){
				player.sendMessage(DwarvesVsZombies.prefix + "YOU HAVE WON THE GAME");
				Bukkit.getServer().broadcastMessage(DwarvesVsZombies.prefix + "THE SPONGE HAS BEEN BROKEN!");
				gamehandler.winMessage();
			}
		}
		if(plugin.playerMob.get(event.getPlayer()) == Class.HungryPig  || plugin.playerMob.get(event.getPlayer()) == Class.HungryBabyPig){
			if(event.getBlock().getType() != Material.SMOOTH_BRICK){
				event.setCancelled(true);
				player.sendMessage(DwarvesVsZombies.prefix + "You can only mine dwarf placed stone bricks");
				return;
				
			}
			else{
				event.setCancelled(true);
				Block block = event.getBlock();
				block.setType(Material.AIR);
				player.giveExp(2);
				powerhandler.playEffect(block.getLocation());

				
			}
			
		}
	}
	@EventHandler
	public void DamageEvent(EntityDamageEvent event){
		if (event.getCause() == DamageCause.FALL && event.getEntity() instanceof Player && plugin.jumping.contains((Player)event.getEntity())) {
			event.setCancelled(true);
			plugin.jumping.remove((Player)event.getEntity());
			event.getEntity().getWorld().createExplosion((org.bukkit.Location)event.getEntity().getLocation(), 0F);
		}
	}
	@EventHandler
	public void FoodLevelChange(FoodLevelChangeEvent event){
	Entity entity = event.getEntity();
		if(entity instanceof Player){
			Player player = (Player) entity;
			if(!plugin.playerMob.get(player).isDwarf()){
				event.setCancelled(true);
			}
		}
	}
	@EventHandler
	public void HealEvent(EntityRegainHealthEvent event){
	}
	@EventHandler
	public void EntityDamageEntity (EntityDamageByEntityEvent event){
		if(event.getDamager() instanceof Player){
			Player damager = (Player) event.getDamager();
			if(plugin.playerMob.get(damager) == Class.Blaze){
				if(damager.getItemInHand().getType() == Material.IRON_INGOT){
					event.setDamage(30);
				}
			}
			if(event.getEntity() instanceof Player){
				Player player = (Player) event.getEntity();
				if(plugin.playerMob.get(damager).equals(Class.Spider)){
					if(damager.getItemInHand().equals(new ItemStack(Material.SPIDER_EYE)));
					powerhandler.posion(damager, player);
					
				}
				if(plugin.playerMob.get(player).isDwarf() != plugin.playerMob.get(damager).isDwarf()){
					event.setCancelled(true);
				}
			}
			
		}
		if (!(event.getDamager() instanceof Snowball) || event.getDamager().getFallDistance() != 10.2F) return;
		if(event.getDamager() instanceof Snowball){
			event.setDamage(6);
		}
	}
	@EventHandler
	public void PlayerRespawn(PlayerRespawnEvent event){
		Player player = event.getPlayer();
		event.setRespawnLocation(plugin.monsterSpawn);
		classhandler.distributeZombieClasses(player);
	}
	@EventHandler 
	public void PlayerDeathEvent(PlayerDeathEvent event){
		gamehandler.checkWinner();
		plugin.playerMob.put(event.getEntity(), Class.Nothing);
	}
	@EventHandler 
	public void PlayerLogin(PlayerLoginEvent event){
		final Player player = event.getPlayer();

		if(plugin.setup){
			event.disallow(Result.KICK_OTHER, DwarvesVsZombies.prefix + "Game is being setup.");
			return;
		}
		if(plugin.isactive){
			if(plugin.leftgame.contains(player)){
				plugin.leftgame.remove(player);
				event.allow();
				return;
			}
			else{
				event.disallow(Result.KICK_OTHER, "Game in progress");
			}
		
			
		}
		else{
			plugin.getServer().getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {

				   public void run() {
						classhandler.distributeDwarfClasses(player);
				   }
				}, 20L);
			plugin.playerMob.put(player, Class.Nothing);
			 plugin.tostart = (int) (Bukkit.getServer().getOnlinePlayers().length * (.6));
			 displayMessageDelayed(player, DwarvesVsZombies.prefix + "Welcome to the offical Mobs and Miners Server");
			 displayMessageDelayed(player, DwarvesVsZombies.prefix + "Type /votestart to start the game sooner!");
			 displayMessageDelayed(player, DwarvesVsZombies.prefix + "Get info about the server with /info");
			 displayMessageDelayed(player, DwarvesVsZombies.prefix + "Pick a class by right clcking with an item");
				
		}
		
	}
	@EventHandler
	public void PlayerLogout(PlayerQuitEvent event){
		Player player = event.getPlayer();
		CompassTrackerUpdater.removePlayer(player);
		if(plugin.isactive){
			if(classhandler.isDwarf(player)){
				plugin.leftgame.add(player);
				gamehandler.checkActivePlayer(player);
			}

		}
		else{
			 plugin.tostart = (int) (Bukkit.getServer().getOnlinePlayers().length * (.6));
		}
		
		
	}
	@EventHandler
	public void setMOTD(ServerListPingEvent event){
		event.setMotd(plugin.motd);
	}
	@EventHandler
	public void PlayerChat(PlayerChatEvent event){
		Player player = event.getPlayer();
		String name = player.getDisplayName();
		Set<Player> chatrecipients = event.getRecipients();
		String message = event.getMessage();
		if(plugin.isactive){
			List<Player> moblist = new ArrayList<Player>();
			List<Player> playerlist = new ArrayList<Player>();
			for (Player p : Bukkit.getServer().getOnlinePlayers()){
				if(classhandler.isDwarf(p)){
					playerlist.add(p);
				}
				else{
					moblist.add(p);
				}
			}
			if(classhandler.isDwarf(player)){
				for(Player p : moblist){
					chatrecipients.remove(p);
					p.sendMessage("<" + name + "> "+ shuffle(message));
				}
			}
			else{
				for(Player p : playerlist){
					chatrecipients.remove(p);
					p.sendMessage("<" + name + "> "+ shuffle(message));
				}
			}
		}
		
	}
	@EventHandler(priority=EventPriority.LOWEST)
	public void onEntityDamage(EntityDamageEvent event) {
		if (6 <= 0 || event.isCancelled() || !(event instanceof EntityDamageByEntityEvent)) return;

		EntityDamageByEntityEvent evt = (EntityDamageByEntityEvent)event;
		if (!(evt.getDamager() instanceof Snowball) || evt.getDamager().getFallDistance() != 10.2F) return;

		if (!(event.getEntity() instanceof Player)) {
			event.setDamage(6);
		}
		
	}
	@EventHandler
	public void onProjectileHitEvent(ProjectileHitEvent event) {
		 if(event.getEntity() instanceof Egg){
	    	   Projectile egg = (Egg) event.getEntity();
	    	   if(egg.getShooter() instanceof Player){
	    		   Player shooter = (Player) egg.getShooter();
	    		   if(plugin.playerMob.get(shooter) == Class.Cougar){
	    			   powerhandler.disarm(event.getEntity(), shooter);
	    		   }
	    	   }
	       }
	
	/**if(event.getEntity() instanceof Snowball){
			 Entity entity = event.getEntity();
	         org.bukkit.Location loc = entity.getLocation();
	         World world = entity.getWorld();
	         world.createExplosion(loc, 2F);
	           
	       }
	       	*/
	}
	@EventHandler
	public void onEntityExplosion(EntityExplodeEvent event){
		for(Block block : event.blockList()){
			if(block.getType() == Material.SPONGE){
				Bukkit.getServer().broadcastMessage(DwarvesVsZombies.prefix + "THE SPONGE HAS BEEN BROKEN!");
				gamehandler.winMessage();
				return;
			}
		}
	}


	       


}
