package me.mrdaniel.npcs.catalogtypes.dyecolor;

import com.google.common.collect.Lists;
import net.minecraft.item.EnumDyeColor;

import java.util.List;

public class DyeColorTypes {

	public static final DyeColorType BLACK = new DyeColorType("Black", "black", EnumDyeColor.BLACK);
	public static final DyeColorType BLUE = new DyeColorType("Blue", "blue", EnumDyeColor.BLUE);
	public static final DyeColorType BROWN = new DyeColorType("Brown", "brown", EnumDyeColor.BROWN);
	public static final DyeColorType CYAN = new DyeColorType("Cyan", "cyan", EnumDyeColor.CYAN);
	public static final DyeColorType GRAY = new DyeColorType("Gray", "gray", EnumDyeColor.GRAY);
	public static final DyeColorType GREEN = new DyeColorType("Green", "green", EnumDyeColor.GREEN);
	public static final DyeColorType LIGHT_BLUE = new DyeColorType("Light Blue", "light_blue", EnumDyeColor.LIGHT_BLUE);
	public static final DyeColorType LIME = new DyeColorType("Lime", "lime", EnumDyeColor.LIME);
	public static final DyeColorType MAGENTA = new DyeColorType("Magenta", "magenta", EnumDyeColor.MAGENTA);
	public static final DyeColorType ORANGE = new DyeColorType("Orange", "orange", EnumDyeColor.ORANGE);
	public static final DyeColorType PINK = new DyeColorType("Pink", "pink", EnumDyeColor.PINK);
	public static final DyeColorType PURPLE = new DyeColorType("Purple", "purple", EnumDyeColor.PURPLE);
	public static final DyeColorType RED = new DyeColorType("Red", "red", EnumDyeColor.RED);
	public static final DyeColorType SILVER = new DyeColorType("Silver", "silver", EnumDyeColor.SILVER);
	public static final DyeColorType WHITE = new DyeColorType("White", "white", EnumDyeColor.WHITE);
	public static final DyeColorType YELLOW = new DyeColorType("Yellow", "yellow", EnumDyeColor.YELLOW);

	public static final List<DyeColorType> ALL = Lists.newArrayList(BLACK, BLUE, BROWN, CYAN, GRAY, GREEN, LIGHT_BLUE, LIME, MAGENTA, ORANGE, PINK, PURPLE, RED, SILVER, WHITE, YELLOW);
}
