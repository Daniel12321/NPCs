package me.mrdaniel.npcs.events;

import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import org.spongepowered.api.command.CommandSource;

public class NPCEditEvent extends NPCEvent<NPCAble> {

	public NPCEditEvent(CommandSource source, NPCAble npc) {
		super(source, npc);
	}
}
