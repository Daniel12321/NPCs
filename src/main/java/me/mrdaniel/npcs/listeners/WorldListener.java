package me.mrdaniel.npcs.listeners;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.data.npc.NPCData;
import me.mrdaniel.npcs.exceptions.NPCException;
import me.mrdaniel.npcs.io.INPCData;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.world.LoadWorldEvent;
import org.spongepowered.api.world.World;

import java.util.ArrayList;
import java.util.List;

public class WorldListener {

	@Listener
	public void onWorldLoad(LoadWorldEvent e) {
		World world = e.getTargetWorld();
		String worldName = world.getName();

		List<INPCData> worldData = new ArrayList<>(NPCs.getInstance().getNPCManager().getNPCs(worldName));
		worldData.forEach(data -> world.loadChunk(data.getNPCPosition().getChunkPosition(), true));

		world.getEntities().stream()
				.filter(ent -> ent.get(NPCData.class).isPresent())
				.forEach(Entity::remove);

		worldData.forEach(data -> {
			try {
				NPCs.getInstance().getNPCManager().spawn(data);
			} catch (final NPCException exc) {
				NPCs.getInstance().getLogger().error("Failed to spawn NPC: ", exc);
			}
		});
	}
}
