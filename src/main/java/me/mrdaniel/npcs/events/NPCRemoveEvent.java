package me.mrdaniel.npcs.events;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.event.Cancellable;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.impl.AbstractEvent;

import lombok.Getter;
import lombok.Setter;
import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.io.NPCFile;

public class NPCRemoveEvent extends AbstractEvent implements Cancellable {

	@Getter private final CommandSource source;
	@Getter private final NPCFile file;
	@Getter private final Cause cause;

	@Getter @Setter private boolean cancelled;

	public NPCRemoveEvent(@Nonnull final CommandSource source, @Nonnull final NPCFile file) {
		this.source = source;
		this.file = file;
		this.cause = Cause.source(NPCs.getInstance().getContainer()).named("source", source).named("file", file).build();

		this.cancelled = false;
	}

	public boolean post() {
		return NPCs.getInstance().getGame().getEventManager().post(this);
	}
}