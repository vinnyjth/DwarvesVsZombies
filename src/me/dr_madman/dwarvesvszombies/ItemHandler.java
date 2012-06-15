package me.dr_madman.dwarvesvszombies;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ItemHandler {
	@SuppressWarnings("unused")
	private DwarvesVsZombies plugin;
	
	public ItemHandler(DwarvesVsZombies instance) {
		plugin = instance;
		
	}
	
	public void clearInventory(Player player){
		player.getInventory().setBoots(null);
		player.getInventory().setHelmet(null);
		player.getInventory().setChestplate(null);
		player.getInventory().setLeggings(null);
		player.getInventory().clear();
	}
	
	public void distributeKillPill(final Player player){
		plugin.getServer().getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {

			   public void run() {
			       player.getInventory().addItem(new ItemStack(Material.GHAST_TEAR));
			   }
			}, 120L);
	}
	
	@SuppressWarnings("deprecation")
	public void initAlchemist(Player player){
		Inventory inv = player.getInventory();
		clearInventory(player);

		inv.addItem(new ItemStack(Material.BOOK));
		inv.addItem(new ItemStack(Material.BREWING_STAND_ITEM));
		inv.addItem(new ItemStack(Material.CAULDRON_ITEM, 2));
		inv.addItem(new ItemStack(Material.CHEST, 2));
		inv.addItem(new ItemStack(Material.REDSTONE, 5));
		inv.addItem(new ItemStack(Material.LAPIS_BLOCK, 64));
		inv.addItem(new ItemStack(Material.GLASS, 64));
		inv.addItem(new ItemStack(Material.SIGN, 3));
		player.updateInventory();

		
	}
	@SuppressWarnings("deprecation")
	public void initBuilder(Player player){
		Inventory inv = player.getInventory();
		clearInventory(player);
		inv.addItem(new ItemStack(Material.BOOK));
		inv.addItem(new ItemStack(Material.IRON_AXE));
		inv.addItem(new ItemStack(Material.IRON_PICKAXE));
		inv.addItem(new ItemStack(Material.IRON_SPADE));
		inv.addItem(new ItemStack(Material.GRILLED_PORK, 5));
		inv.addItem(new ItemStack(Material.LEATHER_HELMET));
		inv.addItem(new ItemStack(Material.LEATHER_CHESTPLATE));
		inv.addItem(new ItemStack(Material.LEATHER_LEGGINGS));
		inv.addItem(new ItemStack(Material.LEATHER_BOOTS));
		player.updateInventory();
		
	}
	@SuppressWarnings("deprecation")
	public void initBlacksmith(Player player){
		Inventory inv = player.getInventory();
		clearInventory(player);
		inv.addItem(new ItemStack(Material.BOOK));
		inv.addItem(new ItemStack(Material.IRON_PICKAXE));
		inv.addItem(new ItemStack(Material.FURNACE, 2));
		inv.addItem(new ItemStack(Material.CHEST, 2));
		inv.addItem(new ItemStack(Material.COAL, 10));
		inv.addItem(new ItemStack(Material.REDSTONE_ORE, 12));
		inv.addItem(new ItemStack(Material.GOLD_ORE, 24));
		inv.addItem(new ItemStack(Material.NETHER_BRICK, 64));
		inv.addItem(new ItemStack(Material.RAW_FISH, 10));
		inv.addItem(new ItemStack(Material.SIGN, 3));
		player.updateInventory();
		
	}
	@SuppressWarnings("deprecation")
	public void initBaker(Player player){
		Inventory inv = player.getInventory();
		clearInventory(player);
		inv.addItem(new ItemStack(Material.BOOK));
		inv.addItem(new ItemStack(Material.FURNACE, 2));
		inv.addItem(new ItemStack(Material.CHEST, 2));
		inv.addItem(new ItemStack(Material.COAL, 10));
		inv.addItem(new ItemStack(Material.CLAY_BALL, 12));
		inv.addItem(new ItemStack(Material.BRICK, 64));
		inv.addItem(new ItemStack(Material.RAW_FISH, 10));
		inv.addItem(new ItemStack(Material.SIGN, 3));
		player.updateInventory();
		
	}
	@SuppressWarnings("deprecation")
	public void initTailor(Player player){
		Inventory inv = player.getInventory();
		clearInventory(player);
		inv.addItem(new ItemStack(Material.BOOK));
		inv.addItem(new ItemStack(Material.RED_ROSE, 6));
		inv.addItem(new ItemStack(Material.CHEST, 2));
		inv.addItem(new ItemStack(Material.YELLOW_FLOWER, 6));
		inv.addItem(new ItemStack(Material.DIAMOND_HOE, 1));
		inv.addItem(new ItemStack(Material.BONE, 1));
		inv.addItem(new ItemStack(Material.RAW_FISH, 10));
		inv.addItem(new ItemStack(Material.SIGN, 3));
		player.updateInventory();
		
	}
	@SuppressWarnings("deprecation")
	public void initZombie(Player player){
		Inventory inv = player.getInventory();
		clearInventory(player);
		ItemStack sword = new ItemStack(Material.IRON_SWORD);
		sword.addEnchantment(Enchantment.DAMAGE_ALL, 2);
		inv.addItem(sword);
		inv.addItem(new ItemStack(Material.IRON_HELMET));
		inv.addItem(new ItemStack(Material.IRON_CHESTPLATE));
		inv.addItem(new ItemStack(Material.IRON_LEGGINGS));
		inv.addItem(new ItemStack(Material.IRON_BOOTS));
		inv.addItem(new ItemStack(Material.MAP));
		distributeKillPill(player);
		inv.addItem(new ItemStack(Material.COMPASS));
		player.updateInventory();
		
	}
	@SuppressWarnings("deprecation")
	public void initSkeleton(Player player){
		Inventory inv = player.getInventory();
		clearInventory(player);
		ItemStack sword = new ItemStack(Material.BOW);
		sword.addEnchantment(Enchantment.ARROW_DAMAGE, 5);
		sword.addEnchantment(Enchantment.ARROW_KNOCKBACK, 2);
		sword.addEnchantment(Enchantment.ARROW_FIRE, 1);
		sword.addEnchantment(Enchantment.ARROW_INFINITE, 1);
		inv.addItem(sword);
		inv.addItem(new ItemStack(Material.ARROW));
		inv.addItem(new ItemStack(Material.LEATHER_HELMET));
		inv.addItem(new ItemStack(Material.LEATHER_CHESTPLATE));
		inv.addItem(new ItemStack(Material.LEATHER_LEGGINGS));
		inv.addItem(new ItemStack(Material.LEATHER_BOOTS));
		inv.addItem(new ItemStack(Material.MAP));
		distributeKillPill(player);
		inv.addItem(new ItemStack(Material.COMPASS));
		player.updateInventory();
		
	}
	public void initCreeper(Player player){
		Inventory inv = player.getInventory();
		clearInventory(player);
		inv.addItem(new ItemStack(Material.SULPHUR));
		inv.addItem(new ItemStack(Material.LEATHER_HELMET));
		inv.addItem(new ItemStack(Material.LEATHER_CHESTPLATE));
		inv.addItem(new ItemStack(Material.LEATHER_LEGGINGS));
		inv.addItem(new ItemStack(Material.LEATHER_BOOTS));
		inv.addItem(new ItemStack(Material.MAP));
		distributeKillPill(player);
		inv.addItem(new ItemStack(Material.COMPASS));
		player.updateInventory();
		
	}
	@SuppressWarnings("deprecation")
	public void initSpider(Player player){
		Inventory inv = player.getInventory();
		clearInventory(player);
		ItemStack boots = new ItemStack(Material.IRON_BOOTS);
		boots.addEnchantment(Enchantment.PROTECTION_FALL, 4 );
		inv.addItem(boots);
		inv.addItem(new ItemStack(Material.VINE, 64));
		inv.addItem(new ItemStack(Material.SPIDER_EYE));
		inv.addItem(new ItemStack(Material.IRON_HELMET));
		inv.addItem(new ItemStack(Material.IRON_CHESTPLATE));
		inv.addItem(new ItemStack(Material.IRON_LEGGINGS));
		inv.addItem(new ItemStack(Material.MAP));
		distributeKillPill(player);
		inv.addItem(new ItemStack(Material.COMPASS));
		player.updateInventory();
		
	}
	@SuppressWarnings("deprecation")
	public void initWolf(Player player){
		Inventory inv = player.getInventory();
		clearInventory(player);
		ItemStack diamondSword = new ItemStack(Material.IRON_SWORD);
		diamondSword.addEnchantment(Enchantment.DAMAGE_ALL, 2);
		inv.addItem(diamondSword);
		ItemStack goldSword = new ItemStack(Material.GOLD_SWORD);
		goldSword.addEnchantment(Enchantment.KNOCKBACK, 2);
		goldSword.addEnchantment(Enchantment.FIRE_ASPECT, 2);
		inv.addItem(goldSword);
		inv.addItem(new ItemStack(Material.MONSTER_EGG, 5, (short) 95));
		inv.addItem(new ItemStack(Material.BONE, 64));
		inv.addItem(new ItemStack(Material.CHAINMAIL_HELMET));
		inv.addItem(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
		inv.addItem(new ItemStack(Material.CHAINMAIL_LEGGINGS));
		inv.addItem(new ItemStack(Material.CHAINMAIL_BOOTS));
		inv.addItem(new ItemStack(Material.MAP));
		distributeKillPill(player);
		inv.addItem(new ItemStack(Material.COMPASS));
		player.updateInventory();
		
	}
	public void initSnowgolem(Player player){
		Inventory inv = player.getInventory();
		clearInventory(player);
		inv.addItem(new ItemStack(Material.SNOW_BALL, 2240));
		inv.addItem(new ItemStack(Material.MAP));
		player.updateInventory();
		
	}
	public void initBroodmother(Player player){
		Inventory inv = player.getInventory();
		clearInventory(player);
		inv.addItem(new ItemStack(Material.RAW_FISH));
		inv.addItem(new ItemStack(Material.IRON_SPADE));
		inv.addItem(new ItemStack(Material.COOKED_FISH));
		inv.addItem(new ItemStack(Material.MONSTER_EGG, 5, (short) 60));
		inv.addItem(new ItemStack(Material.MAP));
		distributeKillPill(player);
		inv.addItem(new ItemStack(Material.COMPASS));
		player.updateInventory();
		
	}public void initBlaze(Player player){
		Inventory inv = player.getInventory();
		clearInventory(player);
		ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
		helmet.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
		inv.addItem(helmet);
		
		ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
		chestplate.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
		inv.addItem(chestplate);
		
		ItemStack pants = new ItemStack(Material.LEATHER_LEGGINGS);
		pants.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
		inv.addItem(pants);
		
		ItemStack shoes = new ItemStack(Material.LEATHER_BOOTS);
		shoes.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
		inv.addItem(shoes);
		
		inv.addItem(new ItemStack(Material.IRON_INGOT));
		inv.addItem(new ItemStack(Material.SLIME_BALL));
		inv.addItem(new ItemStack(Material.MAP));
		distributeKillPill(player);
		inv.addItem(new ItemStack(Material.COMPASS));
		player.updateInventory();
		
	}
	public void initCougar(Player player){
		Inventory inv = player.getInventory();
		clearInventory(player);
		inv.addItem(new ItemStack(Material.VINE, 26));
		inv.addItem(new ItemStack(Material.STRING));
		inv.addItem(new ItemStack(Material.INK_SACK));
		inv.addItem(new ItemStack(Material.LEATHER_HELMET));
		inv.addItem(new ItemStack(Material.LEATHER_CHESTPLATE));
		inv.addItem(new ItemStack(Material.LEATHER_LEGGINGS));
		inv.addItem(new ItemStack(Material.LEATHER_BOOTS));
		inv.addItem(new ItemStack(Material.MAP));
		distributeKillPill(player);
		inv.addItem(new ItemStack(Material.COMPASS));
		player.updateInventory();
		
	}
	public void initBabyHungryPig(Player player){
		Inventory inv = player.getInventory();
		clearInventory(player);
		inv.addItem(new ItemStack(Material.IRON_PICKAXE));
		inv.addItem(new ItemStack(372));
		inv.addItem(new ItemStack(Material.LEATHER_HELMET));
		inv.addItem(new ItemStack(Material.LEATHER_CHESTPLATE));
		inv.addItem(new ItemStack(Material.LEATHER_LEGGINGS));
		inv.addItem(new ItemStack(Material.LEATHER_BOOTS));
		inv.addItem(new ItemStack(Material.MAP));
		distributeKillPill(player);
		inv.addItem(new ItemStack(Material.COMPASS));
		player.updateInventory();
		
	}
	public void initHungryPig(Player player){
		Inventory inv = player.getInventory();
		clearInventory(player);
		ItemStack sword = new ItemStack(Material.STONE_SWORD);
		sword.addEnchantment(Enchantment.DAMAGE_ALL, 4);
		inv.addItem(sword);
		ItemStack pick = new ItemStack(Material.DIAMOND_PICKAXE);
		pick.addEnchantment(Enchantment.DIG_SPEED, 5);
		inv.addItem(pick);
		inv.addItem(new ItemStack(372));
		inv.addItem(new ItemStack(Material.MAP));
		inv.addItem(new ItemStack(Material.LEATHER_HELMET));
		inv.addItem(new ItemStack(Material.LEATHER_CHESTPLATE));
		inv.addItem(new ItemStack(Material.LEATHER_LEGGINGS));
		inv.addItem(new ItemStack(Material.LEATHER_BOOTS));
		distributeKillPill(player);
		inv.addItem(new ItemStack(Material.COMPASS));
		player.updateInventory();
		
	}
	public void initPigman(Player player){
		Inventory inv = player.getInventory();
		clearInventory(player);
		ItemStack sword = new ItemStack(Material.IRON_SWORD);
		sword.addEnchantment(Enchantment.DAMAGE_ALL, 4);
		inv.addItem(sword);
		inv.addItem(new ItemStack(Material.MAP));
		inv.addItem(new ItemStack(Material.LEATHER_HELMET));
		inv.addItem(new ItemStack(Material.LEATHER_CHESTPLATE));
		inv.addItem(new ItemStack(Material.LEATHER_LEGGINGS));
		inv.addItem(new ItemStack(Material.LEATHER_BOOTS));
		distributeKillPill(player);
		inv.addItem(new ItemStack(Material.COMPASS));
		player.updateInventory();
		
	}
	public void initEnderman(Player player){
		Inventory inv = player.getInventory();
		clearInventory(player);
		inv.addItem(new ItemStack(Material.BLAZE_ROD));
		inv.addItem(new ItemStack(Material.ENDER_PEARL));
		distributeKillPill(player);
		inv.addItem(new ItemStack(Material.COMPASS));
		player.updateInventory();
	}
	public void initEndermanPortal(Player player){
		Inventory inv = player.getInventory();
		clearInventory(player);
		inv.addItem(new ItemStack(Material.BLAZE_ROD));
		inv.addItem(new ItemStack(Material.MONSTER_EGG, 5, (short) 51));
		inv.addItem(new ItemStack(Material.MONSTER_EGG, 5, (short) 50));
		inv.addItem(new ItemStack(Material.MONSTER_EGG, 5, (short) 52));
		inv.addItem(new ItemStack(Material.MONSTER_EGG, 5, (short) 54));
		inv.addItem(new ItemStack(Material.MONSTER_EGG, 5, (short) 61));
		player.updateInventory();
	}
	
}
