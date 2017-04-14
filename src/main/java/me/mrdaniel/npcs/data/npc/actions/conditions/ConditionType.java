package me.mrdaniel.npcs.data.npc.actions.conditions;

import javax.annotation.Nonnull;

public enum ConditionType {

	ITEM("Item", "item"),
	EXP("EXP", "exp");

	private final String name;
	private final String id;

	ConditionType(String name, String id) {
		this.name = name;
		this.id = id;
	}

	@Nonnull
	public String getName() {
		return this.name;
	}

	@Nonnull
	public String getId() {
		return this.id;
	}

	@Nonnull
	protected static ConditionType of(@Nonnull final String id) {
		return id.equalsIgnoreCase("item") ? ITEM : EXP;
	}
}