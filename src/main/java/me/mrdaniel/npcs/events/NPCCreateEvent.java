package me.mrdaniel.npcs.events;

import me.mrdaniel.npcs.io.INPCData;
import org.spongepowered.api.command.CommandSource;

public class NPCCreateEvent extends NPCEvent {

	public NPCCreateEvent(CommandSource source, INPCData data) {
		super(source, data, null);
	}
}
