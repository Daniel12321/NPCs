package me.mrdaniel.npcs.utils;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.entity.spawn.EntitySpawnCause;
import org.spongepowered.api.event.cause.entity.spawn.SpawnTypes;

public class ServerUtils {

	@Nonnull
	public static Cause getSpawnCause(@Nonnull final Entity e) {
		return Cause.source(EntitySpawnCause.builder().entity(e).type(SpawnTypes.PLUGIN).build()).build();
	}
}