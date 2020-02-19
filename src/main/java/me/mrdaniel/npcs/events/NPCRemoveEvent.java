package me.mrdaniel.npcs.events;

import me.mrdaniel.npcs.io.NPCFile;
import org.spongepowered.api.command.CommandSource;

public class NPCRemoveEvent extends NPCEvent<NPCFile> {

	public NPCRemoveEvent(CommandSource source, NPCFile file) {
		super(source, file);
	}
}
