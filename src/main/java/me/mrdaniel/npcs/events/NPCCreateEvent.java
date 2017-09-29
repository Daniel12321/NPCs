package me.mrdaniel.npcs.events;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;

import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;

public class NPCCreateEvent extends NPCEvent<NPCType> {

	public NPCCreateEvent(@Nonnull final CommandSource source, @Nonnull final NPCType type) {
		super(source, type);
	}
}