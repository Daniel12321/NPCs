package me.mrdaniel.npcs.io.hocon;

import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializers;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.api.CatalogType;
import org.spongepowered.api.Sponge;

public class CatalogTypeSerializer<T extends CatalogType> implements TypeSerializer<T> {

    private final Class<T> clazz;

    public CatalogTypeSerializer(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Nullable
    @Override
    public T deserialize(@NonNull TypeToken<?> type, @NonNull ConfigurationNode value) throws ObjectMappingException {
        return Sponge.getRegistry().getType(this.clazz, value.getString()).orElse(null);
    }

    @Override
    public void serialize(@NonNull TypeToken<?> type, @Nullable T obj, @NonNull ConfigurationNode value) throws ObjectMappingException {
        value.setValue(obj.getId());
    }

    public static <T extends CatalogType> void register(Class<T> clazz) {
        TypeSerializers.getDefaultSerializers().registerType(TypeToken.of(clazz), new CatalogTypeSerializer<>(clazz));
    }
}
