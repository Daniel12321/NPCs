package me.mrdaniel.npcs.io.hocon.typeserializers;

import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.actions.Condition;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;

public class ConditionTypeSerializer implements TypeSerializer<Condition> {

	@Override
	public Condition deserialize(TypeToken<?> type, ConfigurationNode value) throws ObjectMappingException {
		return Condition.of(value);
	}

	@Override
	public void serialize(TypeToken<?> type, Condition condition, ConfigurationNode value) throws ObjectMappingException {
		value.getNode("Type").setValue(condition.getType().getId());
		condition.serializeValue(value);
	}
}
