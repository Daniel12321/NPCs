package me.mrdaniel.npcs.utils;

import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.EventContext;

public class ServerUtils {

	public static Cause getCause(final Object... causes) {
		Cause.Builder b = Cause.builder();
		for (Object cause : causes) { b.append(cause); }
		return b.build(EventContext.builder().build());
	}
}