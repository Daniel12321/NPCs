package me.mrdaniel.npcs.io.database;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import me.mrdaniel.npcs.actions.Action;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.io.INPCData;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class DatabaseNPCData implements INPCData {

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public <T> Optional<T> getProperty(PropertyType<T> property) {
        return Optional.empty();
    }

    @Override
    public <T> INPCData setProperty(PropertyType<T> property, T value) {
        return this;
    }

    @Override
    public void save() {
    }

    @Override
    public INPCData setRepeatActions(boolean value) {
        return this;
    }

    @Override
    public boolean getRepeatActions() {
        return false;
    }

    @Override
    public List<Action> getActions() {
        return Lists.newArrayList();
    }

    @Override
    public INPCData writeActions() {
        return this;
    }

    @Override
    public Map<UUID, Integer> getCurrent() {
        return Maps.newHashMap();
    }

    @Override
    public INPCData writeCurrent() {
        return this;
    }

    @Override
    public Map<UUID, Long> getCooldowns() {
        return Maps.newHashMap();
    }

    @Override
    public INPCData writeCooldowns() {
        return this;
    }
}
