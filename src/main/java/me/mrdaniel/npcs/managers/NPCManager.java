package me.mrdaniel.npcs.managers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Set;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3d;
import com.flowpowered.math.vector.Vector3f;
import com.google.common.collect.Sets;

import lombok.Getter;
import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.catalogtypes.horsecolor.HorseColors;
import me.mrdaniel.npcs.catalogtypes.horsepattern.HorsePatterns;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCTypes;
import me.mrdaniel.npcs.catalogtypes.optiontype.OptionTypeRegistryModule;
import me.mrdaniel.npcs.events.NPCCreateEvent;
import me.mrdaniel.npcs.events.NPCRemoveEvent;
import me.mrdaniel.npcs.exceptions.NPCException;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.io.NPCFile;
import net.minecraft.entity.EntityLiving;

public class NPCManager {

	@Getter private static NPCManager instance = new NPCManager();

	private final Path storagePath;
	private final Set<NPCFile> npcs;

	public NPCManager() {
		this.storagePath = NPCs.getInstance().getConfigDir().resolve("storage");
		this.npcs = Sets.newHashSet();

		if (!Files.exists(this.storagePath)) {
			try { Files.createDirectory(this.storagePath); }
			catch (final IOException exc) { NPCs.getInstance().getLogger().error("Failed to create main NPC storage directory: {}", exc.getMessage(), exc); }
		}

		for (final String name : this.storagePath.toFile().list()) {
			this.npcs.add(new NPCFile(this.storagePath, Integer.valueOf(name.replaceAll("[^\\d]", ""))));
		}
	}

	public Set<NPCFile> getFiles() {
		return this.npcs;
	}

	public Optional<NPCAble> getNPC(@Nonnull final NPCFile file) {
		Optional<World> w = file.getWorld();
		if (!w.isPresent()) { return Optional.empty(); }
		World world = w.get();

		world.loadChunk(file.getChunkPosition(), true);

		return world.getEntities().stream().filter(ent -> ent instanceof NPCAble).map(ent -> (NPCAble)ent).filter(npc -> npc.getNPCFile() != null && npc.getNPCFile().getId() == file.getId()).findAny();
	}

	public Optional<NPCFile> getFile(final int id) {
		for (NPCFile file : this.npcs) {
			if (file.getId() == id) { return Optional.of(file); }
		}
		return Optional.empty();
	}

	private int getNextID() {
		int highest = 1;
		while (Files.exists(this.storagePath.resolve("npc_" + highest + ".conf"))) { ++highest; }
		return highest;
	}

	public void remove(@Nonnull final CommandSource src, final int id) throws NPCException {
		this.remove(src, this.getFile(id).orElseThrow(() -> new NPCException("No NPC with that ID exists!")), true);
	}

	public void remove(@Nonnull final CommandSource src, @Nonnull final NPCFile file, final boolean removeNPC) throws NPCException {
		if (new NPCRemoveEvent(src, file).post()) { throw new NPCException("Event was cancelled!"); }

		try { Files.deleteIfExists(this.storagePath.resolve("npc_" + Integer.toString(file.getId()) + ".conf")); }
		catch (final IOException exc) { NPCs.getInstance().getLogger().error("Failed to delete npc data file for npc {}: {}", file.getId(), exc.getMessage(), exc); }

		this.npcs.remove(file);

		if (removeNPC) {
			Optional<NPCAble> npc = this.getNPC(file);
			if (npc.isPresent()) { this.remove(src, npc.get(), false); }
		}
	}

	public void remove(@Nonnull final CommandSource src, @Nonnull final NPCAble npc, final boolean removeFile) throws NPCException {
		MenuManager.getInstance().deselect(npc);
		ActionManager.getInstance().removeChoosing(npc);
		((Entity)npc).remove();

		if (removeFile) { this.remove(src, npc.getNPCFile(), false); }
	}

	public NPCAble create(@Nonnull final Player p, @Nonnull final NPCType type) throws NPCException {
		if (new NPCCreateEvent(p, type).post()) { throw new NPCException("Event was cancelled!"); }

		NPCFile file = new NPCFile(this.storagePath, this.getNextID());
		this.npcs.add(file);

		Vector3d loc = p.getLocation().getPosition();
		Vector3f rot = p.getHeadRotation().toFloat();

		if (type == NPCTypes.HUMAN) { file.setName("Steve"); }
		if (type == NPCTypes.SNOWMAN) { file.setPumpkin(true); }
		if (type == NPCTypes.BAT) { file.setHanging(false); }
		if (type == NPCTypes.HORSE) { file.setHorseColor(HorseColors.BROWN); file.setHorsePattern(HorsePatterns.NONE); }
		file.setType(type)
		.setWorld(p.getWorld())
		.setX(loc.getX())
		.setY(loc.getY())
		.setZ(loc.getZ())
		.setYaw(rot.getY())
		.setPitch(rot.getX())
		.setInteract(true)
		.setLooking(false)
		.save();

		NPCAble npc = this.spawn(file);
		npc.selectNPC(p);

		return npc;
	}

	public void copy(@Nonnull final Player p, @Nonnull final NPCFile old) throws NPCException {
		NPCAble npc = this.create(p, old.getType().orElseThrow(() -> new NPCException("Could not find EntityType \"" + old.getTypeName() + "\"")));
		NPCFile file = npc.getNPCFile();

		old.getSkinUUID().ifPresent(value -> file.setSkinUUID(value));
		OptionTypeRegistryModule.getInstance().getMain().forEach(option -> option.writeToFileAndNPC(npc, old));
		OptionTypeRegistryModule.getInstance().getArmor().forEach(option -> option.writeToFileAndNPC(npc, old));

		for (int i = 0; i < old.getActions().size(); i++) { file.getActions().add(i, old.getActions().get(i)); }
		old.writeActions();

		old.getCurrent().forEach((uuid, current) -> file.getCurrent().put(uuid, current));
		old.writeCurrent();

		old.getCooldowns().forEach((uuid, end) -> file.getCooldowns().put(uuid, end));
		old.writeCooldowns();

		file.save();
	}

	public NPCAble spawn(@Nonnull final NPCFile file) throws NPCException {
		this.getNPC(file).ifPresent(npc -> ((EntityLiving)npc).setDead());

		World world = file.getWorld().orElseThrow(() -> new NPCException("Invalid world!"));
		Entity ent = world.createEntity(file.getType().orElseThrow(() -> new NPCException("Could not find EntityType \"" + file.getTypeName() + "\"")).getEntityType(), new Vector3d(0, 0, 0));
		NPCAble npc = (NPCAble) ent;

		npc.setNPCFile(file);
		world.spawnEntity(ent);
		return npc;
	}
}