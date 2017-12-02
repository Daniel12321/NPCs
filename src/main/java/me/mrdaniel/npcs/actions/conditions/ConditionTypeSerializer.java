package me.mrdaniel.npcs.actions.conditions;

import com.google.common.reflect.TypeToken;

import me.mrdaniel.npcs.exceptions.ConditionException;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;

public class ConditionTypeSerializer implements TypeSerializer<Condition> {

	@Override
	public Condition deserialize(final TypeToken<?> type, final ConfigurationNode value) throws ObjectMappingException {
		try { return Condition.of(value); }
		catch (final ConditionException exc) { throw new ObjectMappingException("Failed to read condition!", exc); }
	}

	@Override
	public void serialize(final TypeToken<?> type, final Condition condition, final ConfigurationNode value) throws ObjectMappingException {
		condition.serialize(value);
	}
}