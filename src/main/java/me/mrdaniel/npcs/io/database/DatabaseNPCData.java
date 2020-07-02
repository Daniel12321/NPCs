package me.mrdaniel.npcs.io.database;

import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.io.INPCData;

import java.util.Optional;

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
}
