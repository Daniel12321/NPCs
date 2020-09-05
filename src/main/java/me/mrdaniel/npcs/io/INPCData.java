package me.mrdaniel.npcs.io;

import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;

public interface INPCData {

    int getId();

    <T> Optional<T> getProperty(PropertyType<T> property);
    <T> INPCData setProperty(PropertyType<T> property, T value);

    void save();
}
