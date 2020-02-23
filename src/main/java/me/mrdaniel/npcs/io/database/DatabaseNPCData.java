package me.mrdaniel.npcs.io.database;

import me.mrdaniel.npcs.actions.ActionSet;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.io.INPCData;
import me.mrdaniel.npcs.utils.Position;

import java.util.Optional;

public class DatabaseNPCData implements INPCData {

    @Override
    public int getNPCId() {
        return 0;
    }

    @Override
    public Position getNPCPosition() {
        return new Position("world", 0, 0, 0, 0, 0);
    }

    @Override
    public INPCData setNPCPosition(Position value) {
        return this;
    }

    @Override
    public <T> Optional<T> getNPCProperty(PropertyType<T> property) {
        return Optional.empty();
    }

    @Override
    public <T> INPCData setNPCProperty(PropertyType<T> property, T value) {
        return this;
    }

    @Override
    public ActionSet getNPCActions() {
        return new ActionSet();
    }

    @Override
    public INPCData writeNPCActions() {
        return this;
    }

    @Override
    public void saveNPC() {
    }
}
