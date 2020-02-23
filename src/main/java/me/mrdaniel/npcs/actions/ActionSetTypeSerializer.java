package me.mrdaniel.npcs.actions;

import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;

import java.util.UUID;

public class ActionSetTypeSerializer implements TypeSerializer<ActionSet> {

	@Override
	public ActionSet deserialize(TypeToken<?> type, ConfigurationNode node) throws ObjectMappingException {
		ActionSet actions = new ActionSet();
		actions.setRepeatActions(node.getNode("repeat_actions").getBoolean(true));

		node.getNode("current").getChildrenMap().forEach((uuid, child) -> actions.setCurrent(UUID.fromString((String)uuid), child.getInt(0)));
		node.getNode("cooldowns").getChildrenMap().forEach((uuid, child) -> actions.setCooldown(UUID.fromString((String)uuid), child.getLong(0)));

        for (int i = 0; i < node.getNode("actions").getChildrenMap().size(); i++) {
        	Action.of(node.getNode("actions", Integer.toString(i))).ifPresent(actions::addAction);
        }

		return actions;
	}

	@Override
	public void serialize(TypeToken<?> type, ActionSet actions, ConfigurationNode node) throws ObjectMappingException {
		node.getNode("repeat_actions").setValue(actions.isRepeatActions());

		if (actions.isActionsModified()) {
			for (int i = 0; i < actions.getAllActions().size(); i++) {
				node.getNode("actions").removeChild(Integer.toString(i));
				actions.getAllActions().get(i).serialize(node.getNode("actions", Integer.toString(i)));
			}
		}
		if (actions.isCurrentModified()) {
			actions.getAllCurrent().forEach((uuid, current) -> node.getNode("current", uuid.toString()).setValue(current));
		}
		if (actions.isCooldownsModified()) {
			actions.getAllCooldowns().forEach((uuid, end) -> node.getNode("cooldowns", uuid.toString()).setValue(end));
		}
	}
}
