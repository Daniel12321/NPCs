package me.mrdaniel.npcs.catalogtypes.options;

import org.spongepowered.api.data.type.Career;
import org.spongepowered.api.data.type.HorseColor;
import org.spongepowered.api.data.type.HorseStyle;
import org.spongepowered.api.data.type.LlamaVariant;
import org.spongepowered.api.data.type.OcelotType;
import org.spongepowered.api.data.type.RabbitType;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.world.World;

import me.mrdaniel.npcs.catalogtypes.options.types.OptionAngry;
import me.mrdaniel.npcs.catalogtypes.options.types.OptionBaby;
import me.mrdaniel.npcs.catalogtypes.options.types.OptionBoots;
import me.mrdaniel.npcs.catalogtypes.options.types.OptionCareer;
import me.mrdaniel.npcs.catalogtypes.options.types.OptionCatType;
import me.mrdaniel.npcs.catalogtypes.options.types.OptionCharged;
import me.mrdaniel.npcs.catalogtypes.options.types.OptionChestplate;
import me.mrdaniel.npcs.catalogtypes.options.types.OptionGlowColor;
import me.mrdaniel.npcs.catalogtypes.options.types.OptionGlowing;
import me.mrdaniel.npcs.catalogtypes.options.types.OptionHelmet;
import me.mrdaniel.npcs.catalogtypes.options.types.OptionHorseColor;
import me.mrdaniel.npcs.catalogtypes.options.types.OptionHorseStyle;
import me.mrdaniel.npcs.catalogtypes.options.types.OptionInteract;
import me.mrdaniel.npcs.catalogtypes.options.types.OptionLeggings;
import me.mrdaniel.npcs.catalogtypes.options.types.OptionLlamaVariant;
import me.mrdaniel.npcs.catalogtypes.options.types.OptionLooking;
import me.mrdaniel.npcs.catalogtypes.options.types.OptionMainHand;
import me.mrdaniel.npcs.catalogtypes.options.types.OptionName;
import me.mrdaniel.npcs.catalogtypes.options.types.OptionOffHand;
import me.mrdaniel.npcs.catalogtypes.options.types.OptionPosition;
import me.mrdaniel.npcs.catalogtypes.options.types.OptionRabbitType;
import me.mrdaniel.npcs.catalogtypes.options.types.OptionSaddle;
import me.mrdaniel.npcs.catalogtypes.options.types.OptionSilent;
import me.mrdaniel.npcs.catalogtypes.options.types.OptionSitting;
import me.mrdaniel.npcs.catalogtypes.options.types.OptionSize;
import me.mrdaniel.npcs.catalogtypes.options.types.OptionSkin;
import me.mrdaniel.npcs.catalogtypes.options.types.OptionWorld;
import me.mrdaniel.npcs.utils.Position;

public final class OptionTypes {

	public static final OptionType<World> WORLD = new OptionWorld();
	public static final OptionType<Position> POSITION = new OptionPosition();
	public static final OptionType<String> NAME = new OptionName();
	public static final OptionType<String> SKIN = new OptionSkin();
	public static final OptionType<Boolean> LOOKING = new OptionLooking();
	public static final OptionType<Boolean> INTERACT = new OptionInteract();
	public static final OptionType<Boolean> SILENT = new OptionSilent();
	public static final OptionType<Boolean> GLOWING = new OptionGlowing();
	public static final OptionType<TextColor> GLOWCOLOR = new OptionGlowColor();

	public static final OptionType<Boolean> BABY = new OptionBaby();
	public static final OptionType<Boolean> CHARGED = new OptionCharged();
	public static final OptionType<Boolean> ANGRY = new OptionAngry();
	public static final OptionType<Integer> SIZE = new OptionSize();
	public static final OptionType<Boolean> SITTING = new OptionSitting();
	public static final OptionType<Boolean> SADDLE = new OptionSaddle();
	public static final OptionType<Career> CAREER = new OptionCareer();
//	public static final OptionType<SkeletonType> SKELETONTYPE = new OptionSkeletonType();
//	public static final OptionType<ZombieType> ZOMBIETYPE = new OptionZombieType();
//	public static final OptionType<HorseVariant> HORSEVARIANT = new OptionHorseVariant();
	public static final OptionType<HorseStyle> HORSESTYLE = new OptionHorseStyle();
	public static final OptionType<HorseColor> HORSECOLOR = new OptionHorseColor();
	public static final OptionType<LlamaVariant> LLAMAVARIANT = new OptionLlamaVariant();
	public static final OptionType<OcelotType> CATTYPE = new OptionCatType();
	public static final OptionType<RabbitType> RABBITTYPE = new OptionRabbitType();

	public static final OptionType<ItemStack> HELMET = new OptionHelmet();
	public static final OptionType<ItemStack> CHESTPLATE = new OptionChestplate();
	public static final OptionType<ItemStack> LEGGINGS = new OptionLeggings();
	public static final OptionType<ItemStack> BOOTS = new OptionBoots();
	public static final OptionType<ItemStack> MAINHAND = new OptionMainHand();
	public static final OptionType<ItemStack> OFFHAND = new OptionOffHand();
}
