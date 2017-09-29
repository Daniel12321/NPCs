package me.mrdaniel.npcs.events;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;

import me.mrdaniel.npcs.io.NPCFile;

public class NPCRemoveEvent extends NPCEvent<NPCFile> {

	public NPCRemoveEvent(@Nonnull final CommandSource source, @Nonnull final NPCFile file) {
		super(source, file);
	}
}