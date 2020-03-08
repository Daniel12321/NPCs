package me.mrdaniel.npcs.io.hocon.typeserializers;

import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.utils.Position;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public class PositionTypeSerializer implements TypeSerializer<Position> {

    @Nullable
    @Override
    public Position deserialize(@NonNull TypeToken<?> type, @NonNull ConfigurationNode value) {
        return new Position(
                value.getNode("world").getString("world"),
                value.getNode("x").getDouble(0),
                value.getNode("y").getDouble(0),
                value.getNode("z").getDouble(0),
                value.getNode("yaw").getFloat(0),
                value.getNode("pitch").getFloat(0)
        );
    }

    @Override
    public void serialize(@NonNull TypeToken<?> type, @Nullable Position obj, @NonNull ConfigurationNode value) {
        value.getNode("world").setValue(obj.getWorldName());
        value.getNode("x").setValue(obj.getX());
        value.getNode("y").setValue(obj.getY());
        value.getNode("z").setValue(obj.getZ());
        value.getNode("yaw").setValue(obj.getYaw());
        value.getNode("pitch").setValue(obj.getPitch());
    }
}
