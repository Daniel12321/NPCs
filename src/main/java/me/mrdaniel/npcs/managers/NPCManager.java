package me.mrdaniel.npcs.managers;

import com.flowpowered.math.vector.Vector3d;
import com.google.common.collect.Maps;
import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.catalogtypes.horsecolor.HorseColors;
import me.mrdaniel.npcs.catalogtypes.horsepattern.HorsePatterns;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCTypes;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyTypes;
import me.mrdaniel.npcs.data.npc.NPCData;
import me.mrdaniel.npcs.events.NPCCreateEvent;
import me.mrdaniel.npcs.events.NPCRemoveEvent;
import me.mrdaniel.npcs.exceptions.NPCException;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.io.Config;
import me.mrdaniel.npcs.io.INPCData;
import me.mrdaniel.npcs.io.INPCStore;
import me.mrdaniel.npcs.io.StorageType;
import me.mrdaniel.npcs.utils.Position;
import net.minecraft.entity.EntityLiving;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class NPCManager {

    private final INPCStore npcStore;
    private final Map<Integer, INPCData> npcs;

    public NPCManager(Config config) {
        this.npcStore = StorageType.of(config.getNode("storage", "storage_type").getString()).orElse(StorageType.HOCON).createNPCStore();
        this.npcs = Maps.newHashMap();
    }

    public void setup() {
        this.npcStore.setup();
    }

    public void load() {
        this.npcs.clear();
        this.npcStore.load(this.npcs);
    }

    public NPCAble spawn(INPCData data) throws NPCException {
        this.getNPC(data).ifPresent(npc -> ((EntityLiving)npc).setDead());

        World world = data.getProperty(PropertyTypes.WORLD).orElseThrow(() -> new NPCException("Invalid world!"));
        Entity ent = world.createEntity(data.getProperty(PropertyTypes.TYPE).orElseThrow(() -> new NPCException("Could not find EntityType for NPC!")).getEntityType(), new Vector3d(0, 0, 0));
        ent.offer(new NPCData(data.getId()));

        NPCAble npc = (NPCAble) ent;
        npc.setNPCData(data);

        world.spawnEntity(ent);
        return npc;
    }

    public NPCAble create(Player p, NPCType type) throws NPCException {
		if (new NPCCreateEvent(p, type).post()) {
			throw new NPCException("Event was cancelled!");
		}

		INPCData data = this.npcStore.create(type);
		this.npcs.put(data.getId(), data);

        if (type == NPCTypes.HUMAN) {
            data.setProperty(PropertyTypes.NAME, "Steve");
        } else if (type == NPCTypes.HORSE) {
		    data.setProperty(PropertyTypes.HORSECOLOR, HorseColors.BROWN).setProperty(PropertyTypes.HORSEPATTERN, HorsePatterns.NONE);
		}

		data.setProperty(PropertyTypes.TYPE, type)
                .setProperty(PropertyTypes.WORLD, p.getWorld())
                .setProperty(PropertyTypes.POSITION, new Position(p.getLocation().getPosition(), p.getHeadRotation()))
                .setProperty(PropertyTypes.INTERACT, true)
                .setProperty(PropertyTypes.LOOKING, false)
                .save();

		NPCAble npc = this.spawn(data);
		NPCs.getInstance().getMenuManager().select(p, npc);

		return npc;
    }

    public void remove(CommandSource src, int id) throws NPCException {
        this.remove(src, this.getData(id).orElseThrow(() -> new NPCException("No NPC with that ID exists!")));
    }

    public void remove(CommandSource src, INPCData data) throws NPCException {
        this.remove(src, data, this.getNPC(data).orElse(null));
    }

    public void remove(CommandSource src, INPCData data, @Nullable NPCAble npc) throws NPCException {
        if (new NPCRemoveEvent(src, data).post()) {
            throw new NPCException("Event was cancelled!");
        }

        NPCs.getInstance().getMenuManager().deselect(npc);
        NPCs.getInstance().getActionManager().removeChoosing(npc);

        this.npcStore.remove(data);
        this.npcs.remove(data.getId());

        if (npc != null) {
            ((Entity)npc).remove();
        } else {
            src.sendMessage(Text.of(TextColors.RED, "Failed to delete NPC Entity!"));
        }
    }

    public Optional<INPCData> getData(int id) {
        return Optional.ofNullable(this.npcs.get(id));
    }

    public Optional<NPCAble> getNPC(INPCData data) {
        World world = data.getProperty(PropertyTypes.WORLD).orElse(null);
        if (world == null) {
            return Optional.empty();
        }

        world.loadChunk(data.getProperty(PropertyTypes.POSITION).get().getChunkPosition(), true);

        // TODO: Optimize
        return world.getEntities().stream()
                .filter(ent -> ent instanceof NPCAble)
                .map(ent -> (NPCAble)ent).filter(npc -> npc.getNPCData() != null && npc.getNPCData().getId() == data.getId())
                .findFirst();
    }

    public List<INPCData> getNPCs(String worldName) {
        return this.npcs.values().stream().filter(data -> worldName.equals(data.getWorldName())).collect(Collectors.toList());
    }
}
