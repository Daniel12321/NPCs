package me.mrdaniel.npcs.events;

import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.io.INPCData;
import org.spongepowered.api.command.CommandSource;

import javax.annotation.Nullable;

public class NPCRemoveEvent extends NPCEvent {

	public NPCRemoveEvent(CommandSource source, INPCData file, @Nullable NPCAble npc) {
		super(source, file, npc);
	}
}
