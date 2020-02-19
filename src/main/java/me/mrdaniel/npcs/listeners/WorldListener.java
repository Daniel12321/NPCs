package me.mrdaniel.npcs.listeners;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.data.npc.NPCData;
import me.mrdaniel.npcs.exceptions.NPCException;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.io.NPCFile;
import me.mrdaniel.npcs.managers.NPCManager;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.world.LoadWorldEvent;
import org.spongepowered.api.world.World;

import java.util.List;
import java.util.stream.Collectors;

public class WorldListener {

	@Listener
	public void onWorldLoad(LoadWorldEvent e) {
		World world = e.getTargetWorld();
		String worldName = world.getName();

		List<NPCFile> files = NPCManager.getInstance().getFiles().stream().filter(file -> worldName.equals(file.getWorldName())).collect(Collectors.toList());
		files.forEach(file -> world.loadChunk(file.getChunkPosition(), true));

		world.getEntities().stream()
				.filter(ent -> ent.get(NPCData.class).isPresent() || (ent instanceof NPCAble && ((NPCAble)ent).getNPCFile() != null))
				.forEach(npc -> npc.remove());

		files.forEach(file -> {
			try {
				NPCManager.getInstance().spawn(file);
			} catch (final NPCException exc) {
				NPCs.getInstance().getLogger().error("Failed to spawn NPC: " + exc.getMessage(), exc);
			}
		});
	}
}
