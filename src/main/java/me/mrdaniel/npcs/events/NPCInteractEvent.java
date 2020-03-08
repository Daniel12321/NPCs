package me.mrdaniel.npcs.events;

import me.mrdaniel.npcs.mixin.interfaces.NPCAble;
import me.mrdaniel.npcs.io.INPCData;
import org.spongepowered.api.command.CommandSource;

import javax.annotation.Nullable;

public class NPCInteractEvent extends NPCEvent {

	public NPCInteractEvent(CommandSource source, INPCData data, @Nullable NPCAble npc) {
		super(source, data, npc);
	}
}
