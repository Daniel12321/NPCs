package me.mrdaniel.npcs.listeners;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.data.npc.NPCData;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.DestructEntityEvent;
import org.spongepowered.api.event.entity.SpawnEntityEvent;
import org.spongepowered.api.event.filter.cause.First;

public class EntityListener {

    @Listener
    public void onEntitySpawn(SpawnEntityEvent.ChunkLoad e) {
        e.getEntities().forEach(ent -> ent.get(NPCData.class).ifPresent(data -> {
            NPCs.getInstance().getNPCManager().onNPCSpawn(ent, data.getId());
        }));
    }

    @Listener
    public void onEntityDespawn(DestructEntityEvent.Death e, @First NPCAble npc) {
        if (npc.getData() != null) {
            e.setCancelled(true);

            NPCs.getInstance().getLogger().warn("Cancelled an NPC death!");
        }
    }
}
