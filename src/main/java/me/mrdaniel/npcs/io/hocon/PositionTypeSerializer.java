package me.mrdaniel.npcs.io.hocon;

import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.utils.Position;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public class PositionTypeSerializer implements TypeSerializer<Position> {

    @Nullable
    @Override
    public Position deserialize(@NonNull TypeToken<?> type, @NonNull ConfigurationNode value) throws ObjectMappingException {
        return new Position(
                value.getNode("x").getDouble(),
                value.getNode("y").getDouble(),
                value.getNode("z").getDouble(),
                value.getNode("yaw").getFloat(),
                value.getNode("pitch").getFloat()
        );
    }

    @Override
    public void serialize(@NonNull TypeToken<?> type, @Nullable Position obj, @NonNull ConfigurationNode value) throws ObjectMappingException {
        value.getNode("x").setValue(obj.getX());
        value.getNode("y").setValue(obj.getY());
        value.getNode("z").setValue(obj.getZ());
        value.getNode("yaw").setValue(obj.getYaw());
        value.getNode("pitch").setValue(obj.getPitch());
    }
}
