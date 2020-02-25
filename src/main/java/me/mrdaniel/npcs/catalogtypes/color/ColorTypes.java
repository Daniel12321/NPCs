package me.mrdaniel.npcs.catalogtypes.color;

import com.google.common.collect.Lists;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.text.TextFormatting;

import java.util.List;

public class ColorTypes {

	public static final ColorType BLACK = new ColorType("Black", "black", TextFormatting.BLACK);
	public static final ColorType DARK_BLUE = new ColorType("Dark Blue", "dark_blue", TextFormatting.DARK_BLUE);
	public static final ColorType DARK_GREEN = new ColorType("Dark Green", "dark_green", TextFormatting.DARK_GREEN);
	public static final ColorType DARK_AQUA = new ColorType("Dark Aqua", "dark_aqua", TextFormatting.DARK_AQUA);
	public static final ColorType DARK_RED = new ColorType("Dark Red", "dark_red", TextFormatting.DARK_RED);
	public static final ColorType DARK_PURPLE = new ColorType("Dark Purple", "dark_purple", TextFormatting.DARK_PURPLE);
	public static final ColorType GOLD = new ColorType("Gold", "gold", TextFormatting.GOLD);
	public static final ColorType GRAY = new ColorType("Gray", "gray", TextFormatting.GRAY);
	public static final ColorType DARK_GRAY = new ColorType("Dark Gray", "dark_gray", TextFormatting.DARK_GRAY);
	public static final ColorType BLUE = new ColorType("Blue", "blue", TextFormatting.BLUE);
	public static final ColorType GREEN = new ColorType("Green", "green", TextFormatting.GREEN);
	public static final ColorType AQUA = new ColorType("Aqua", "aqua", TextFormatting.AQUA);
	public static final ColorType RED = new ColorType("Red", "red", TextFormatting.RED);
	public static final ColorType LIGHT_PURPLE = new ColorType("Light Purple", "light_purple", TextFormatting.LIGHT_PURPLE);
	public static final ColorType WHITE = new ColorType("White", "white", TextFormatting.WHITE);
	public static final ColorType YELLOW = new ColorType("Yellow", "yellow", TextFormatting.YELLOW);

	public static final List<ColorType> ALL = Lists.newArrayList(BLACK, DARK_BLUE, DARK_GREEN, DARK_AQUA, DARK_RED, DARK_PURPLE, GOLD, GRAY, DARK_GRAY, BLUE, GREEN, AQUA, RED, LIGHT_PURPLE, WHITE, YELLOW);
}
