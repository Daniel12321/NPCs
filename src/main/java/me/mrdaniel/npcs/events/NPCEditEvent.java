package me.mrdaniel.npcs.events;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;

import me.mrdaniel.npcs.interfaces.mixin.NPCAble;

public class NPCEditEvent extends NPCEvent<NPCAble> {

	public NPCEditEvent(@Nonnull final CommandSource source, @Nonnull final NPCAble npc) {
		super(source, npc);
	}
}