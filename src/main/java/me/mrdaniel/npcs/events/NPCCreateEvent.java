package me.mrdaniel.npcs.events;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Cancellable;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.impl.AbstractEvent;
import org.spongepowered.api.plugin.PluginContainer;

public class NPCCreateEvent extends AbstractEvent implements Cancellable {

	private final Player player;
	private final EntityType type;
	private final Cause cause;

	private boolean cancelled;

	public NPCCreateEvent(@Nonnull final PluginContainer container, @Nonnull final Player player, @Nonnull final EntityType type) {
		this.player = player;
		this.type = type;
		this.cause = Cause.source(container).named("player", player).named("type", type).build();

		this.cancelled = false;
	}

	@Nonnull public Player getPlayer() { return this.player; }
	@Nonnull public EntityType getType() { return this.type; }
	@Nonnull @Override public Cause getCause() { return this.cause; }

	@Override public boolean isCancelled() { return this.cancelled; }
	@Override public void setCancelled(final boolean cancelled) { this.cancelled = cancelled; }
}