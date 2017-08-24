package me.mrdaniel.npcs.catalogtypes.optiontype;

import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.world.World;

import me.mrdaniel.npcs.catalogtypes.career.Career;
import me.mrdaniel.npcs.catalogtypes.cattype.CatType;
import me.mrdaniel.npcs.catalogtypes.glowcolor.GlowColor;
import me.mrdaniel.npcs.catalogtypes.horsecolor.HorseColor;
import me.mrdaniel.npcs.catalogtypes.horsepattern.HorsePattern;
import me.mrdaniel.npcs.catalogtypes.llamatype.LlamaType;
import me.mrdaniel.npcs.catalogtypes.optiontype.types.OptionAngry;
import me.mrdaniel.npcs.catalogtypes.optiontype.types.OptionBaby;
import me.mrdaniel.npcs.catalogtypes.optiontype.types.OptionBoots;
import me.mrdaniel.npcs.catalogtypes.optiontype.types.OptionCareer;
import me.mrdaniel.npcs.catalogtypes.optiontype.types.OptionCatType;
import me.mrdaniel.npcs.catalogtypes.optiontype.types.OptionCharged;
import me.mrdaniel.npcs.catalogtypes.optiontype.types.OptionChestplate;
import me.mrdaniel.npcs.catalogtypes.optiontype.types.OptionGlowColor;
import me.mrdaniel.npcs.catalogtypes.optiontype.types.OptionGlowing;
import me.mrdaniel.npcs.catalogtypes.optiontype.types.OptionHanging;
import me.mrdaniel.npcs.catalogtypes.optiontype.types.OptionHelmet;
import me.mrdaniel.npcs.catalogtypes.optiontype.types.OptionHorseColor;
import me.mrdaniel.npcs.catalogtypes.optiontype.types.OptionHorsePattern;
import me.mrdaniel.npcs.catalogtypes.optiontype.types.OptionInteract;
import me.mrdaniel.npcs.catalogtypes.optiontype.types.OptionLeggings;
import me.mrdaniel.npcs.catalogtypes.optiontype.types.OptionLlamaType;
import me.mrdaniel.npcs.catalogtypes.optiontype.types.OptionLooking;
import me.mrdaniel.npcs.catalogtypes.optiontype.types.OptionMainHand;
import me.mrdaniel.npcs.catalogtypes.optiontype.types.OptionName;
import me.mrdaniel.npcs.catalogtypes.optiontype.types.OptionOffHand;
import me.mrdaniel.npcs.catalogtypes.optiontype.types.OptionParrotType;
import me.mrdaniel.npcs.catalogtypes.optiontype.types.OptionPosition;
import me.mrdaniel.npcs.catalogtypes.optiontype.types.OptionPumpkin;
import me.mrdaniel.npcs.catalogtypes.optiontype.types.OptionRabbitType;
import me.mrdaniel.npcs.catalogtypes.optiontype.types.OptionSaddle;
import me.mrdaniel.npcs.catalogtypes.optiontype.types.OptionSilent;
import me.mrdaniel.npcs.catalogtypes.optiontype.types.OptionSitting;
import me.mrdaniel.npcs.catalogtypes.optiontype.types.OptionSize;
import me.mrdaniel.npcs.catalogtypes.optiontype.types.OptionSkin;
import me.mrdaniel.npcs.catalogtypes.optiontype.types.OptionWorld;
import me.mrdaniel.npcs.catalogtypes.parrottype.ParrotType;
import me.mrdaniel.npcs.catalogtypes.rabbittype.RabbitType;
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
	public static final OptionType<GlowColor> GLOWCOLOR = new OptionGlowColor();

	public static final OptionType<Boolean> BABY = new OptionBaby();
	public static final OptionType<Boolean> CHARGED = new OptionCharged();
	public static final OptionType<Boolean> ANGRY = new OptionAngry();
	public static final OptionType<Integer> SIZE = new OptionSize();
	public static final OptionType<Boolean> SITTING = new OptionSitting();
	public static final OptionType<Boolean> SADDLE = new OptionSaddle();
	public static final OptionType<Boolean> HANGING = new OptionHanging();
	public static final OptionType<Boolean> PUMPKIN = new OptionPumpkin();
	public static final OptionType<Career> CAREER = new OptionCareer();
	public static final OptionType<HorsePattern> HORSEPATTERN = new OptionHorsePattern();
	public static final OptionType<HorseColor> HORSECOLOR = new OptionHorseColor();
	public static final OptionType<LlamaType> LLAMATYPE = new OptionLlamaType();
	public static final OptionType<CatType> CATTYPE = new OptionCatType();
	public static final OptionType<RabbitType> RABBITTYPE = new OptionRabbitType();
	public static final OptionType<ParrotType> PARROTTYPE = new OptionParrotType();

	public static final OptionType<ItemStack> HELMET = new OptionHelmet();
	public static final OptionType<ItemStack> CHESTPLATE = new OptionChestplate();
	public static final OptionType<ItemStack> LEGGINGS = new OptionLeggings();
	public static final OptionType<ItemStack> BOOTS = new OptionBoots();
	public static final OptionType<ItemStack> MAINHAND = new OptionMainHand();
	public static final OptionType<ItemStack> OFFHAND = new OptionOffHand();
}
