package me.mrdaniel.npcs.managers.actions;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.actions.ActionSet;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyTypes;
import me.mrdaniel.npcs.exceptions.NPCException;
import me.mrdaniel.npcs.io.INPCData;
import me.mrdaniel.npcs.managers.IActionManager;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scheduler.Task;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ActionManager implements IActionManager {

    private final Map<UUID, INPCData> choosing;
    private final List<UUID> waiting;

    public ActionManager() {
        this.choosing = Maps.newHashMap();
        this.waiting = Lists.newArrayList();
    }

    @Override
    public void execute(UUID uuid, INPCData data) throws NPCException {
        ActionSet actions = data.getProperty(PropertyTypes.ACTION_SET).orElse(new ActionSet());

        if (this.waiting.contains(uuid)) {
            return;
        } else if (actions.getAllActions().size() == 0) {
            return;
        }

        Player p = NPCs.getInstance().getGame().getServer().getPlayer(uuid).orElseThrow(() -> new NPCException("Player not found!"));
        int next = actions.getCurrent(uuid).orElse(0);

        if (next >= actions.getAllActions().size()) {
            if (actions.isRepeatActions()) {
                actions.setCurrent(uuid, 0);
                data.setProperty(PropertyTypes.ACTION_SET, actions);
                data.save();
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
                data.setProperty(PropertyTypes.ACTION_SET, actions);
                data.save();
                if (result.getPerformNextAction()) {
                    try {
                        this.execute(uuid, data);
                    } catch (final NPCException exc) {}
                }
            }).submit(NPCs.getInstance());
        }
        else {
            actions.setCurrent(uuid, result.getNextAction());
            data.setProperty(PropertyTypes.ACTION_SET, actions);
            data.save();
            if (result.getPerformNextAction()) {
                this.execute(uuid, data);
            }
        }
    }

    @Override
    public void executeChoice(INPCData data, UUID uuid, int next) throws NPCException {
        INPCData choosingData = this.choosing.remove(uuid);
        if (choosingData == null) {
            throw new NPCException("You are not choosing anything!");
        } else if (choosingData != data) {
            throw new NPCException("You can't execute old choices!");
        }

        ActionSet actions = data.getProperty(PropertyTypes.ACTION_SET).orElse(new ActionSet());
        actions.setCurrent(uuid, next);
        data.setProperty(PropertyTypes.ACTION_SET, actions);
        data.save();
        this.execute(uuid, data);
    }

    @Override
    public void setChoosing(UUID uuid, INPCData file) {
        this.choosing.put(uuid, file);
    }

    @Override
    public void removeChoosing(UUID uuid) {
        this.choosing.remove(uuid);
    }

    @Override
    public void removeChoosing(INPCData data) {
        this.choosing.entrySet().stream().filter(e -> e.getValue() == data).map(Map.Entry::getKey).forEach(this.choosing::remove);
    }
}
