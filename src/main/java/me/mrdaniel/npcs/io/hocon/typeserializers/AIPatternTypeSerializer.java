package me.mrdaniel.npcs.io.hocon.typeserializers;

import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.ai.pattern.AbstractAIPattern;
import me.mrdaniel.npcs.catalogtypes.aitype.AIType;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public class AIPatternTypeSerializer implements TypeSerializer<AbstractAIPattern> {

    @Nullable
    @Override
    public AbstractAIPattern deserialize(@NonNull TypeToken<?> type, @NonNull ConfigurationNode value) throws ObjectMappingException {
        AIType aiType = value.getNode("Type").getValue(TypeToken.of(AIType.class));

        return aiType == null ? null : aiType.getDeserializer().apply(value);
    }

    @Override
    public void serialize(@NonNull TypeToken<?> type, @Nullable AbstractAIPattern obj, @NonNull ConfigurationNode value) throws ObjectMappingException {
        value.getNode("Type").setValue(obj.getType().getId());
        obj.serialize(value);
    }
}
