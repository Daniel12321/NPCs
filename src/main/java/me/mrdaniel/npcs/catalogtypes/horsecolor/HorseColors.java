package me.mrdaniel.npcs.catalogtypes.horsecolor;

import com.google.common.collect.Lists;

import java.util.List;

public class HorseColors {

	public static final HorseColor WHITE = new HorseColor("White", "white", 0);
	public static final HorseColor CREAMY = new HorseColor("Creamy", "creamy", 1);
	public static final HorseColor CHESTNUT = new HorseColor("Chestnut", "chestnut", 2);
	public static final HorseColor BROWN = new HorseColor("Brown", "brown", 3);
	public static final HorseColor BLACK = new HorseColor("Black", "black", 4);
	public static final HorseColor GRAY = new HorseColor("Gray", "gray", 5);
	public static final HorseColor DARK_BROWN = new HorseColor("DarkBrown", "dark_brown", 6);

	public static final List<HorseColor> ALL = Lists.newArrayList(WHITE, CREAMY, CHESTNUT, BROWN, BLACK, GRAY, DARK_BROWN);
}
