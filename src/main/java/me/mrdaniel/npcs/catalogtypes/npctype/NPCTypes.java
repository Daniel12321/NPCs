package me.mrdaniel.npcs.catalogtypes.npctype;

import com.google.common.collect.Lists;
import org.spongepowered.api.entity.EntityTypes;

import java.util.List;

public class NPCTypes {

	public static final NPCType BAT = new NPCType("Bat", "bat", EntityTypes.BAT);
	public static final NPCType BLAZE = new NPCType("Blaze", "blaze", EntityTypes.BLAZE);
	public static final NPCType CAVE_SPIDER = new NPCType("CaveSpider", "cave_spider", EntityTypes.CAVE_SPIDER);
	public static final NPCType CHICKEN = new NPCType("Chicken", "chicken", EntityTypes.CHICKEN);
	public static final NPCType COW = new NPCType("Cow", "cow", EntityTypes.COW);
	public static final NPCType CREEPER = new NPCType("Creeper", "creeper", EntityTypes.CREEPER);
	public static final NPCType DONKEY = new NPCType("Donkey", "donkey", EntityTypes.DONKEY);
	public static final NPCType ELDER_GUARDIAN = new NPCType("ElderGuardian", "elder_guardian", EntityTypes.ELDER_GUARDIAN);
	public static final NPCType ENDERMAN = new NPCType("Enderman", "enderman", EntityTypes.ENDERMAN);
	public static final NPCType ENDERMITE = new NPCType("Endermite", "endermite", EntityTypes.ENDERMITE);
	public static final NPCType ENDER_DRAGON = new NPCType("EnderDragon", "ender_dragon", EntityTypes.ENDER_DRAGON);
	public static final NPCType EVOCATION_ILLAGER = new NPCType("EvocationIllager", "evocation_illager", EntityTypes.EVOCATION_ILLAGER);
	public static final NPCType GHAST = new NPCType("Ghast", "ghast", EntityTypes.GHAST);
	public static final NPCType GIANT = new NPCType("Giant", "giant", EntityTypes.GIANT);
	public static final NPCType GUARDIAN = new NPCType("Guardian", "guardian", EntityTypes.GUARDIAN);
	public static final NPCType HORSE = new NPCType("Horse", "horse", EntityTypes.HORSE);
	public static final NPCType HUMAN = new NPCType("Human", "human", EntityTypes.HUMAN);
	public static final NPCType HUSK = new NPCType("Husk", "husk", EntityTypes.HUSK);
	public static final NPCType ILLUSION_ILLAGER = new NPCType("IllusionIllager", "illusion_illager", EntityTypes.ILLUSION_ILLAGER);
	public static final NPCType IRON_GOLEM = new NPCType("IronGolem", "iron_golem", EntityTypes.IRON_GOLEM);
	public static final NPCType LLAMA = new NPCType("Llama", "llama", EntityTypes.LLAMA);
	public static final NPCType MAGMA_CUBE = new NPCType("MagmaCube", "magma_cube", EntityTypes.MAGMA_CUBE);
	public static final NPCType MULE = new NPCType("Mule", "mule", EntityTypes.MULE);
	public static final NPCType MUSHROOM_COW = new NPCType("MushroomCow", "mushroom_cow", EntityTypes.MUSHROOM_COW);
	public static final NPCType OCELOT = new NPCType("Ocelot", "ocelot", EntityTypes.OCELOT);
	public static final NPCType PARROT = new NPCType("Parrot", "parrot", EntityTypes.PARROT);
	public static final NPCType PIG = new NPCType("Pig", "pig", EntityTypes.PIG);
	public static final NPCType PIG_ZOMBIE = new NPCType("PigZombie", "pig_zombie", EntityTypes.PIG_ZOMBIE);
	public static final NPCType POLAR_BEAR = new NPCType("Polarbear", "polar_bear", EntityTypes.POLAR_BEAR);
	public static final NPCType RABBIT = new NPCType("Rabbit", "rabbit", EntityTypes.RABBIT);
	public static final NPCType SHEEP = new NPCType("Sheep", "sheep", EntityTypes.SHEEP);
	public static final NPCType SHULKER = new NPCType("Shulker", "shulker", EntityTypes.SHULKER);
	public static final NPCType SILVERFISH = new NPCType("Silverfish", "silverfish", EntityTypes.SILVERFISH);
	public static final NPCType SKELETON = new NPCType("Skeleton", "skeleton", EntityTypes.SKELETON);
	public static final NPCType SKELETON_HORSE = new NPCType("SkeletonHorse", "skeleton_horse", EntityTypes.SKELETON_HORSE);
	public static final NPCType SLIME = new NPCType("Slime", "slime", EntityTypes.SLIME);
	public static final NPCType SNOWMAN = new NPCType("Snowman", "snowman", EntityTypes.SNOWMAN);
	public static final NPCType SPIDER = new NPCType("Spider", "spider", EntityTypes.SPIDER);
	public static final NPCType SQUID = new NPCType("Squid", "squid", EntityTypes.SQUID);
	public static final NPCType STRAY = new NPCType("Stray", "stray", EntityTypes.STRAY);
	public static final NPCType VEX = new NPCType("Vex", "vex", EntityTypes.VEX);
	public static final NPCType VILLAGER = new NPCType("Villager", "villager", EntityTypes.VILLAGER);
	public static final NPCType VINDICATION_ILLAGER = new NPCType("VindicationIllager", "vindication_illager", EntityTypes.VINDICATION_ILLAGER);
	public static final NPCType WITCH = new NPCType("Witch", "witch", EntityTypes.WITCH);
	public static final NPCType WITHER = new NPCType("Wither", "wither", EntityTypes.WITHER);
	public static final NPCType WITHER_SKELETON = new NPCType("WitherSkeleton", "wither_skeleton", EntityTypes.WITHER_SKELETON);
	public static final NPCType WOLF = new NPCType("Wolf", "wolf", EntityTypes.WOLF);
	public static final NPCType ZOMBIE = new NPCType("Zombie", "zombie", EntityTypes.ZOMBIE);
	public static final NPCType ZOMBIE_HORSE = new NPCType("ZombieHorse", "zombie_horse", EntityTypes.ZOMBIE_HORSE);
	public static final NPCType ZOMBIE_VILLAGER = new NPCType("ZombieVillager", "zombie_villager", EntityTypes.ZOMBIE_VILLAGER);

	public static final List<NPCType> ALL = Lists.newArrayList(
			BAT, BLAZE, CAVE_SPIDER, CHICKEN, COW, CREEPER, DONKEY, ELDER_GUARDIAN, ENDER_DRAGON, ENDERMAN,
			ENDERMITE, EVOCATION_ILLAGER, GHAST, GIANT, GUARDIAN, HORSE, HUMAN, HUSK, ILLUSION_ILLAGER, IRON_GOLEM,
			LLAMA, MAGMA_CUBE, MULE, MUSHROOM_COW, OCELOT, PARROT, PIG, PIG_ZOMBIE, POLAR_BEAR, RABBIT,
			SHEEP, SHULKER, SILVERFISH, SKELETON, SKELETON_HORSE, SLIME, SNOWMAN, SPIDER, SQUID, STRAY,
			VEX, VILLAGER, VINDICATION_ILLAGER, WITCH, WITHER, WITHER_SKELETON, WOLF, ZOMBIE, ZOMBIE_HORSE, ZOMBIE_VILLAGER);
}
