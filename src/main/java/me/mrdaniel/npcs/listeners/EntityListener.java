package me.mrdaniel.npcs.listeners;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.data.npc.NPCData;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.io.INPCData;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.DestructEntityEvent;
import org.spongepowered.api.event.entity.SpawnEntityEvent;
import org.spongepowered.api.event.filter.cause.First;

import java.util.Optional;

public class EntityListener {

    @Listener
    public void onEntitySpawn(SpawnEntityEvent.ChunkLoad e) {
        e.getEntities().forEach(ent -> ent.get(NPCData.class).ifPresent(data -> {

            Optional<INPCData> npcData = NPCs.getInstance().getNPCManager().getData(data.getId());
            if (npcData.isPresent()) {
                ((NPCAble) ent).setNPCData(npcData.get());
            } else {
                ent.remove();
            }
        }));
    }

    @Listener
    public void onEntityDespawn(DestructEntityEvent.Death e, @First NPCAble npc) {
        if (npc.getNPCData() != null) {
            e.setCancelled(true);

            NPCs.getInstance().getLogger().warn("Cancelled a death for an NPC!");
        }
    }
}
