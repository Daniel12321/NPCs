package me.mrdaniel.npcs.catalogtypes.horsepattern;

import com.google.common.collect.Lists;

import java.util.List;

public class HorsePatterns {

	public static final HorsePattern NONE = new HorsePattern("None", "none", 0);
	public static final HorsePattern WHITE = new HorsePattern("White", "white", 256);
	public static final HorsePattern WHITE_FIELDS = new HorsePattern("WhiteFields", "white_fields", 512);
	public static final HorsePattern WHITE_DOTS = new HorsePattern("WhiteDots", "white_dots", 768);
	public static final HorsePattern BLACK_DOTS = new HorsePattern("BlackDots", "black_dots", 1024);

	public static final List<HorsePattern> ALL = Lists.newArrayList(NONE, WHITE, WHITE_FIELDS, WHITE_DOTS, BLACK_DOTS);
}
