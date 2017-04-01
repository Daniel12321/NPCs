package me.mrdaniel.npcs;

import javax.annotation.Nonnull;

import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.Server;
import org.spongepowered.api.plugin.PluginContainer;

public abstract class NPCObject {

	private final NPCs npcs;

	public NPCObject(@Nonnull final NPCs npcs) {
		this.npcs = npcs;
	}

	@Nonnull public NPCs getNPCs() { return this.npcs; }
	@Nonnull public Game getGame() { return this.npcs.getGame(); }
	@Nonnull public Server getServer() { return this.npcs.getGame().getServer(); }
	@Nonnull public Logger getLogger() { return this.npcs.getLogger(); }
	@Nonnull public PluginContainer getContainer() { return this.npcs.getContainer(); }
}