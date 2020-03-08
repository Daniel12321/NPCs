package me.mrdaniel.npcs.events;

import me.mrdaniel.npcs.mixin.interfaces.NPCAble;
import me.mrdaniel.npcs.io.INPCData;
import me.mrdaniel.npcs.utils.CauseUtils;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.event.Cancellable;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.impl.AbstractEvent;

import javax.annotation.Nullable;

public abstract class NPCEvent extends AbstractEvent implements Cancellable {

	private final CommandSource source;
	private final INPCData data;
	@Nullable private final NPCAble npc;
	private final Cause cause;

	private boolean cancelled;

	protected NPCEvent(CommandSource source, INPCData data, @Nullable NPCAble npc) {
		this.source = source;
		this.data = data;
		this.npc = npc;
		this.cause = CauseUtils.getCause(source, data, npc);

		this.cancelled = false;
	}

	@Override
	public CommandSource getSource() {
		return source;
	}

	public INPCData getData() {
		return data;
	}

	@Nullable
	public NPCAble getNpc() {
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
