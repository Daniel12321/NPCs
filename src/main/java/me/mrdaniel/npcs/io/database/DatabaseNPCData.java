package me.mrdaniel.npcs.io.database;

import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.io.INPCData;
import me.mrdaniel.npcs.utils.Position;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;

public class DatabaseNPCData implements INPCData {

    @Override
    public int getId() {
        return 0;
    }

    @Nullable
    @Override
    public UUID getUniqueId() {
        return null;
    }

    @Override
    public void setUniqueId(@Nullable UUID uuid) {

    }

    @Override
    public Position getPosition() {
        return new Position("world", 0, 0, 0, 0, 0);
    }

    @Override
    public INPCData setPosition(Position value) {
        return this;
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
