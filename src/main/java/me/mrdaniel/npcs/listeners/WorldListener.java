package me.mrdaniel.npcs.listeners;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyTypes;
import me.mrdaniel.npcs.data.npc.NPCData;
import me.mrdaniel.npcs.exceptions.NPCException;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
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

		List<INPCData> files = new ArrayList<>(NPCs.getInstance().getNPCManager().getNPCs(worldName));
		files.forEach(file -> world.loadChunk(file.getProperty(PropertyTypes.POSITION).get().getChunkPosition(), true));

		// TODO: Replace to use NPCData only
		world.getEntities().stream()
				.filter(ent -> ent.get(NPCData.class).isPresent() || (ent instanceof NPCAble && ((NPCAble)ent).getNPCData() != null))
				.forEach(Entity::remove);

		files.forEach(file -> {
			try {
				NPCs.getInstance().getNPCManager().spawn(file);
			} catch (final NPCException exc) {
				NPCs.getInstance().getLogger().error("Failed to spawn NPC: " + exc.getMessage(), exc);
			}
		});
	}
}
