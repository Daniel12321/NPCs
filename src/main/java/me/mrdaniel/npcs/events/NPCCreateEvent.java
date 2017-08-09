package me.mrdaniel.npcs.events;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.event.Cancellable;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.impl.AbstractEvent;

import lombok.Getter;
import lombok.Setter;
import me.mrdaniel.npcs.NPCs;

public class NPCCreateEvent extends AbstractEvent implements Cancellable {

	@Getter private final CommandSource source;
	@Getter private final EntityType type;
	@Getter private final Cause cause;

	@Getter @Setter private boolean cancelled;

	public NPCCreateEvent(@Nonnull final CommandSource source, @Nonnull final EntityType type) {
		this.source = source;
		this.type = type;
		this.cause = Cause.source(NPCs.getInstance().getContainer()).named("source", source).named("type", type).build();

		this.cancelled = false;
	}

	public boolean post() {
		return NPCs.getInstance().getGame().getEventManager().post(this);
	}
}