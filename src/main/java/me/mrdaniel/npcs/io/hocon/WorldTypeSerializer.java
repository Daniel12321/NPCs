package me.mrdaniel.npcs.io.hocon;

import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.world.World;

public class WorldTypeSerializer implements TypeSerializer<World> {

    @Nullable
    @Override
    public World deserialize(@NonNull TypeToken<?> type, @NonNull ConfigurationNode value) throws ObjectMappingException {
        return Sponge.getServer().getWorld(value.getString()).orElse(null);
    }

    @Override
    public void serialize(@NonNull TypeToken<?> type, @Nullable World obj, @NonNull ConfigurationNode value) throws ObjectMappingException {
        value.setValue(obj.getName());
    }
}
