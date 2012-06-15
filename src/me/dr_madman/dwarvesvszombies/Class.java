package me.dr_madman.dwarvesvszombies;

import org.bukkit.Material;

public enum Class {
	Nothing (false, 0, Material.AIR),
    Blacksmith(true, .30, Material.FURNACE),
    Builder   (true, 1, Material.SMOOTH_BRICK),
    Alchemist (true, .20, Material.GLASS_BOTTLE),
    Tailor (true, .30, Material.DIAMOND_CHESTPLATE),
    Baker    (true, .25, Material.BREAD),
    Zombie (false, 1, Material.ROTTEN_FLESH),
    Skeleton  (false, .75, Material.BOW),
    Creeper  (false, .50, Material.TNT),
    Spider (false, .30, Material.WEB),
    Wolf (false, .30, Material.BONE),
    SnowGolem (false, .30, Material.SNOW_BALL),
    Broodmother(false, .25, Material.EGG),
    Blaze(false, .07, Material.BLAZE_ROD),
    Cougar(false, .10, Material.RAW_FISH),
    HungryBabyPig(false, .10, Material.PORK),
    HungryPig(false, 0, Material.AIR),
    Pigman(false, 0, Material.AIR),
    Enderman (false, .05, Material.EYE_OF_ENDER),
    EndermanPortal(false, 0, Material.AIR),
	Spy(false, 0, Material.AIR);

    private final boolean isDwarf;   
    private final double chance;
    private final Material item;
    Class(boolean isDwarf, double chance, Material item) {
        this.isDwarf = isDwarf;
        this.chance = chance;
        this.item = item;
    }
    public boolean isDwarf() { return isDwarf; }
    public double chance() { return chance; }
    public Material item() { return item; }
}


