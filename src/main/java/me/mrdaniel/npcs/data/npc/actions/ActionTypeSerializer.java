package me.mrdaniel.npcs.data.npc.actions;

import com.google.common.reflect.TypeToken;

import me.mrdaniel.npcs.exceptions.ActionException;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;

public class ActionTypeSerializer implements TypeSerializer<Action> {

	@Override
	public Action deserialize(final TypeToken<?> type, final ConfigurationNode node) throws ObjectMappingException {
		try { return Action.of(node); }
		catch (final ActionException exc) { throw new ObjectMappingException("Failed to read action!", exc); }
	}

	@Override
	public void serialize(final TypeToken<?> type, final Action action, final ConfigurationNode node) throws ObjectMappingException {
		action.serialize(node);
	}
}