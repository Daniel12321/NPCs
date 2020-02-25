package me.mrdaniel.npcs.catalogtypes.horsearmor;

import com.google.common.collect.Lists;
import org.spongepowered.api.item.ItemTypes;

import java.util.List;

public class HorseArmorTypes {

	public static final HorseArmorType NONE = new HorseArmorType("None", "none", null);
	public static final HorseArmorType IRON = new HorseArmorType("Iron", "iron", ItemTypes.IRON_HORSE_ARMOR);
	public static final HorseArmorType GOLD = new HorseArmorType("Gold", "gold", ItemTypes.GOLDEN_HORSE_ARMOR);
	public static final HorseArmorType DIAMOND = new HorseArmorType("Diamond", "diamond", ItemTypes.DIAMOND_HORSE_ARMOR);

	public static final List<HorseArmorType> ALL = Lists.newArrayList(NONE, IRON, GOLD, DIAMOND);
}
