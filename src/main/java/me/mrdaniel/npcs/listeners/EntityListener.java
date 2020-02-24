package me.mrdaniel.npcs.listeners;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.data.npc.NPCData;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.io.INPCData;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.DestructEntityEvent;
import org.spongepowered.api.event.entity.SpawnEntityEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.world.chunk.UnloadChunkEvent;

public class EntityListener {

    @Listener
    public void onEntitySpawn(SpawnEntityEvent.ChunkLoad e) {
        e.getEntities().forEach(ent -> ent.get(NPCData.class).ifPresent(data -> {

            INPCData npcData = NPCs.getInstance().getNPCManager().getData(data.getId()).orElse(null);
            if (npcData != null && npcData.getNPCUUID() == null) {
                npcData.setNPCUUID(ent.getUniqueId());
                ((NPCAble) ent).setNPCData(npcData);
            } else {
                ent.remove();
            }
        }));
    }

    @Listener
    public void onEntityDespawn(UnloadChunkEvent e) {
        e.getTargetChunk().getEntities().forEach(ent -> {
            if (ent instanceof NPCAble) {
                NPCAble npc = (NPCAble) ent;
                INPCData data = npc.getNPCData();
                if (data != null && data.getNPCUUID() != null && data.getNPCUUID().equals(ent.getUniqueId())) {
                    data.setNPCUUID(null);
                }
            }
        });
    }

    @Listener
    public void onEntityDespawn(DestructEntityEvent.Death e, @First NPCAble npc) {
        if (npc.getNPCData() != null) {
            e.setCancelled(true);

            NPCs.getInstance().getLogger().warn("Cancelled an NPC death!");
        }
    }
}
