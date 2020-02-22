package me.mrdaniel.npcs.catalogtypes.propertytype;

import com.google.common.collect.Lists;
import me.mrdaniel.npcs.catalogtypes.career.Career;
import me.mrdaniel.npcs.catalogtypes.cattype.CatType;
import me.mrdaniel.npcs.catalogtypes.glowcolor.GlowColor;
import me.mrdaniel.npcs.catalogtypes.horsecolor.HorseColor;
import me.mrdaniel.npcs.catalogtypes.horsepattern.HorsePattern;
import me.mrdaniel.npcs.catalogtypes.llamatype.LlamaType;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.catalogtypes.parrottype.ParrotType;
import me.mrdaniel.npcs.catalogtypes.propertytype.types.*;
import me.mrdaniel.npcs.catalogtypes.rabbittype.RabbitType;
import me.mrdaniel.npcs.utils.Position;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.world.World;

import java.util.List;
import java.util.UUID;

public final class PropertyTypes {

	public static final PropertyType<NPCType> TYPE = new PropertyNPCType();
	public static final PropertyType<World> WORLD = new PropertyWorld();
	public static final PropertyType<String> WORLD_NAME = new PropertyWorldName();
	public static final PropertyType<Position> POSITION = new PropertyPosition();
	public static final PropertyType<String> NAME = new PropertyName();
	public static final PropertyType<Boolean> NAME_VISIBLE = new PropertyNameVisible();
	public static final PropertyType<UUID> SKIN_UUID = new PropertySkinUUID();
	public static final PropertyType<String> SKIN_NAME = new PropertySkinName();
	public static final PropertyType<Boolean> LOOKING = new PropertyLooking();
	public static final PropertyType<Boolean> INTERACT = new PropertyInteract();
	public static final PropertyType<Boolean> SILENT = new PropertySilent();
	public static final PropertyType<Boolean> GLOWING = new PropertyGlowing();
	public static final PropertyType<GlowColor> GLOWCOLOR = new PropertyGlowColor();

	public static final PropertyType<Boolean> BABY = new PropertyBaby();
	public static final PropertyType<Boolean> CHARGED = new PropertyCharged();
	public static final PropertyType<Boolean> ANGRY = new PropertyAngry();
	public static final PropertyType<Integer> SIZE = new PropertySize();
	public static final PropertyType<Boolean> SITTING = new PropertySitting();
	public static final PropertyType<Boolean> SADDLE = new PropertySaddle();
	public static final PropertyType<Boolean> HANGING = new PropertyHanging();
	public static final PropertyType<Boolean> PUMPKIN = new PropertyPumpkin();
	public static final PropertyType<Career> CAREER = new PropertyCareer();
	public static final PropertyType<HorsePattern> HORSEPATTERN = new PropertyHorsePattern();
	public static final PropertyType<HorseColor> HORSECOLOR = new PropertyHorseColor();
	public static final PropertyType<LlamaType> LLAMATYPE = new PropertyLlamaType();
	public static final PropertyType<CatType> CATTYPE = new PropertyCatType();
	public static final PropertyType<RabbitType> RABBITTYPE = new PropertyRabbitType();
	public static final PropertyType<ParrotType> PARROTTYPE = new PropertyParrotType();

	public static final PropertyType<ItemStack> HELMET = new PropertyHelmet();
	public static final PropertyType<ItemStack> CHESTPLATE = new PropertyChestplate();
	public static final PropertyType<ItemStack> LEGGINGS = new PropertyLeggings();
	public static final PropertyType<ItemStack> BOOTS = new PropertyBoots();
	public static final PropertyType<ItemStack> MAINHAND = new PropertyMainHand();
	public static final PropertyType<ItemStack> OFFHAND = new PropertyOffHand();

//	public static final List<PropertyType> ARMOR = Lists.newArrayList(HELMET, CHESTPLATE, LEGGINGS, BOOTS, MAINHAND, OFFHAND);
//	public static final List<PropertyType> MAIN = Lists.newArrayList(
//			NAME, NAME_VISIBLE, LOOKING, INTERACT, SILENT, GLOWING, GLOWCOLOR,
//			BABY, CHARGED, ANGRY, SIZE, SITTING, SADDLE, HANGING, PUMPKIN, CAREER,
//			HORSEPATTERN, HORSECOLOR, LLAMATYPE, CATTYPE, RABBITTYPE, PARROTTYPE);

	public static final List<PropertyType> NPC_INIT = Lists.newArrayList(
			POSITION, NAME, NAME_VISIBLE, SKIN_UUID, LOOKING, INTERACT, SILENT, GLOWING,
			GLOWCOLOR, BABY, CHARGED, ANGRY, SIZE, SITTING, SADDLE, HANGING,
			PUMPKIN, CAREER, HORSEPATTERN, HORSECOLOR, LLAMATYPE, CATTYPE, RABBITTYPE, PARROTTYPE,
			HELMET, CHESTPLATE, LEGGINGS, BOOTS, MAINHAND, OFFHAND
	);
	public static final List<PropertyType> ALL = Lists.newArrayList(NPC_INIT);

	// TODO: STUFF
	static {
		ALL.add(TYPE);
		ALL.add(WORLD);
		ALL.add(WORLD_NAME);
		ALL.add(SKIN_NAME);
	}
}
