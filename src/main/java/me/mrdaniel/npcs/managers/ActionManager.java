package me.mrdaniel.npcs.managers;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.actions.ActionSet;
import me.mrdaniel.npcs.exceptions.NPCException;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.io.INPCData;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scheduler.Task;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ActionManager {

	private final Map<UUID, INPCData> choosing;
	private final List<UUID> waiting;

	public ActionManager() {
		this.choosing = Maps.newHashMap();
		this.waiting = Lists.newArrayList();
	}

	public void executeChoice(INPCData data, UUID uuid, int next) throws NPCException {
		INPCData choosingData = this.choosing.remove(uuid);
		if (choosingData == null) {
			throw new NPCException("You are not choosing anything!");
		} else if (choosingData != data) {
			throw new NPCException("You can't execute old choices!");
		}

		ActionSet actions = data.getNPCActions();
		actions.setCurrent(uuid, next);
		data.writeNPCActions().saveNPC();
		this.execute(uuid, data);
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
		} else if (data.getNPCActions().getAllActions().size() == 0) {
			return;
		}

		ActionSet actions = data.getNPCActions();
		Player p = NPCs.getInstance().getGame().getServer().getPlayer(uuid).orElseThrow(() -> new NPCException("Player not found!"));
		int next = actions.getCurrent(uuid).orElse(0);

		if (next >= actions.getAllActions().size()) {
			if (actions.isRepeatActions()) {
				actions.setCurrent(uuid, 0);
				data.writeNPCActions().saveNPC();
			}
			return;
		}

		ActionResult result = new ActionResult(next);
		actions.getAction(next).execute(p, data, result);

		if (result.getWaitTicks() > 0) {
			this.waiting.add(uuid);
			Task.builder().delayTicks(result.getWaitTicks()).execute(() -> {
				this.waiting.remove(uuid);
				actions.setCurrent(uuid, result.getNextAction());
				data.writeNPCActions().saveNPC();
				if (result.getPerformNextAction()) {
					try {
						this.execute(uuid, data);
					} catch (final NPCException exc) {}
				}
			}).submit(NPCs.getInstance());
		}
		else {
			actions.setCurrent(uuid, result.getNextAction());
			data.writeNPCActions().saveNPC();
			if (result.getPerformNextAction()) {
				this.execute(uuid, data);
			}
		}
	}

	public void setChoosing(UUID uuid, INPCData file) {
		this.choosing.put(uuid, file);
	}

	public void removeChoosing(UUID uuid) {
		this.choosing.remove(uuid);
	}

	public void removeChoosing(NPCAble npc) {
		this.choosing.entrySet().stream().filter(e -> e.getValue() == npc.getNPCData()).map(Map.Entry::getKey).forEach(this.choosing::remove);
	}
}
