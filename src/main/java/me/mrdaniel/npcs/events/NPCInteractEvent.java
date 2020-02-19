package me.mrdaniel.npcs.events;

import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import org.spongepowered.api.command.CommandSource;

public class NPCInteractEvent extends NPCEvent<NPCAble> {

	public NPCInteractEvent(CommandSource source, NPCAble npc) {
		super(source, npc);
	}
}
