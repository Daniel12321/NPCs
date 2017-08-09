package me.mrdaniel.npcs.utils;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.event.cause.entity.spawn.EntitySpawnCause;
import org.spongepowered.api.event.cause.entity.spawn.SpawnTypes;

public class ServerUtils {

	public static Cause getCause(final Object root, final NamedCause... causes) {
		Cause.Builder b = Cause.source(root);
		for (NamedCause cause : causes) { b.named(cause); }
		return b.build();
	}

	public static Cause getSpawnCause(@Nonnull final Entity e, final NamedCause... causes) {
		return getCause(EntitySpawnCause.builder().entity(e).type(SpawnTypes.PLUGIN).build(), causes);
	}
}