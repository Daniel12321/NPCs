package me.mrdaniel.npcs.io.hocon.typeserializers;

import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.actions.Action;
import me.mrdaniel.npcs.catalogtypes.actiontype.ActionType;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;
import org.checkerframework.checker.nullness.qual.Nullable;

public class ActionTypeSerializer implements TypeSerializer<Action> {

    @Nullable
    @Override
    public Action deserialize(TypeToken<?> type, ConfigurationNode value) throws ObjectMappingException {
        return value.getNode("Type").getValue(TypeToken.of(ActionType.class)).getDeserializer().apply(value);
    }

    @Override
    public void serialize(TypeToken<?> type, Action obj, ConfigurationNode value) throws ObjectMappingException {
        value.getNode("Type").setValue(obj.getType().getId());
        obj.serializeValue(value);
    }
}
