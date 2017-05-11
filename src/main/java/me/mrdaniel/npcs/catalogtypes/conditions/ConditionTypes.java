package me.mrdaniel.npcs.catalogtypes.conditions;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import me.mrdaniel.npcs.data.npc.actions.conditions.ConditionExp;
import me.mrdaniel.npcs.data.npc.actions.conditions.ConditionItem;
import me.mrdaniel.npcs.data.npc.actions.conditions.ConditionLevel;

public final class ConditionTypes {

	public static final ConditionType ITEM = new ConditionType("Item", "item", ConditionItem.class);
	public static final ConditionType LEVEL = new ConditionType("Level", "level", ConditionLevel.class);
	public static final ConditionType EXP = new ConditionType("EXP", "exp", ConditionExp.class);

	public static final List<ConditionType> VALUES = Lists.newArrayList(ITEM, LEVEL, EXP);

	@Nullable
	public static Optional<ConditionType> of(@Nonnull final String id) {
		for (ConditionType type : VALUES) { if (type.getId().equalsIgnoreCase(id)) { return Optional.of(type); } }
		return Optional.empty();
	}
}