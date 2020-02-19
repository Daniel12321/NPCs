package me.mrdaniel.npcs.events;

import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import org.spongepowered.api.command.CommandSource;

public class NPCCreateEvent extends NPCEvent<NPCType> {

	public NPCCreateEvent(CommandSource source, NPCType type) {
		super(source, type);
	}
}
