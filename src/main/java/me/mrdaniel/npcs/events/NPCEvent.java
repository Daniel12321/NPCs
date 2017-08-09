package me.mrdaniel.npcs.events;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.event.Cancellable;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.impl.AbstractEvent;

import lombok.Getter;
import lombok.Setter;
import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;

public class NPCEvent extends AbstractEvent implements Cancellable {

	@Getter private final CommandSource source;
	@Getter private final NPCAble npc;
	@Getter private final Cause cause;

	@Getter @Setter private boolean cancelled;

	protected NPCEvent(@Nonnull final CommandSource source, @Nonnull final NPCAble npc) {
		this.source = source;
		this.npc = npc;
		this.cause = Cause.source(NPCs.getInstance().getContainer()).named("source", source).named("npc", npc).build();

		this.cancelled = false;
	}

	public boolean post() {
		return NPCs.getInstance().getGame().getEventManager().post(this);
	}

	public static class Interact extends NPCEvent {
		public Interact(@Nonnull final CommandSource source, @Nonnull final NPCAble npc) {
			super(source, npc);
		}
	}

	public static class Edit extends NPCEvent {
		public Edit(@Nonnull final CommandSource source, @Nonnull final NPCAble npc) {
			super(source, npc);
		}
	}

	public static class Select extends NPCEvent {
		public Select(@Nonnull final CommandSource source, @Nonnull final NPCAble npc) {
			super(source, npc);
		}
	}

	public static class Remove extends NPCEvent {
		public Remove(@Nonnull final CommandSource source, @Nonnull final NPCAble npc) {
			super(source, npc);
		}
	}
}