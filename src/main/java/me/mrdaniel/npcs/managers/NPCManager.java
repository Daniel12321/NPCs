package me.mrdaniel.npcs.managers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3d;
import com.flowpowered.math.vector.Vector3f;
import com.google.common.collect.Sets;

import lombok.Getter;
import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.catalogtypes.options.OptionTypeRegistryModule;
import me.mrdaniel.npcs.events.NPCCreateEvent;
import me.mrdaniel.npcs.events.NPCRemoveEvent;
import me.mrdaniel.npcs.exceptions.NPCException;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.io.NPCFile;
import me.mrdaniel.npcs.utils.Position;
import me.mrdaniel.npcs.utils.ServerUtils;
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
		Optional<org.spongepowered.api.world.World> w = file.getWorld();
		if (!w.isPresent()) { return Optional.empty(); }

		List<NPCAble> l = w.get().getEntities().stream().filter(ent -> ent instanceof NPCAble).map(ent -> (NPCAble)ent).filter(npc -> npc.getNPCFile() != null).filter(npc -> npc.getNPCFile().getId() == file.getId()).collect(Collectors.toList());

		return l.isEmpty() ? Optional.empty() : Optional.of(l.get(0));
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
		this.remove(src, this.getFile(id).orElseThrow(() -> new NPCException("No NPC with that ID exists!")));
	}

	public void remove(@Nonnull final CommandSource src, @Nonnull final NPCFile file) throws NPCException {
		if (new NPCRemoveEvent(src, file).post()) { throw new NPCException("Event was cancelled!"); }

		try { Files.deleteIfExists(this.storagePath.resolve("npc_" + Integer.toString(file.getId()) + ".conf")); }
		catch (final IOException exc) { NPCs.getInstance().getLogger().error("Failed to delete npc data file for npc {}: {}", file.getId(), exc.getMessage(), exc); }

		this.npcs.remove(file);
		this.getNPC(file).ifPresent(npc -> {
			MenuManager.getInstance().deselect(npc);
			ActionManager.getInstance().removeChoosing(npc);
			((EntityLiving)npc).setDead();
		});
	}

	public void create(@Nonnull final Player p, @Nonnull final EntityType type) throws NPCException {
		if (new NPCCreateEvent(p, type).post()) { throw new NPCException("Event was cancelled!"); }

		NPCFile file = new NPCFile(this.storagePath, this.getNextID());
		Vector3d loc = p.getLocation().getPosition();
		Vector3f rot = p.getHeadRotation().toFloat();

		if (type == EntityTypes.HUMAN) { file.setName("Steve"); }
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

		this.spawn(file, p);
	}

	public void copy(@Nonnull final Player p, @Nonnull final NPCFile old) throws NPCException {
		EntityType type = old.getType().orElseThrow(() -> new NPCException("Could not find EntityType \"" + old.getTypeName() + "\""));
		if (new NPCCreateEvent(p, type).post()) {
			throw new NPCException("Event was cancelled!");
		}

		NPCFile copy = new NPCFile(this.storagePath, this.getNextID());
		copy.setType(type)
		.setWorld(p.getWorld())
		.setX(old.getX())
		.setY(old.getY())
		.setZ(old.getZ())
		.setYaw(old.getYaw())
		.setPitch(old.getPitch())
		.setInteract(true)
		.setLooking(false)
		.save();
		old.getSkinUUID().ifPresent(v -> copy.setSkinUUID(v));

		OptionTypeRegistryModule.getInstance().getMain().forEach(option -> option.writeToFileFromFile(old, copy));
		OptionTypeRegistryModule.getInstance().getArmor().forEach(option -> option.writeToFileFromFile(old, copy));

		Vector3d loc = p.getLocation().getPosition();
		Vector3f rot = p.getHeadRotation().toFloat();
		copy.setPosition(new Position(loc.getX(), loc.getY(), loc.getZ(), rot.getY(), rot.getX()));

		for (int i = 0; i < old.getActions().size(); i++) { copy.getActions().add(i, old.getActions().get(i)); }
		old.getCurrent().forEach((uuid, current) -> copy.getCurrent().put(uuid, current));
		old.getCooldowns().forEach((uuid, end) -> copy.getCooldowns().put(uuid, end));
		copy.save();

		this.spawn(copy, p);
	}

	public void spawn(@Nonnull final NPCFile file, @Nonnull final Player p) throws NPCException {
		NPCAble npc = this.respawn(file);

		npc.selectNPC(p);
		this.npcs.add(file);
	}

	public NPCAble respawn(@Nonnull final NPCFile file) throws NPCException {
		this.getNPC(file).ifPresent(npc -> ((EntityLiving)npc).setDead());

		World world = file.getWorld().orElseThrow(() -> new NPCException("Invalid world!"));
		Entity ent = world.createEntity(file.getType().orElseThrow(() -> new NPCException("Could not find EntityType \"" + file.getTypeName() + "\"")), new Vector3d(0, 0, 0));
		NPCAble npc = (NPCAble) ent;

		npc.setNPCFile(file);
		world.spawnEntity(ent, ServerUtils.getSpawnCause(ent));
		return npc;
	}
}