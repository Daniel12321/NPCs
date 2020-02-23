package me.mrdaniel.npcs.events;

import me.mrdaniel.npcs.io.INPCData;
import org.spongepowered.api.command.CommandSource;

public class NPCRemoveEvent extends NPCEvent<INPCData> {

	public NPCRemoveEvent(CommandSource source, INPCData file) {
		super(source, file);
	}
}
