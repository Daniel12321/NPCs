package me.mrdaniel.npcs.event;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Cancellable;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.impl.AbstractEvent;
import org.spongepowered.api.plugin.PluginContainer;

public class NPCEvent extends AbstractEvent implements Cancellable {

	private final Player player;
	private final Cause cause;

	private boolean cancelled;

	protected NPCEvent(@Nonnull final Cause cause, @Nonnull final Player player) {
		this.player = player;
		this.cause = cause;

		this.cancelled = false;
	}

	@Nonnull public Player getPlayer() { return this.player; }
	@Override public Cause getCause() { return this.cause; }
	@Override public boolean isCancelled() { return this.cancelled; }
	@Override public void setCancelled(final boolean cancelled) { this.cancelled = cancelled; }

	public static class Interact extends NPCEvent {

		private final Living npc;

		public Interact(@Nonnull final PluginContainer container, @Nonnull final Player player, @Nonnull final Living npc) {
			super(Cause.source(container).named("player", player).named("npc", npc).build(), player);

			this.npc = npc;
		}

		@Nonnull public Living getNPC() { return this.npc; }
	}

	public static class Edit extends NPCEvent {

		private final Living npc;

		public Edit(@Nonnull final PluginContainer container, @Nonnull final Player player, @Nonnull final Living npc) {
			super(Cause.source(container).named("player", player).named("npc", npc).build(), player);

			this.npc = npc;
		}

		@Nonnull public Living getNPC() { return this.npc; }
	}

	public static class Select extends NPCEvent {

		private final Living npc;

		public Select(@Nonnull final PluginContainer container, @Nonnull final Player player, @Nonnull final Living npc) {
			super(Cause.source(container).named("player", player).named("npc", npc).build(), player);

			this.npc = npc;
		}

		@Nonnull public Living getNPC() { return this.npc; }
	}

	public static class Create extends NPCEvent {

		private final EntityType type;

		public Create(@Nonnull final PluginContainer container, @Nonnull final Player player, @Nonnull final EntityType type) {
			super(Cause.source(container).named("player", player).named("type", type).build(), player);

			this.type = type;
		}

		@Nonnull public EntityType getType() { return this.type; }
	}

	public static class Remove extends NPCEvent {

		private final Living npc;

		public Remove(@Nonnull final PluginContainer container, @Nonnull final Player player, @Nonnull final Living npc) {
			super(Cause.source(container).named("player", player).named("npc", npc).build(), player);

			this.npc = npc;
		}

		@Nonnull public Living getNPC() { return this.npc; }
	}
}