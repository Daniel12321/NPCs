package me.mrdaniel.npcs.io.hocon;

import com.flowpowered.math.vector.Vector3d;
import com.flowpowered.math.vector.Vector3f;
import com.google.common.collect.Sets;
import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.catalogtypes.horsecolor.HorseColors;
import me.mrdaniel.npcs.catalogtypes.horsepattern.HorsePatterns;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCTypes;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyTypes;
import me.mrdaniel.npcs.events.NPCCreateEvent;
import me.mrdaniel.npcs.events.NPCRemoveEvent;
import me.mrdaniel.npcs.exceptions.NPCException;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.io.INPCData;
import me.mrdaniel.npcs.io.INPCStore;
import me.mrdaniel.npcs.utils.Position;
import net.minecraft.entity.EntityLiving;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.World;

import javax.annotation.Nullable;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Set;

public class HoconNPCStore implements INPCStore {

    private final Path storageDir;
	private final Set<INPCData> npcs;

    public HoconNPCStore() {
        this.storageDir = NPCs.getInstance().getConfigDir().resolve("storage");
        this.npcs = Sets.newHashSet();

        this.load();
    }

    @Override
    public void load() {
		if (!Files.exists(this.storageDir)) {
			try {
				Files.createDirectory(this.storageDir);
			} catch (final IOException exc) {
				NPCs.getInstance().getLogger().error("Failed to create main NPC storage directory: ",  exc);
			}
		}

		for (String name : this.storageDir.toFile().list()) {
		    this.npcs.add(new HoconNPCData(this.storageDir.resolve(name)));
		}
    }

    @Override
    public NPCAble spawn(INPCData data) throws NPCException {
		this.getNPC(data).ifPresent(npc -> ((EntityLiving)npc).setDead());

		World world = Sponge.getServer().getWorld(data.getProperty(PropertyTypes.WORLD_NAME).get()).orElseThrow(() -> new NPCException("Invalid world!"));
		Entity ent = world.createEntity(data.getProperty(PropertyTypes.TYPE).orElseThrow(() -> new NPCException("Could not find EntityType for NPC!")).getEntityType(), new Vector3d(0, 0, 0));
		NPCAble npc = (NPCAble) ent;

		npc.setNPCData(data);
		world.spawnEntity(ent);
		return npc;
    }

    @Override
    public NPCAble create(Player p, NPCType type) throws NPCException {
		if (new NPCCreateEvent(p, type).post()) {
			throw new NPCException("Event was cancelled!");
		}

		INPCData file = new HoconNPCData(this.storageDir.resolve("npc_" + this.getNextID() + ".conf"));
		this.npcs.add(file);

		Vector3d loc = p.getLocation().getPosition();
		Vector3f head = p.getHeadRotation().toFloat();

		if (type == NPCTypes.HUMAN) { file.setProperty(PropertyTypes.NAME, "Steve"); }

		// TODO: Find out if this is really needed
//		if (type == NPCTypes.SNOWMAN) { file.setPumpkin(true); }
//		if (type == NPCTypes.BAT) { file.setHanging(false); }
		if (type == NPCTypes.HORSE) {
		    file.setProperty(PropertyTypes.HORSECOLOR, HorseColors.BROWN)
                    .setProperty(PropertyTypes.HORSEPATTERN, HorsePatterns.NONE);
		}

		file.setProperty(PropertyTypes.TYPE, type)
                .setProperty(PropertyTypes.WORLD, p.getWorld())
                .setProperty(PropertyTypes.POSITION, new Position(loc, head))
                .setProperty(PropertyTypes.INTERACT, true)
                .setProperty(PropertyTypes.LOOKING, false)
                .save();

		NPCAble npc = this.spawn(file);
		NPCs.getInstance().getMenuManager().select(p, npc);

		return npc;
    }

    // TODO: Change so it doesnt use IDs in directory
	private int getNextID() {
		int highest = 1;
		while (Files.exists(this.storageDir.resolve("npc_" + highest + ".conf"))) {
			++highest;
		}
		return highest;
	}

    @Override
    public void remove(CommandSource src, int id) throws NPCException {
        this.remove(src, this.getData(id).orElseThrow(() -> new NPCException("No NPC with that ID exists!")));
    }

    @Override
    public void remove(CommandSource src, INPCData data) throws NPCException {
		if (new NPCRemoveEvent(src, data).post()) {
			throw new NPCException("Event was cancelled!");
		}

		Optional<NPCAble> npc = this.getNPC(data);
		if (npc.isPresent()) {
		    this.remove(src, npc.get());
		}
    }

    @Override
    public void remove(CommandSource src, INPCData data, @Nullable NPCAble npc) throws NPCException {
        NPCs.getInstance().getMenuManager().deselect(npc);
        NPCs.getInstance().getActionManager().removeChoosing(npc);

        try {
            //TODO: Fix so it doesnt use directory
            Files.deleteIfExists(this.storageDir.resolve("npc_" + Integer.toString(data.getId()) + ".conf"));
        } catch (final IOException exc) {
            NPCs.getInstance().getLogger().error("Failed to delete npc data for npc {}: {}", data.getId(), exc.getMessage(), exc);
        }

        if (npc != null) {
            this.npcs.remove(data);
            ((Entity)npc).remove();
        }
    }

    @Override
    public Optional<INPCData> getData(int id) {
        for (INPCData data : this.npcs) {
            if (data.getId() == id) {
                return Optional.of(data);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<NPCAble> getNPC(INPCData data) {
        World world = Sponge.getServer().getWorld(data.getProperty(PropertyTypes.WORLD_NAME).get()).orElse(null);
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

    @Override
    public Set<INPCData> getAllNPCs() {
        return this.npcs;
    }
}
