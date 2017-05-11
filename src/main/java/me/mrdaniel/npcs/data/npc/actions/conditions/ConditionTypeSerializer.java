package me.mrdaniel.npcs.data.npc.actions.conditions;

import com.google.common.reflect.TypeToken;

import me.mrdaniel.npcs.exceptions.ConditionException;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;

public class ConditionTypeSerializer implements TypeSerializer<Condition> {

	@Override
	public Condition deserialize(TypeToken<?> type, ConfigurationNode value) throws ObjectMappingException {
		try { return Condition.of(value); }
		catch (final ConditionException exc) { throw new ObjectMappingException("Failed to read condition!", exc); }
	}

	@Override
	public void serialize(TypeToken<?> type, Condition condition, ConfigurationNode value) throws ObjectMappingException {
		condition.serialize(value);
	}
}