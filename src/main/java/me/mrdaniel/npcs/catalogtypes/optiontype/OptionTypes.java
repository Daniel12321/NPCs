package me.mrdaniel.npcs.catalogtypes.optiontype;

import com.google.common.collect.Lists;
import me.mrdaniel.npcs.catalogtypes.career.Career;
import me.mrdaniel.npcs.catalogtypes.cattype.CatType;
import me.mrdaniel.npcs.catalogtypes.glowcolor.GlowColor;
import me.mrdaniel.npcs.catalogtypes.horsecolor.HorseColor;
import me.mrdaniel.npcs.catalogtypes.horsepattern.HorsePattern;
import me.mrdaniel.npcs.catalogtypes.llamatype.LlamaType;
import me.mrdaniel.npcs.catalogtypes.optiontype.types.*;
import me.mrdaniel.npcs.catalogtypes.parrottype.ParrotType;
import me.mrdaniel.npcs.catalogtypes.rabbittype.RabbitType;
import me.mrdaniel.npcs.utils.Position;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.world.World;

import java.util.List;

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

	public static final List<OptionType> ARMOR = Lists.newArrayList(
			OptionTypes.HELMET, OptionTypes.CHESTPLATE, OptionTypes.LEGGINGS, OptionTypes.BOOTS, OptionTypes.MAINHAND, OptionTypes.OFFHAND);
	public static final List<OptionType> MAIN = Lists.newArrayList(
			OptionTypes.NAME, OptionTypes.LOOKING, OptionTypes.INTERACT, OptionTypes.GLOWING, OptionTypes.GLOWCOLOR, OptionTypes.BABY,
			OptionTypes.CHARGED, OptionTypes.ANGRY, OptionTypes.SIZE, OptionTypes.SITTING, OptionTypes.SADDLE, OptionTypes.CAREER,
			OptionTypes.HORSEPATTERN, OptionTypes.HORSECOLOR, OptionTypes.LLAMATYPE, OptionTypes.CATTYPE, OptionTypes.RABBITTYPE, OptionTypes.PARROTTYPE);
	public static final List<OptionType> ALL = Lists.newArrayList();

	static {
		ALL.addAll(MAIN);
		ALL.addAll(ARMOR);
	}
}
