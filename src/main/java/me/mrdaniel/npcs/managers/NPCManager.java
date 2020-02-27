package me.mrdaniel.npcs.managers;

import com.flowpowered.math.vector.Vector3d;
import com.google.common.collect.Maps;
import com.google.inject.Inject;
import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCTypes;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyTypes;
import me.mrdaniel.npcs.data.npc.NPCData;
import me.mrdaniel.npcs.events.NPCCreateEvent;
import me.mrdaniel.npcs.events.NPCRemoveEvent;
import me.mrdaniel.npcs.exceptions.NPCException;
import me.mrdaniel.npcs.io.Config;
import me.mrdaniel.npcs.io.INPCData;
import me.mrdaniel.npcs.io.INPCStore;
import me.mrdaniel.npcs.io.StorageType;
import me.mrdaniel.npcs.mixin.interfaces.NPCAble;
import me.mrdaniel.npcs.utils.Position;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.World;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class NPCManager {

    private final Map<Integer, INPCData> data;
    private INPCStore npcStore;

    @Inject
    public NPCManager() {
        this.data = Maps.newHashMap();
        this.npcStore = null;
    }

    public void load(Config config, Path configDir) {
        this.npcStore = StorageType.of(config.getNode("storage", "storage_type").getString()).orElse(StorageType.HOCON).createNPCStore(this, configDir);
        this.npcStore.setup();
        this.data.clear();
        this.npcStore.load(this.data);
    }

    public NPCAble create(Player p, NPCType type) throws NPCException {
		INPCData data = this.npcStore.create(type);

        if (Sponge.getEventManager().post(new NPCCreateEvent(p, data))) {
            this.npcStore.remove(data);
            throw new NPCException("Event was cancelled!");
        }

		this.data.put(data.getId(), data);

        if (type == NPCTypes.HUMAN) {
            data.setProperty(PropertyTypes.NAME, "Steve");
        }
        data.setPosition(new Position(p.getWorld().getName(), p.getLocation().getPosition(), p.getHeadRotation()));
		data.setProperty(PropertyTypes.TYPE, type)
                .setProperty(PropertyTypes.INTERACT, true)
                .setProperty(PropertyTypes.LOOKING, false)
                .save();

		NPCAble npc = this.spawn(data);
		NPCs.getInstance().getSelectedManager().select(p, data);

		return npc;
    }

    public NPCAble spawn(INPCData data) throws NPCException {
        this.getNPC(data).ifPresent(npc -> ((Entity)npc).remove());

        Position pos = data.getPosition();
        World world = pos.getWorld().orElseThrow(() -> new NPCException("Invalid world!"));
        Entity ent = world.createEntity(data.getProperty(PropertyTypes.TYPE).orElseThrow(() -> new NPCException("Could not find EntityType for NPC!")).getEntityType(), new Vector3d(0, 0, 0));
        ent.offer(new NPCData(data.getId()));

        NPCAble npc = (NPCAble) ent;
        npc.setData(data);
        data.setUniqueId(ent.getUniqueId());

        world.spawnEntity(ent);
        return npc;
    }

    public void remove(CommandSource src, INPCData data) throws NPCException {
        NPCAble npc = this.getNPC(data).orElse(null);

        if (Sponge.getEventManager().post(new NPCRemoveEvent(src, data, npc))) {
            throw new NPCException("Event was cancelled!");
        }

        NPCs.getInstance().getSelectedManager().deselect(data);
        NPCs.getInstance().getActionManager().removeChoosing(data);

        this.npcStore.remove(data);
        this.data.remove(data.getId());

        if (npc != null) {
            ((Entity)npc).remove();
        } else {
            data.getPosition().getWorld().ifPresent(world -> world.loadChunk(data.getPosition().getChunkPosition(), true));
        }
    }

    public Optional<INPCData> getData(int id) {
        return Optional.ofNullable(this.data.get(id));
    }

    public List<INPCData> getData(String worldName) {
        return this.data.values().stream().filter(data -> worldName.equals(data.getPosition().getWorldName())).collect(Collectors.toList());
    }

    public Optional<NPCAble> getNPC(INPCData data) {
        UUID uuid = data.getUniqueId();
        World world = data.getPosition().getWorld().orElse(null);

        if (uuid == null || world == null) {
            return Optional.empty();
        }

        return world.getEntity(uuid).map(ent -> ((NPCAble)ent));
    }

    public void onNPCSpawn(Entity ent, int id) {
        INPCData data = NPCs.getInstance().getNPCManager().getData(id).orElse(null);

        if (data == null) {
            ent.remove();
        } else if (data.getUniqueId() == null) {
            data.setUniqueId(ent.getUniqueId());
            ((NPCAble)ent).setData(data);
        } else if (data.getUniqueId().equals(ent.getUniqueId())) {
            ((NPCAble)ent).setData(data);
        } else {
            ent.remove();
        }
    }

    public int getNextID() {
        int id = 1;

        while (this.data.containsKey(id)) {
            id++;
        }
        return id;
    }
}
