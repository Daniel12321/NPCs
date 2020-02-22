package me.mrdaniel.npcs.managers;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.exceptions.NPCException;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.io.INPCData;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scheduler.Task;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class ActionManager {

	private final Map<UUID, INPCData> choosing;
	private final List<UUID> waiting;

	public ActionManager() {
		this.choosing = Maps.newHashMap();
		this.waiting = Lists.newArrayList();
	}

	public void executeChoice(INPCData file, UUID uuid, int next) throws NPCException {
		INPCData f = this.choosing.remove(uuid);
		if (f == null) {
			throw new NPCException("You are not choosing!");
		}
		if (f != file) {
			throw new NPCException("You can't execute old choices!");
		}

		file.getCurrent().put(uuid, next);
		file.writeCurrent().save();
		this.execute(uuid, file);
	}

//	public void execute(UUID uuid, Living npc) throws NPCException {
//		this.execute(uuid, npc.get(NPCData.class).orElseThrow(() -> new NPCException("This Entity is not an NPC!")));
//	}

//	public void execute(UUID uuid, NPCData data) throws NPCException {
//		this.execute(uuid, NPCManager.getInstance().getFile(data.getId()).orElseThrow(() -> new NPCException("Could not find file for NPC!")));
//	}

	public void execute(UUID uuid, INPCData data) throws NPCException {
		if (this.waiting.contains(uuid)) {
			return;
		}
		if (data.getActions().size() == 0) {
			return;
		}

		Player p = NPCs.getInstance().getGame().getServer().getPlayer(uuid).orElseThrow(() -> new NPCException("Player not found!"));
		int next = Optional.ofNullable(data.getCurrent().get(uuid)).orElse(0);

		if (next >= data.getActions().size()) {
			if (data.getRepeatActions()) {
				data.getCurrent().put(uuid, 0);
				data.writeCurrent().save();
			}
			return;
		}

		ActionResult result = new ActionResult(next);

		data.getActions().get(next).execute(p, data, result);

		if (result.getWaitTicks() > 0) {
			this.waiting.add(uuid);
			Task.builder().delayTicks(result.getWaitTicks()).execute(() -> {
				this.waiting.remove(uuid);
				data.getCurrent().put(uuid, result.getNextAction());
				data.writeCurrent().save();
				if (result.getPerformNextAction()) {
					try { this.execute(uuid, data); }
					catch (final NPCException exc) {}
				}
			}).submit(NPCs.getInstance());
		}
		else {
			data.getCurrent().put(uuid, result.getNextAction());
			data.writeCurrent().save();
			if (result.getPerformNextAction()) { this.execute(uuid, data); }
		}
	}

	public void setChoosing(UUID uuid, INPCData file) {
		this.choosing.put(uuid, file);
	}

	public void removeChoosing(UUID uuid) {
		this.choosing.remove(uuid);
	}

	public void removeChoosing(NPCAble npc) {
		this.choosing.entrySet().stream().filter(e -> e.getValue() == npc.getNPCData()).map(e -> e.getKey()).forEach(this.choosing::remove);
	}
}
