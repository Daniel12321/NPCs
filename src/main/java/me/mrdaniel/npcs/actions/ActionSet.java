package me.mrdaniel.npcs.actions;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class ActionSet {

    private List<Action> actions;
    private Map<UUID, Integer> current;
    private Map<UUID, Long> cooldowns;

    private boolean repeatActions;

    private boolean actionsModified;
    private boolean currentModified;
    private boolean cooldownsModified;

    public ActionSet() {
        this.actions = Lists.newArrayList();
        this.current = Maps.newHashMap();
        this.cooldowns = Maps.newHashMap();
        this.repeatActions = false;
        this.actionsModified = false;
        this.currentModified = false;
        this.cooldownsModified = false;
    }

    public List<Action> getAllActions() {
        return this.actions;
    }

    public Action getAction(int id) {
        return this.actions.get(id);
    }

    public void addAction(Action action) {
        this.actions.add(action);
        this.actionsModified = true;
    }

    public void setAction(int id, Action action) {
        this.actions.set(id, action);
        this.actionsModified = true;
    }

    public void removeAction(int id) {
        this.actions.remove(id);
        this.actionsModified = true;
    }

    public Map<UUID, Integer> getAllCurrent() {
        return this.current;
    }

    public Optional<Integer> getCurrent(UUID uuid) {
        return Optional.ofNullable(this.current.get(uuid));
    }

    public void setCurrent(UUID uuid, int action) {
        this.current.put(uuid, action);
        this.currentModified = true;
    }

    public Map<UUID, Long> getAllCooldowns() {
        return this.cooldowns;
    }

    public Optional<Long> getCooldown(UUID uuid) {
        return Optional.ofNullable(this.cooldowns.get(uuid));
    }

    public void setCooldown(UUID uuid, long time) {
        this.cooldowns.put(uuid, time);
        this.cooldownsModified = true;
    }

    public boolean isRepeatActions() {
        return this.repeatActions;
    }

    public void setRepeatActions(boolean repeatActions) {
        this.repeatActions = repeatActions;
    }

    public boolean isActionsModified() {
        return this.actionsModified;
    }

    public void setActionsModified(boolean actionsModified) {
        this.actionsModified = actionsModified;
    }

    public boolean isCurrentModified() {
        return this.currentModified;
    }

    public void setCurrentModified(boolean currentModified) {
        this.currentModified = currentModified;
    }

    public boolean isCooldownsModified() {
        return this.cooldownsModified;
    }

    public void setCooldownsModified(boolean cooldownsModified) {
        this.cooldownsModified = cooldownsModified;
    }
}
