package me.mrdaniel.npcs.events;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.utils.ServerUtils;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.event.Cancellable;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.impl.AbstractEvent;

import javax.annotation.Nonnull;

public abstract class NPCEvent<T> extends AbstractEvent implements Cancellable {

	private final CommandSource source;
	private final T npc;
	private final Cause cause;

	private boolean cancelled;

	protected NPCEvent(CommandSource source, T npc) {
		this.source = source;
		this.npc = npc;
		this.cause = ServerUtils.getCause(npc, source);

		this.cancelled = false;
	}

	public boolean post() {
		return NPCs.getInstance().getGame().getEventManager().post(this);
	}

	@Override
	public CommandSource getSource() {
		return source;
	}

	public T getNpc() {
		return npc;
	}

	@Override
	public Cause getCause() {
		return cause;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
}
