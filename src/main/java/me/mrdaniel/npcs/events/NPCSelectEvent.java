package me.mrdaniel.npcs.events;

import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import org.spongepowered.api.command.CommandSource;

public class NPCSelectEvent extends NPCEvent<NPCAble> {

	public NPCSelectEvent(CommandSource source, NPCAble npc) {
		super(source, npc);
	}
}
