package me.mrdaniel.npcs.catalogtypes.parrottype;

import com.google.common.collect.Lists;

import java.util.List;

public class ParrotTypes {

	public static final ParrotType RED = new ParrotType("Red", "red", 0);
	public static final ParrotType BLUE = new ParrotType("Blue", "blue", 1);
	public static final ParrotType GREEN = new ParrotType("Green", "green", 2);
	public static final ParrotType CYAN = new ParrotType("Cyan", "cyan", 3);
	public static final ParrotType SILVER = new ParrotType("Silver", "silver", 4);

	public static final List<ParrotType> ALL = Lists.newArrayList(ParrotTypes.RED, ParrotTypes.BLUE, ParrotTypes.GREEN, ParrotTypes.CYAN, ParrotTypes.SILVER);
}
