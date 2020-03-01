package me.mrdaniel.npcs.catalogtypes.propertytype;

import com.google.common.collect.Lists;
import me.mrdaniel.npcs.catalogtypes.aitype.AIType;
import me.mrdaniel.npcs.catalogtypes.career.Career;
import me.mrdaniel.npcs.catalogtypes.cattype.CatType;
import me.mrdaniel.npcs.catalogtypes.color.ColorType;
import me.mrdaniel.npcs.catalogtypes.dyecolor.DyeColorType;
import me.mrdaniel.npcs.catalogtypes.horsearmor.HorseArmorType;
import me.mrdaniel.npcs.catalogtypes.horsecolor.HorseColor;
import me.mrdaniel.npcs.catalogtypes.horsepattern.HorsePattern;
import me.mrdaniel.npcs.catalogtypes.llamatype.LlamaType;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.catalogtypes.parrottype.ParrotType;
import me.mrdaniel.npcs.catalogtypes.propertytype.types.*;
import me.mrdaniel.npcs.catalogtypes.rabbittype.RabbitType;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.item.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

public final class PropertyTypes {

	public static final PropertyType<NPCType> TYPE = new PropertyNPCType();
	public static final PropertyType<String> NAME = new PropertyName();
	public static final PropertyType<Boolean> NAME_VISIBLE = new PropertyNameVisible();
	public static final PropertyType<UUID> SKIN_UUID = new PropertySkinUUID();
	public static final PropertyType<String> SKIN = new PropertySkin();
	public static final PropertyType<Boolean> INTERACT = new PropertyInteract();
	public static final PropertyType<Boolean> SILENT = new PropertySilent();
	public static final PropertyType<Boolean> BURNING = new PropertyBurning();
	public static final PropertyType<Boolean> GLOWING = new PropertyGlowing();
	public static final PropertyType<ColorType> GLOWCOLOR = new PropertyGlowColor();

	public static final PropertyType<Boolean> ANGRY = new PropertyAngry();
	public static final PropertyType<Boolean> ARMORED = new PropertyArmored();
	public static final PropertyType<Boolean> BABY = new PropertyBaby();
	public static final PropertyType<Boolean> CHARGED = new PropertyCharged();
	public static final PropertyType<Boolean> CHEST = new PropertyChest();
	public static final PropertyType<Boolean> EATING = new PropertyEating();
	public static final PropertyType<Boolean> HANGING = new PropertyHanging();
	public static final PropertyType<Boolean> PEEKING = new PropertyPeeking();
	public static final PropertyType<Boolean> PUMPKIN = new PropertyPumpkin();
	public static final PropertyType<Boolean> SCREAMING = new PropertyScreaming();
	public static final PropertyType<Boolean> SHEARED = new PropertySheared();
	public static final PropertyType<Boolean> SITTING = new PropertySitting();
	public static final PropertyType<Boolean> SADDLE = new PropertySaddle();
	public static final PropertyType<Integer> SIZE = new PropertySize();
	public static final PropertyType<BlockType> CARRIES = new PropertyCarries();
	public static final PropertyType<Career> CAREER = new PropertyCareer();
	public static final PropertyType<DyeColorType> COLLARCOLOR = new PropertyCollarColor();
	public static final PropertyType<DyeColorType> WOOLCOLOR = new PropertyWoolColor();
	public static final PropertyType<DyeColorType> SHULKERCOLOR = new PropertyShulkerColor();
	public static final PropertyType<HorseArmorType> HORSEARMOR = new PropertyHorseArmorType();
	public static final PropertyType<HorseColor> HORSECOLOR = new PropertyHorseColor();
	public static final PropertyType<HorsePattern> HORSEPATTERN = new PropertyHorsePattern();
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

	public static final PropertyType<Boolean> LOOKING = new PropertyLooking();
	public static final PropertyType<AIType> AITYPE = new PropertyAIType();

	public static final List<PropertyType> ALL = Lists.newArrayList(
			TYPE, NAME, NAME_VISIBLE, SKIN_UUID, SKIN, INTERACT, SILENT, GLOWING,
			GLOWCOLOR, BABY, CHARGED, ANGRY, SIZE, SITTING, SADDLE, HANGING,
			PUMPKIN, CAREER, HORSEPATTERN, HORSECOLOR, LLAMATYPE, CATTYPE, RABBITTYPE, PARROTTYPE,
			HELMET, CHESTPLATE, LEGGINGS, BOOTS, MAINHAND, OFFHAND,
			LOOKING, AITYPE);
	public static final List<PropertyType> NPC_INIT = Lists.newArrayList(ALL);
	public static final List<PropertyType<ItemStack>> EQUIPMENT = Lists.newArrayList(HELMET, CHESTPLATE, LEGGINGS, BOOTS, MAINHAND, OFFHAND);

	static {
		NPC_INIT.remove(TYPE);
		NPC_INIT.remove(SKIN);
	}
}
