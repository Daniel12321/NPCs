package me.mrdaniel.npcs.events;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;

import me.mrdaniel.npcs.interfaces.mixin.NPCAble;

public class NPCSelectEvent extends NPCEvent<NPCAble> {

	public NPCSelectEvent(@Nonnull final CommandSource source, @Nonnull final NPCAble npc) {
		super(source, npc);
	}
}