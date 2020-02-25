package me.mrdaniel.npcs.utils;

import me.mrdaniel.npcs.NPCs;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.EventContext;
import org.spongepowered.api.event.cause.EventContextKeys;

public class CauseUtils {

	public static Cause getCause(final Object... causes) {
		EventContext context = EventContext.builder().add(EventContextKeys.PLUGIN, NPCs.getInstance().getContainer()).build();

		Cause.Builder b = Cause.builder();
		for (Object cause : causes) {
			if (cause != null) {
				b.append(cause);
			}
		}
		return b.build(context);
	}

//	public static Cause getSpawnCause(@Nonnull final Entity e, final NamedCause... causes) {
//		return getCause(EntitySpawnCause.builder().entity(e).type(SpawnTypes.PLUGIN).build(), causes);
//	}
}
