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
        this.npcStore = StorageType.of(config.getNode("storage", "storage_type").getString()).orElse(StorageType.HOCON).createNPCStore(this);
        this.npcs = Maps.newHashMap();
    }

    public void setup() {
        this.npcStore.setup();
    }

    public void load() {
        this.npcs.clear();
        this.npcStore.load(this.npcs);
    }

    public NPCAble create(Player p, NPCType type) throws NPCException {
		if (new NPCCreateEvent(p, type).post()) {
			throw new NPCException("Event was cancelled!");
		}

		INPCData data = this.npcStore.create(type);
		this.npcs.put(data.getNPCId(), data);

        if (type == NPCTypes.HUMAN) {
            data.setNPCProperty(PropertyTypes.NAME, "Steve");
        } else if (type == NPCTypes.HORSE) {
		    data.setNPCProperty(PropertyTypes.HORSECOLOR, HorseColors.BROWN).setNPCProperty(PropertyTypes.HORSEPATTERN, HorsePatterns.NONE);
		}

        data.setNPCPosition(new Position(p.getWorld().getName(), p.getLocation().getPosition(), p.getHeadRotation()));
		data.setNPCProperty(PropertyTypes.TYPE, type)
                .setNPCProperty(PropertyTypes.INTERACT, true)
                .setNPCProperty(PropertyTypes.LOOKING, false)
                .saveNPC();

		NPCAble npc = this.spawn(data);
		NPCs.getInstance().getMenuManager().select(p, npc);

		return npc;
    }

    public NPCAble spawn(INPCData data) throws NPCException {
        this.getNPC(data).ifPresent(npc -> ((EntityLiving)npc).setDead());

        Position pos = data.getNPCPosition();
        World world = pos.getWorld().orElseThrow(() -> new NPCException("Invalid world!"));
        Entity ent = world.createEntity(data.getNPCProperty(PropertyTypes.TYPE).orElseThrow(() -> new NPCException("Could not find EntityType for NPC!")).getEntityType(), new Vector3d(0, 0, 0));
        ent.offer(new NPCData(data.getNPCId()));

        NPCAble npc = (NPCAble) ent;
        npc.setNPCData(data);

        ((net.minecraft.entity.Entity) ent).setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(), pos.getYaw(), pos.getPitch());

        world.spawnEntity(ent);
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

        NPCs.getInstance().getMenuManager().deselect(npc.getNPCData());
        NPCs.getInstance().getActionManager().removeChoosing(npc);

        this.npcStore.remove(data);
        this.npcs.remove(data.getNPCId());

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
        World world = data.getNPCPosition().getWorld().orElse(null);
        if (world == null) {
            return Optional.empty();
        }

        world.loadChunk(data.getNPCPosition().getChunkPosition(), true);

        // TODO: Optimize
        return world.getEntities().stream()
                .filter(ent -> ent instanceof NPCAble)
                .map(ent -> (NPCAble)ent).filter(npc -> npc.getNPCData() != null && npc.getNPCData().getNPCId() == data.getNPCId())
                .findFirst();
    }

    public List<INPCData> getNPCs(String worldName) {
        return this.npcs.values().stream().filter(data -> worldName.equals(data.getNPCPosition().getWorldName())).collect(Collectors.toList());
    }
}
