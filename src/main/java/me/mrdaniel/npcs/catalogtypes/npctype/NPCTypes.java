package me.mrdaniel.npcs.catalogtypes.npctype;

import com.google.common.collect.Lists;
import org.spongepowered.api.entity.EntityTypes;

import java.util.List;

public class NPCTypes {

	public static final NPCType BAT = new NPCType("Bat", "bat", EntityTypes.BAT, 1.0);
	public static final NPCType BLAZE = new NPCType("Blaze", "blaze", EntityTypes.BLAZE, 1.0);
	public static final NPCType CAVE_SPIDER = new NPCType("CaveSpider", "cave_spider", EntityTypes.CAVE_SPIDER, 1.0);
	public static final NPCType CHICKEN = new NPCType("Chicken", "chicken", EntityTypes.CHICKEN, 1.0, true);
	public static final NPCType COW = new NPCType("Cow", "cow", EntityTypes.COW, 1.0, true);
	public static final NPCType CREEPER = new NPCType("Creeper", "creeper", EntityTypes.CREEPER, 1.0);
	public static final NPCType DONKEY = new NPCType("Donkey", "donkey", EntityTypes.DONKEY, 1.0, true);
	public static final NPCType ELDER_GUARDIAN = new NPCType("ElderGuardian", "elder_guardian", EntityTypes.ELDER_GUARDIAN, 1.0);
	public static final NPCType ENDERMAN = new NPCType("Enderman", "enderman", EntityTypes.ENDERMAN, 1.0);
	public static final NPCType ENDERMITE = new NPCType("Endermite", "endermite", EntityTypes.ENDERMITE, 1.0);
	public static final NPCType ENDER_DRAGON = new NPCType("EnderDragon", "ender_dragon", EntityTypes.ENDER_DRAGON, 1.0);
	public static final NPCType EVOCATION_ILLAGER = new NPCType("EvocationIllager", "evocation_illager", EntityTypes.EVOCATION_ILLAGER, 1.0);
	public static final NPCType GHAST = new NPCType("Ghast", "ghast", EntityTypes.GHAST, 1.0);
	public static final NPCType GIANT = new NPCType("Giant", "giant", EntityTypes.GIANT, 1.0);
	public static final NPCType GUARDIAN = new NPCType("Guardian", "guardian", EntityTypes.GUARDIAN, 1.0);
	public static final NPCType HORSE = new NPCType("Horse", "horse", EntityTypes.HORSE, 1.0, true);
	public static final NPCType HUMAN = new NPCType("Human", "human", EntityTypes.HUMAN, 0.1, false, true);
	public static final NPCType HUSK = new NPCType("Husk", "husk", EntityTypes.HUSK, 1.0, true, true);
	public static final NPCType ILLUSION_ILLAGER = new NPCType("IllusionIllager", "illusion_illager", EntityTypes.ILLUSION_ILLAGER, 1.0);
	public static final NPCType IRON_GOLEM = new NPCType("IronGolem", "iron_golem", EntityTypes.IRON_GOLEM, 1.0);
	public static final NPCType LLAMA = new NPCType("Llama", "llama", EntityTypes.LLAMA, 1.0, true);
	public static final NPCType MAGMA_CUBE = new NPCType("MagmaCube", "magma_cube", EntityTypes.MAGMA_CUBE, 1.0);
	public static final NPCType MULE = new NPCType("Mule", "mule", EntityTypes.MULE, 1.0, true);
	public static final NPCType MUSHROOM_COW = new NPCType("MushroomCow", "mushroom_cow", EntityTypes.MUSHROOM_COW, 1.0, true);
	public static final NPCType OCELOT = new NPCType("Ocelot", "ocelot", EntityTypes.OCELOT, 1.0, true, false);
	public static final NPCType PARROT = new NPCType("Parrot", "parrot", EntityTypes.PARROT, 1.0);
	public static final NPCType PIG = new NPCType("Pig", "pig", EntityTypes.PIG, 1.0, true);
	public static final NPCType PIG_ZOMBIE = new NPCType("PigZombie", "pig_zombie", EntityTypes.PIG_ZOMBIE, 1.0, true, true);
	public static final NPCType POLAR_BEAR = new NPCType("Polarbear", "polar_bear", EntityTypes.POLAR_BEAR, 1.0, true);
	public static final NPCType RABBIT = new NPCType("Rabbit", "rabbit", EntityTypes.RABBIT, 1.0, true);
	public static final NPCType SHEEP = new NPCType("Sheep", "sheep", EntityTypes.SHEEP, 1.0, true);
	public static final NPCType SHULKER = new NPCType("Shulker", "shulker", EntityTypes.SHULKER, 1.0);
	public static final NPCType SILVERFISH = new NPCType("Silverfish", "silverfish", EntityTypes.SILVERFISH, 1.0);
	public static final NPCType SKELETON = new NPCType("Skeleton", "skeleton", EntityTypes.SKELETON, 1.25, false, true);
	public static final NPCType SKELETON_HORSE = new NPCType("SkeletonHorse", "skeleton_horse", EntityTypes.SKELETON_HORSE, 1.0, true);
	public static final NPCType SLIME = new NPCType("Slime", "slime", EntityTypes.SLIME, 1.0);
	public static final NPCType SNOWMAN = new NPCType("Snowman", "snowman", EntityTypes.SNOWMAN, 1.0);
	public static final NPCType SPIDER = new NPCType("Spider", "spider", EntityTypes.SPIDER, 1.0);
	public static final NPCType SQUID = new NPCType("Squid", "squid", EntityTypes.SQUID, 1.0);
	public static final NPCType STRAY = new NPCType("Stray", "stray", EntityTypes.STRAY, 1.0, false, true);
	public static final NPCType VEX = new NPCType("Vex", "vex", EntityTypes.VEX, 1.0);
	public static final NPCType VILLAGER = new NPCType("Villager", "villager", EntityTypes.VILLAGER, 1.0, true);
	public static final NPCType VINDICATION_ILLAGER = new NPCType("VindicationIllager", "vindication_illager", EntityTypes.VINDICATION_ILLAGER, 1.0);
	public static final NPCType WITCH = new NPCType("Witch", "witch", EntityTypes.WITCH, 1.0);
	public static final NPCType WITHER = new NPCType("Wither", "wither", EntityTypes.WITHER, 1.0);
	public static final NPCType WITHER_SKELETON = new NPCType("WitherSkeleton", "wither_skeleton", EntityTypes.WITHER_SKELETON, 1.0, false, true);
	public static final NPCType WOLF = new NPCType("Wolf", "wolf", EntityTypes.WOLF, 1.0, true, false);
	public static final NPCType ZOMBIE = new NPCType("Zombie", "zombie", EntityTypes.ZOMBIE, 1.25, true, true);
	public static final NPCType ZOMBIE_HORSE = new NPCType("ZombieHorse", "zombie_horse", EntityTypes.ZOMBIE_HORSE, 1.0, true);
	public static final NPCType ZOMBIE_VILLAGER = new NPCType("ZombieVillager", "zombie_villager", EntityTypes.ZOMBIE_VILLAGER, 1.0, true, true);

	public static final List<NPCType> ALL = Lists.newArrayList(
			BAT, BLAZE, CAVE_SPIDER, CHICKEN, COW, CREEPER, DONKEY, ELDER_GUARDIAN, ENDER_DRAGON, ENDERMAN,
			ENDERMITE, EVOCATION_ILLAGER, GHAST, GIANT, GUARDIAN, HORSE, HUMAN, HUSK, ILLUSION_ILLAGER, IRON_GOLEM,
			LLAMA, MAGMA_CUBE, MULE, MUSHROOM_COW, OCELOT, PARROT, PIG, PIG_ZOMBIE, POLAR_BEAR, RABBIT,
			SHEEP, SHULKER, SILVERFISH, SKELETON, SKELETON_HORSE, SLIME, SNOWMAN, SPIDER, SQUID, STRAY,
			VEX, VILLAGER, VINDICATION_ILLAGER, WITCH, WITHER, WITHER_SKELETON, WOLF, ZOMBIE, ZOMBIE_HORSE, ZOMBIE_VILLAGER);
}
