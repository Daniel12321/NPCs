package me.mrdaniel.npcs.data.action;

import javax.annotation.Nonnull;

public enum ActionType {

	MESSAGE("Message", "message"),
	CONSOLECMD("Console Command", "consolecmd"),
	PLAYERCMD("Player Command", "playercmd");

	private final String name;
	private final String id;

	ActionType(String name, String id) {
		this.name = name;
		this.id = id;
	}

	@Nonnull
	public String getId() {
		return this.id;
	}

	@Nonnull
	protected static ActionType of(@Nonnull final String id) {
		return id.equalsIgnoreCase("message") ? MESSAGE : id.equalsIgnoreCase("consolecmd") ? CONSOLECMD : PLAYERCMD;
	}

	@Nonnull
	public String getName() {
		return this.name;
	}
}