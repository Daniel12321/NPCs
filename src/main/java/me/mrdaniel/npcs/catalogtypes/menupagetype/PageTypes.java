package me.mrdaniel.npcs.catalogtypes.menupagetype;

import com.google.common.collect.Lists;

import java.util.List;

public final class PageTypes {

	public static final PageType MAIN = new PageType("Main", "main");
	public static final PageType ARMOR = new PageType("Armor", "armor");
	public static final PageType ACTIONS = new PageType("Actions", "actions");

	public static final List<PageType> ALL = Lists.newArrayList(MAIN, ARMOR, ACTIONS);
}
