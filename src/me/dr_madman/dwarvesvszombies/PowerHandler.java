package me.dr_madman.dwarvesvszombies;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class PowerHandler {
	private DwarvesVsZombies plugin;
	
	public PowerHandler(DwarvesVsZombies instance) {
		plugin = instance;
	}
	private Random generator = new Random();
	public boolean checkOdds(Double chance){
		if (Math.random() < chance){
			return true;
		}
		else{
			return false;
		}

	}
	public void playEffect(Location location) {
		World world = location.getWorld();
		int lx = location.getBlockX();
		int ly = location.getBlockY();
		int lz = location.getBlockZ();
		Location loc;

		for (int x = lx-1; x <= lx+1; x++) {
			for (int y = ly; y <= ly+1; y++) {
				for (int z = lz-1; z <= lz+1; z++) {
					for (int i = 0; i <= 8; i+=2) {
						loc = new Location(world, x, y, z);
						world.playEffect(loc, Effect.SMOKE, i);
					}
				}
			}
		}
	}
	
	public void powerCooldown(Player player, Long time){
		plugin.cooldownTime.put(player, System.currentTimeMillis()+ (time*1000));
	}
	public boolean checkHasUsedPower(Player player){
		if(plugin.cooldownTime.containsKey(player)){
			if(plugin.cooldownTime.get(player) > System.currentTimeMillis()){
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
	@SuppressWarnings("deprecation")
	public void alchemistTransmute(Player player){
		List<ItemStack> items = new ArrayList<ItemStack>();
		items.add(new ItemStack(Material.MILK_BUCKET));
		items.add(new ItemStack(Material.BONE, generator.nextInt(12)+3));
		items.add(new ItemStack(Material.SAND, 9));
		player.getInventory().clear(player.getInventory().first(new ItemStack(Material.POTION, 1 , (short) 64)));
		player.getInventory().clear(player.getInventory().first(new ItemStack(Material.POTION, 1 , (short) 64)));
		player.getInventory().clear(player.getInventory().first(new ItemStack(Material.POTION, 1 , (short) 64)));
		player.updateInventory();
		player.giveExp(generator.nextInt(7)+4);
		int i = 0;
		plugin.log.info(i + "");
		while(i < 3){
			if(checkOdds(.50)){
				items.add(new ItemStack(Material.POTION, generator.nextInt(6)+1, (short) 16389));
				i++;
			}
			if(checkOdds(.25)){
				items.add(new  ItemStack(Material.POTION, generator.nextInt(6)+1, (short) 16393));
				i++;
			}
			if(checkOdds(.20)){
				items.add(new  ItemStack(Material.POTION, generator.nextInt(6)+1, (short) 16387));
				i++;
			}
			if(checkOdds(.10)){
				items.add(new  ItemStack(Material.POTION, generator.nextInt(6)+1, (short) 16386));
				i++;
			}
		}
		Location loc = player.getEyeLocation().add(player.getLocation().getDirection());
		for (ItemStack item : items) {
			player.getWorld().dropItem(loc, item);
		}
	}
	public void builderTransmute(final Player player){
		List<ItemStack> items = new ArrayList<ItemStack>();
		items.add(new ItemStack(Material.GLOWSTONE, generator.nextInt(4)+1));
		player.giveExp(generator.nextInt(7)+4);
		int i = 0;
		while (i < 2){
			if(checkOdds(.50)){
				items.add(new ItemStack(Material.SMOOTH_BRICK, generator.nextInt(64)+64));
				i++;
			}
			if(checkOdds(.25)){
				items.add(new ItemStack(Material.SMOOTH_BRICK, generator.nextInt(64)+64, (short) 1  ));
				i++;
			}
			if(checkOdds(.10)){
				items.add(new ItemStack(Material.STONE, generator.nextInt(64)+64 ));
				i++;
			}
			if(checkOdds(.10)){
				items.add(new ItemStack(Material.SMOOTH_BRICK, generator.nextInt(64)+64, (short) 2 ));
				i++;
			}
			if(checkOdds(.5)){
				items.add(new ItemStack(Material.SMOOTH_BRICK, generator.nextInt(64)+64, (short) 3 ));
				i++;
			}
			
		}
		Location loc = player.getEyeLocation().add(player.getLocation().getDirection());
		for (ItemStack item : items) {
			player.getWorld().dropItem(loc, item);
		}
		powerCooldown(player, 30L);
		
	}
	@SuppressWarnings("deprecation")
	public void blacksmithTransmute(final Player player){
		List<ItemStack> items = new ArrayList<ItemStack>();
		items.add(new ItemStack(Material.REDSTONE_ORE, 8));
		items.add(new ItemStack(Material.REDSTONE_ORE, 25));
		player.getInventory().clear(player.getInventory().first(Material.WATCH));
		player.updateInventory();
		player.giveExp(generator.nextInt(7)+4);
		int i = 0;
		while (i < generator.nextInt(3)+2){
			if(checkOdds(.20)){
				items.add(new ItemStack(Material.STRING, 3));
				i++;
			}
			if(checkOdds(.20)){
				items.add(new ItemStack(Material.DIAMOND_SWORD));
				i++;
			}
			if(checkOdds(.20)){
				items.add(new ItemStack(Material.FEATHER, 32));
				i++;
			}
			if(checkOdds(.20)){
				items.add(new ItemStack(Material.DIAMOND_PICKAXE));
				i++;
			}
			if(checkOdds(.20)){
				items.add(new ItemStack(Material.FLINT, 32));
				i++;
			}
			
		}
		Location loc = player.getEyeLocation().add(player.getLocation().getDirection());
		for (ItemStack item : items) {
			player.getWorld().dropItem(loc, item);
		}
		
	}
	@SuppressWarnings("deprecation")
	public void bakerTransmute(final Player player){
		List<ItemStack> items = new ArrayList<ItemStack>();
		items.add(new ItemStack(Material.CLAY_BALL, generator.nextInt(10)+11));
		player.getInventory().clear(player.getInventory().first(Material.CLAY_BRICK));
		player.updateInventory();
		player.giveExp(generator.nextInt(7)+4);
		int i = 0;
		while (i < generator.nextInt(3)+2){
			if(checkOdds(.75)){
				items.add(new ItemStack(Material.COOKIE, generator.nextInt(7)+4));
				i++;
			}
			if(checkOdds(.25)){
				items.add(new ItemStack(Material.CAKE));
				i++;
			}
			
		}
		Location loc = player.getEyeLocation().add(player.getLocation().getDirection());
		for (ItemStack item : items) {
			player.getWorld().dropItem(loc, item);
		}
		
	}
	@SuppressWarnings("deprecation")
	public void tailorTransmute(final Player player){
		List<ItemStack> items = new ArrayList<ItemStack>();
		items.add(new ItemStack(Material.BONE, generator.nextInt(3)+1));
		player.getInventory().clear(player.getInventory().first(new ItemStack(Material.INK_SACK, 3, (short) 14)));
		player.updateInventory();
		player.giveExp(generator.nextInt(7)+4);
		int i = 0;
		while (i < generator.nextInt(3)+2){
			if(checkOdds(.40)){
				items.add(new ItemStack(Material.DIAMOND_HELMET));
				i++;
			}
			if(checkOdds(.30)){
				items.add(new ItemStack(Material.DIAMOND_HELMET));
				i++;
			}
			if(checkOdds(.20)){
				items.add(new ItemStack(Material.DIAMOND_LEGGINGS));
				i++;
			}
			if(checkOdds(.10)){
				items.add(new ItemStack(Material.DIAMOND_CHESTPLATE));
				i++;
			}
			
		}
		Location loc = player.getEyeLocation().add(player.getLocation().getDirection());
		for (ItemStack item : items) {
			player.getWorld().dropItem(loc, item);
		}
		
	}
	
	public void creeperExplode(Player player){
		Location loc = player.getLocation();
		player.getWorld().createExplosion(loc, 5F);
		player.setHealth(0);
		
	}
	public void changePosion(Player player){
		if(plugin.spiderPosionSelected.get(player) == "dizzy"){
			plugin.spiderPosionSelected.put(player, "vision");
			player.sendMessage(DwarvesVsZombies.prefix + "Vision Posion selected!");
			return;
		}
		if(plugin.spiderPosionSelected.get(player) == "vision"){
			plugin.spiderPosionSelected.put(player, "dizzy");
			player.sendMessage(DwarvesVsZombies.prefix + "Dizzy Posion selected!");
			return;
		}
		
	}
	public void posion(Player spider, Player posioned){
		if(plugin.spiderPosionSelected.get(spider) == "dizzy"){
			posioned.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 600, 3));
			posioned.sendMessage(DwarvesVsZombies.prefix + "You have been posioned!");
		}
		if(plugin.spiderPosionSelected.get(spider) == "vision"){
			posioned.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 600, 3));
			posioned.sendMessage(DwarvesVsZombies.prefix + "You have been posioned!");
		}
		
	}
	public void launchSnowBalls(Player player){
		Random rand = new Random();
		Vector mod;
		for (int i = 0; i < 1100; i++) {
			Snowball snowball = player.launchProjectile(Snowball.class);
			snowball.setFallDistance(10.2F); 
			mod = new Vector((rand.nextDouble() - .5) * 1.5, (rand.nextDouble() - .5) * 1.5, (rand.nextDouble() - .5) * 1.5);
			snowball.setVelocity(snowball.getVelocity().add(mod));
		}
		player.playEffect(player.getLocation(), Effect.BOW_FIRE, 0);
		powerCooldown(player, 1L);
	}
	public void broodMotherPlaceEggs(Player player){
		powerCooldown(player, (long) 10);

		boolean containsBlock = false;
		Location loc = player.getLocation();
		 for(double x = (loc.getX() - 3); x <= (loc.getX() + 3); x++){
             for(double y = (loc.getY() - 3); y <= (loc.getY() + 3); y++){
                 for(double z = (loc.getZ() - 3); z <= (loc.getZ() + 3); z++){
            		 Location block = new Location(player.getWorld(), x, y, z);
                	 if(block.getBlock().getType() == Material.SMOOTH_BRICK || block.getBlock().getType() == Material.COBBLESTONE || block.getBlock().getType() == Material.STONE){
                		 block.getBlock().setType(Material.MONSTER_EGGS);
                		 if(!containsBlock){
                    		 player.sendMessage(DwarvesVsZombies.prefix + "You place an egg");
                    		 containsBlock = true;
                		 }

                	 }
                 }
             }
		 }
	}
	public void broodMotherShout(Player player){
		powerCooldown(player, (long) 5);
		Random generator = new Random();
		player.sendMessage(DwarvesVsZombies.prefix + "You scream the cry of the Broodmother!");
		Location loc = player.getLocation();
		 for(double x = (loc.getX() - 3); x <= (loc.getX() + 3); x++){
             for(double y = (loc.getY() - 3); y <= (loc.getY() + 3); y++){
                 for(double z = (loc.getZ() - 3); z <= (loc.getZ() + 3); z++){
            		 Location block = new Location(player.getWorld(), x, y, z);
                	 if(block.getBlock().getType() == Material.MONSTER_EGGS){
                		 if(generator.nextInt(2) == 1){
                    		 World world = block.getWorld();
                    		 block.getBlock().setType(Material.AIR);
                    		 world.spawnCreature(block, EntityType.SILVERFISH);
                		 }

                	 }
                 }
             }
		 }
		for(Entity players:player.getNearbyEntities(40, 40, 40)){
			if(players instanceof Player){
				Player nearbyPlayer = (Player) players;
				nearbyPlayer.sendMessage(DwarvesVsZombies.prefix + "You hear the shout of a nearby silverfish!");
			}
		}
	}
	public void blazeJump(Player player){
		powerCooldown(player, (long) 25);
		Vector v = player.getLocation().getDirection();
		v.setY(0).normalize().multiply(4*1.5).setY(1.5*1.5);
		player.setVelocity(v);
		plugin.jumping.add(player);
		player.getWorld().createExplosion(player.getLocation(), 0F);
	}
	public void disarm(Entity e, Player shooter){
		powerCooldown(shooter, (long) 15);
		for(Entity entity: e.getNearbyEntities(10, 10, 10)){
			if(entity instanceof Player){
				Player player = (Player) entity;
				if(player.getItemInHand().getType() == Material.IRON_SWORD || player.getItemInHand().getType() == Material.STONE_SWORD || player.getItemInHand().getType() == Material.DIAMOND_SWORD){
					final ItemStack inHand = player.getItemInHand();
					player.sendMessage(DwarvesVsZombies.prefix + "You have been disarmed");
					player.setItemInHand(null);
					Item item = player.getWorld().dropItemNaturally(player.getLocation(), inHand.clone());
					item.setPickupDelay(400);
				}
			}
			
		}
		
	}
	public void stealFood(Entity e, Player shooter){
		powerCooldown(shooter, (long) 15);
		for(Entity entity: e.getNearbyEntities(10, 10, 10)){
			if(entity instanceof Player){
				Player player = (Player) entity;
				player.setFoodLevel(player.getFoodLevel()/3);
				player.sendMessage(DwarvesVsZombies.prefix + "Your food has been drained");
			}
			
		}
		
	}
	public void teleportToPortal(Player player){
		if(plugin.portalLocation != null){
			if(plugin.isPortalOpen){
				powerCooldown(player, (long) 15);
				player.teleport(player.getWorld().getHighestBlockAt(plugin.portalLocation).getLocation());
				player.sendMessage(DwarvesVsZombies.prefix + "The portal has been closed");
			}
			else{
				player.sendMessage(DwarvesVsZombies.prefix + "The portal has been closed");
			}
			
		}
		else{
			player.sendMessage(DwarvesVsZombies.prefix + "The portal has not been opened yet");
		}
	
	}
	public void regenPortal(Player player){
		powerCooldown(player, 15L);
		Location portalLocation = plugin.playerPortal.get(player);
		plugin.isPortalOpen = true;
		plugin.portalLocation = portalLocation;
		player.sendMessage(DwarvesVsZombies.prefix + "The portal has been reinforced!");
		Bukkit.getServer().broadcastMessage(DwarvesVsZombies.prefix + "The portal has been reinforced!");
		player.getWorld().setTime(14000);
		player.getWorld().strikeLightningEffect(player.getLocation());
		
	}
	public void createPortalFrame(Player player){
		plugin.isPortalOpen = true;
		player.getWorld().setTime(14000);
		Location loc = player.getLocation().add(0, 20, 0);
		plugin.portalLocation = loc.add(0,10,0);
		int radius = 9;
		for(double x = (loc.getX() - radius); x <= (loc.getX() + radius); x++){
			 for(double z = (loc.getZ() - radius); z <= (loc.getZ() + radius); z++){
             	Location newLocation = new Location(player.getWorld(), x, loc.getY(), z);
             	if (loc.distance(newLocation) <= radius){
             		newLocation.getBlock().setType(Material.GLOWSTONE);
             	}
             }
         }
		radius = 8;
		for(double x = (loc.getX() - radius); x <= (loc.getX() + radius); x++){
			 for(double z = (loc.getZ() - radius); z <= (loc.getZ() + radius); z++){
            	Location newLocation = new Location(player.getWorld(), x, loc.getY(), z);
            	if (loc.distance(newLocation) <= radius){
            		newLocation.getBlock().setType(Material.AIR);
            	}
            }
        }
		loc = loc.subtract(0,1,0);
		for(double x = (loc.getX() - radius); x <= (loc.getX() + radius); x++){
			 for(double z = (loc.getZ() - radius); z <= (loc.getZ() + radius); z++){
             	Location newLocation = new Location(player.getWorld(), x, loc.getY(), z);
             	if (loc.distance(newLocation) <= radius){
             		newLocation.getBlock().setType(Material.ENDER_STONE);
             	}
             }
         }
		radius = 7;
		for(double x = (loc.getX() - radius); x <= (loc.getX() + radius); x++){
			 for(double z = (loc.getZ() - radius); z <= (loc.getZ() + radius); z++){
            	Location newLocation = new Location(player.getWorld(), x, loc.getY(), z);
            	if (loc.distance(newLocation) <= radius){
            		newLocation.getBlock().setType(Material.AIR);
            	}
            }
        }
		radius = 6;
		for(double x = (loc.getX() - radius); x <= (loc.getX() + radius); x++){
			 for(double z = (loc.getZ() - radius); z <= (loc.getZ() + radius); z++){
            	Location newLocation = new Location(player.getWorld(), x, loc.getY(), z);
            	if (loc.distance(newLocation) <= radius){
            		newLocation.getBlock().setType(Material.ENDER_STONE);
            	}
            }
        }
		radius = 5;
		for(double x = (loc.getX() - radius); x <= (loc.getX() + radius); x++){
			 for(double z = (loc.getZ() - radius); z <= (loc.getZ() + radius); z++){
            	Location newLocation = new Location(player.getWorld(), x, loc.getY(), z);
            	if (loc.distance(newLocation) <= radius){
            		newLocation.getBlock().setType(Material.AIR);
            	}
            }
        }
		radius = 4;
		for(double x = (loc.getX() - radius); x <= (loc.getX() + radius); x++){
			 for(double z = (loc.getZ() - radius); z <= (loc.getZ() + radius); z++){
            	Location newLocation = new Location(player.getWorld(), x, loc.getY(), z);
            	if (loc.distance(newLocation) <= radius){
            		newLocation.getBlock().setType(Material.ENDER_STONE);
            	}
            }
        }
		radius = 3;
		for(double x = (loc.getX() - radius); x <= (loc.getX() + radius); x++){
			 for(double z = (loc.getZ() - radius); z <= (loc.getZ() + radius); z++){
            	Location newLocation = new Location(player.getWorld(), x, loc.getY(), z);
            	if (loc.distance(newLocation) <= radius){
            		newLocation.getBlock().setType(Material.AIR);
            	}
            }
        }
		radius = 2;
		for(double x = (loc.getX() - radius); x <= (loc.getX() + radius); x++){
			 for(double z = (loc.getZ() - radius); z <= (loc.getZ() + radius); z++){
            	Location newLocation = new Location(player.getWorld(), x, loc.getY(), z);
            	if (loc.distance(newLocation) <= radius){
            		newLocation.getBlock().setType(Material.ENDER_STONE);
            	}
            }
        }
		radius = 1;
		for(double x = (loc.getX() - radius); x <= (loc.getX() + radius); x++){
			 for(double z = (loc.getZ() - radius); z <= (loc.getZ() + radius); z++){
            	Location newLocation = new Location(player.getWorld(), x, loc.getY(), z);
            	if (loc.distance(newLocation) <= radius){
            		newLocation.getBlock().setType(Material.ENDER_STONE);
            	}
            }
        }
		loc = loc.subtract(0,1,0);
		radius = 6;
		for(double x = (loc.getX() - radius); x <= (loc.getX() + radius); x++){
			 for(double z = (loc.getZ() - radius); z <= (loc.getZ() + radius); z++){
            	Location newLocation = new Location(player.getWorld(), x, loc.getY(), z);
            	if (loc.distance(newLocation) <= radius){
            		newLocation.getBlock().setType(Material.ENDER_STONE);
            	}
            }
        }
		radius = 5;
		for(double x = (loc.getX() - radius); x <= (loc.getX() + radius); x++){
			 for(double z = (loc.getZ() - radius); z <= (loc.getZ() + radius); z++){
            	Location newLocation = new Location(player.getWorld(), x, loc.getY(), z);
            	if (loc.distance(newLocation) <= radius){
            		newLocation.getBlock().setType(Material.AIR);
            	}
            }
        }
		radius = 4;
		for(double x = (loc.getX() - radius); x <= (loc.getX() + radius); x++){
			 for(double z = (loc.getZ() - radius); z <= (loc.getZ() + radius); z++){
            	Location newLocation = new Location(player.getWorld(), x, loc.getY(), z);
            	if (loc.distance(newLocation) <= radius){
            		newLocation.getBlock().setType(Material.ENDER_STONE);
            	}
            }
        }
		radius = 3;
		for(double x = (loc.getX() - radius); x <= (loc.getX() + radius); x++){
			 for(double z = (loc.getZ() - radius); z <= (loc.getZ() + radius); z++){
            	Location newLocation = new Location(player.getWorld(), x, loc.getY(), z);
            	if (loc.distance(newLocation) <= radius){
            		newLocation.getBlock().setType(Material.AIR);
            	}
            }
        }
		radius = 2;
		for(double x = (loc.getX() - radius); x <= (loc.getX() + radius); x++){
			 for(double z = (loc.getZ() - radius); z <= (loc.getZ() + radius); z++){
            	Location newLocation = new Location(player.getWorld(), x, loc.getY(), z);
            	if (loc.distance(newLocation) <= radius){
            		newLocation.getBlock().setType(Material.ENDER_STONE);
            	}
            }
        }
		radius = 1;
		for(double x = (loc.getX() - radius); x <= (loc.getX() + radius); x++){
			 for(double z = (loc.getZ() - radius); z <= (loc.getZ() + radius); z++){
            	Location newLocation = new Location(player.getWorld(), x, loc.getY(), z);
            	if (loc.distance(newLocation) <= radius){
            		newLocation.getBlock().setType(Material.ENDER_STONE);
            	}
            }
        }
		loc = loc.subtract(0,1,0);
		radius = 4;
		for(double x = (loc.getX() - radius); x <= (loc.getX() + radius); x++){
			 for(double z = (loc.getZ() - radius); z <= (loc.getZ() + radius); z++){
            	Location newLocation = new Location(player.getWorld(), x, loc.getY(), z);
            	if (loc.distance(newLocation) <= radius){
            		newLocation.getBlock().setType(Material.ENDER_STONE);
            	}
            }
        }
		radius = 3;
		for(double x = (loc.getX() - radius); x <= (loc.getX() + radius); x++){
			 for(double z = (loc.getZ() - radius); z <= (loc.getZ() + radius); z++){
            	Location newLocation = new Location(player.getWorld(), x, loc.getY(), z);
            	if (loc.distance(newLocation) <= radius){
            		newLocation.getBlock().setType(Material.AIR);
            	}
            }
        }
		radius = 2;
		for(double x = (loc.getX() - radius); x <= (loc.getX() + radius); x++){
			 for(double z = (loc.getZ() - radius); z <= (loc.getZ() + radius); z++){
            	Location newLocation = new Location(player.getWorld(), x, loc.getY(), z);
            	if (loc.distance(newLocation) <= radius){
            		newLocation.getBlock().setType(Material.ENDER_STONE);
            	}
            }
        }
		radius = 1;
		for(double x = (loc.getX() - radius); x <= (loc.getX() + radius); x++){
			 for(double z = (loc.getZ() - radius); z <= (loc.getZ() + radius); z++){
            	Location newLocation = new Location(player.getWorld(), x, loc.getY(), z);
            	if (loc.distance(newLocation) <= radius){
            		newLocation.getBlock().setType(Material.ENDER_STONE);
            	}
            }
        }
		loc = loc.subtract(0,1,0);
		radius = 2;
		for(double x = (loc.getX() - radius); x <= (loc.getX() + radius); x++){
			 for(double z = (loc.getZ() - radius); z <= (loc.getZ() + radius); z++){
            	Location newLocation = new Location(player.getWorld(), x, loc.getY(), z);
            	if (loc.distance(newLocation) <= radius){
            		newLocation.getBlock().setType(Material.ENDER_STONE);
            	}
            }
        }
		radius = 1;
		for(double x = (loc.getX() - radius); x <= (loc.getX() + radius); x++){
			 for(double z = (loc.getZ() - radius); z <= (loc.getZ() + radius); z++){
            	Location newLocation = new Location(player.getWorld(), x, loc.getY(), z);
            	if (loc.distance(newLocation) <= radius){
            		newLocation.getBlock().setType(Material.ENDER_STONE);
            	}
            }
        }

	}

	
	
}
