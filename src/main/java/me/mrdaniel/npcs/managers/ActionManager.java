package me.mrdaniel.npcs.managers;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scheduler.Task;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.Getter;
import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.exceptions.NPCException;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.io.NPCFile;

public class ActionManager {

	@Getter private static ActionManager instance = new ActionManager();

	private final Map<UUID, NPCFile> choosing;
	private final List<UUID> waiting;

	public ActionManager() {
		this.choosing = Maps.newHashMap();
		this.waiting = Lists.newArrayList();
	}

	public void executeChoice(@Nonnull final NPCFile file, @Nonnull final UUID uuid, final int next) throws NPCException {
		NPCFile f = Optional.ofNullable(this.choosing.remove(uuid)).orElseThrow(() -> new NPCException("You are not choosing!"));
		if (f != file) { throw new NPCException("You can't execute old choices!"); }

		file.getCurrent().put(uuid, next);
		file.writeCurrent().save();
		this.execute(uuid, file);
	}

//	public void execute(@Nonnull final UUID uuid, @Nonnull final Living npc) throws NPCException {
//		this.execute(uuid, npc.get(NPCData.class).orElseThrow(() -> new NPCException("This Entity is not an NPC!")));
//	}

//	public void execute(@Nonnull final UUID uuid, @Nonnull final NPCData data) throws NPCException {
//		this.execute(uuid, NPCManager.getInstance().getFile(data.getId()).orElseThrow(() -> new NPCException("Could not find file for NPC!")));
//	}

	public void execute(@Nonnull final UUID uuid, @Nonnull final NPCFile file) throws NPCException {
		if (this.waiting.contains(uuid)) { return; }
		if (file.getActions().size() == 0) { return; }

		Player p = NPCs.getInstance().getGame().getServer().getPlayer(uuid).orElseThrow(() -> new NPCException("Player not found!"));
		int next = Optional.ofNullable(file.getCurrent().get(uuid)).orElse(0);

		if (next >= file.getActions().size()) {
			if (file.getRepeatActions()) {
				file.getCurrent().put(uuid, 0);
				file.writeCurrent().save();
			}
			return;
		}

		ActionResult result = new ActionResult(next);

		file.getActions().get(next).execute(p, file, result);

		if (result.getWaitTicks() > 0) {
			this.waiting.add(uuid);
			Task.builder().delayTicks(result.getWaitTicks()).execute(() -> {
				this.waiting.remove(uuid);
				file.getCurrent().put(uuid, result.getNextAction());
				file.writeCurrent().save();
				if (result.getPerformNextAction()) {
					try { this.execute(uuid, file); }
					catch (final NPCException exc) {}
				}
			}).submit(NPCs.getInstance());
		}
		else {
			file.getCurrent().put(uuid, result.getNextAction());
			file.writeCurrent().save();
			if (result.getPerformNextAction()) { this.execute(uuid, file); }
		}
	}

	public void setChoosing(@Nonnull final UUID uuid, @Nonnull final NPCFile file) {
		this.choosing.put(uuid, file);
	}

	public void removeChoosing(@Nonnull final UUID uuid) {
		this.choosing.remove(uuid);
	}

	public void removeChoosing(@Nonnull final NPCAble npc) {
		this.choosing.entrySet().stream().filter(e -> e.getValue() == npc.getNPCFile()).map(e -> e.getKey()).forEach(uuid -> this.choosing.remove(uuid));
	}
}