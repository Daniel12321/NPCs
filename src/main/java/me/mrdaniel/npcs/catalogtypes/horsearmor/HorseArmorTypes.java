package me.mrdaniel.npcs.catalogtypes.horsearmor;

import com.google.common.collect.Lists;

import java.util.List;

public class HorseArmorTypes {

	public static final HorseArmorType NONE = new HorseArmorType("None", "none", 0);
	public static final HorseArmorType IRON = new HorseArmorType("Iron", "iron", 1);
	public static final HorseArmorType GOLD = new HorseArmorType("Gold", "gold", 2);
	public static final HorseArmorType DIAMOND = new HorseArmorType("Diamond", "diamond", 3);

	public static final List<HorseArmorType> ALL = Lists.newArrayList(NONE, IRON, GOLD, DIAMOND);
}
