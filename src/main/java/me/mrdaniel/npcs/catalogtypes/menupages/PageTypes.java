package me.mrdaniel.npcs.catalogtypes.menupages;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.Lists;

public final class PageTypes {

	public static final PageType MAIN = new PageType("Main", "main");
	public static final PageType ARMOR = new PageType("Armor", "armor");
	public static final PageType ACTIONS = new PageType("Actions", "actions");

	public static final List<PageType> VALUES = Lists.newArrayList(MAIN, ARMOR, ACTIONS);

	@Nullable
	public static Optional<PageType> of(@Nonnull final String id) {
		for (PageType type : VALUES) { if (type.getId().equalsIgnoreCase(id)) { return Optional.of(type); } }
		return Optional.empty();
	}
}