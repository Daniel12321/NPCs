package me.mrdaniel.npcs.catalogtypes.options;

import org.spongepowered.api.data.type.Career;
import org.spongepowered.api.data.type.HorseColor;
import org.spongepowered.api.data.type.HorseStyle;
import org.spongepowered.api.data.type.LlamaVariant;
import org.spongepowered.api.data.type.OcelotType;
import org.spongepowered.api.data.type.RabbitType;
import org.spongepowered.api.entity.ArmorEquipable;
import org.spongepowered.api.entity.living.Human;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.world.World;

import me.mrdaniel.npcs.utils.Position;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;

public final class OptionTypes {

	public static final OptionType<World> WORLD = new OptionType<>("World", "world", npc -> true, (npc, value) -> npc.setNPCWorld(value), (file, value) -> file.setWorld(value).save(), file -> file.getWorld().orElse(null));
	public static final OptionType<Position> POSITION = new OptionType<>("Position", "position", npc -> true, (npc, value) -> npc.setNPCPosition(value), (file, value) -> file.setPosition(value).save(), file -> new Position(file.getX(), file.getY(), file.getZ(), file.getYaw(), file.getPitch()));
	public static final OptionType<String> 	NAME = new OptionType<>("Name", "name", npc -> true, (npc, value) -> npc.setNPCName(value), (file, value) -> file.setName(value).save(), file -> file.getName().orElse(null));
	public static final OptionType<String> SKIN = new OptionType<>("Skin", "skin", npc -> npc instanceof Human, (npc, value) -> npc.setNPCSkin(value), (file, value) -> file.setSkinName(value).save(), file -> file.getSkinName().orElse(null));
	public static final OptionType<Boolean> LOOKING = new OptionType<>("Looking", "looking", npc -> true, (npc, value) -> npc.setNPCLooking(value), (file, value) -> file.setLooking(value).save(), file -> file.getLooking());
	public static final OptionType<Boolean> INTERACT = new OptionType<>("Interact", "interact", npc -> true, (npc, value) -> npc.setNPCInteract(value), (file, value) -> file.setInteract(value).save(), file -> file.getInteract());
	public static final OptionType<Boolean> SILENT = new OptionType<>("Silent", "silent", npc -> true, (npc, value) -> npc.setNPCSilent(true), (file, value) -> file.setSilent(value), file -> file.getSilent());
	public static final OptionType<Boolean> GLOWING = new OptionType<>("Glowing", "glowing", npc -> true, (npc, value) -> npc.setNPCGlowing(value), (file, value) -> file.setGlowing(value).save(), file -> file.getGlowing());
	public static final OptionType<TextColor> GLOWCOLOR = new OptionType<>("GlowColor", "glowcolor", npc -> true, (npc, value) -> npc.setNPCGlowColor(value), (file, value) -> file.setGlowColor(value).save(), file -> file.getGlowColor().orElse(null));

	public static final OptionType<Boolean> BABY = new OptionType<>("Baby", "baby", npc -> (npc instanceof EntityZombie || npc instanceof EntityAgeable), (npc, value) -> npc.setNPCBaby(value), (file, value) -> file.setBaby(value).save(), file -> file.getBaby());
	public static final OptionType<Boolean> CHARGED = new OptionType<>("Charged", "charged", npc -> npc instanceof EntityCreeper, (npc, value) -> npc.setNPCCharged(value), (file, value) -> file.setCharged(value).save(), file -> file.getCharged());
	public static final OptionType<Boolean> ANGRY = new OptionType<>("Angry", "angry", npc -> npc instanceof EntityWolf, (npc, value) -> npc.setNPCAngry(value), (file, value) -> file.setAngry(value).save(), file -> file.getAngry());
	public static final OptionType<Integer> SIZE = new OptionType<>("Size", "size", npc -> npc instanceof EntitySlime, (npc, value) -> npc.setNPCSize(value), (file, value) -> file.setSize(value).save(), file -> file.getSize());
	public static final OptionType<Boolean> SITTING = new OptionType<>("Sitting", "sitting", npc -> npc instanceof EntityTameable, (npc, value) -> npc.setNPCSitting(value), (file, value) -> file.setSitting(value).save(), file -> file.getSitting());
	public static final OptionType<Career> CAREER = new OptionType<>("Career", "career", npc -> npc instanceof EntityVillager, (npc, value) -> npc.setNPCCareer(value), (file, value) -> file.setCareer(value).save(), file -> file.getCareer().orElse(null));
//	public static final OptionType<SkeletonType> SKELETONTYPE = new OptionType<>("Skeleton Type", "skeletontype");
//	public static final OptionType<ZombieType> ZOMBIETYPE = new OptionType<>("Zombie Type", "zombietype");
//	public static final OptionType<HorseVariant> HORSEVARIANT = new OptionType<>("Horse Variant", "horsevariant");
	public static final OptionType<HorseStyle> HORSESTYLE = new OptionType<>("Horse Style", "horsestyle", npc -> npc instanceof EntityHorse, (npc, value) -> npc.setNPCHorseStyle(value), (file, value) -> file.setHorseStyle(value).save(), file -> file.getHorseStyle().orElse(null));
	public static final OptionType<HorseColor> HORSECOLOR = new OptionType<>("Horse Color", "horsecolor", npc -> npc instanceof EntityHorse, (npc, value) -> npc.setNPCHorseColor(value), (file, value) -> file.setHorseColor(value).save(), file -> file.getHorseColor().orElse(null));
	public static final OptionType<LlamaVariant> LLAMAVARIANT = new OptionType<>("Llama Variant", "llamavariant", npc -> npc instanceof EntityLlama, (npc, value) -> npc.setNPCLlamaVariant(value), (file, value) -> file.setLlamaVariant(value).save(), file -> file.getLlamaVariant().orElse(null));
	public static final OptionType<OcelotType> CATTYPE = new OptionType<>("Cat Type", "cattype", npc -> npc instanceof EntityOcelot, (npc, value) -> npc.setNPCCatType(value), (file, value) -> file.setCatType(value).save(), file -> file.getCatType().orElse(null));
	public static final OptionType<RabbitType> RABBITTYPE = new OptionType<>("Rabbit Type", "cattype", npc -> npc instanceof EntityRabbit, (npc, value) -> npc.setNPCRabbitType(value), (file, value) -> file.setRabbitType(value).save(), file -> file.getRabbitType().orElse(null));

	public static final OptionType<ItemStack> HELMET = new OptionType<>("Helmet", "helmet", npc -> npc instanceof ArmorEquipable, (npc, value) -> npc.setNPCHelmet(value), (file, value) -> file.setHelmet(value).save(), file -> file.getHelmet().orElse(null));
	public static final OptionType<ItemStack> CHESTPLATE = new OptionType<>("Chestplate", "chestplate", npc -> npc instanceof ArmorEquipable, (npc, value) -> npc.setNPCChestplate(value), (file, value) -> file.setChestplate(value).save(), file -> file.getChestplate().orElse(null));
	public static final OptionType<ItemStack> LEGGINGS = new OptionType<>("Leggings", "leggings", npc -> npc instanceof ArmorEquipable, (npc, value) -> npc.setNPCLeggings(value), (file, value) -> file.setLeggings(value).save(), file -> file.getLeggings().orElse(null));
	public static final OptionType<ItemStack> BOOTS = new OptionType<>("Boots", "boots", npc -> npc instanceof ArmorEquipable, (npc, value) -> npc.setNPCBoots(value), (file, value) -> file.setBoots(value).save(), file -> file.getBoots().orElse(null));
	public static final OptionType<ItemStack> MAINHAND = new OptionType<>("Main Hand", "mainhand", npc -> npc instanceof ArmorEquipable, (npc, value) -> npc.setNPCMainHand(value), (file, value) -> file.setMainHand(value).save(), file -> file.getMainHand().orElse(null));
	public static final OptionType<ItemStack> OFFHAND = new OptionType<>("Off Hand", "offhand", npc -> npc instanceof ArmorEquipable, (npc, value) -> npc.setNPCOffHand(value), (file, value) -> file.setOffHand(value).save(), file -> file.getOffHand().orElse(null));
}
