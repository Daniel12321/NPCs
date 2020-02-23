package me.mrdaniel.npcs.actions;

import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.exceptions.ActionException;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;

import java.util.UUID;

public class ActionSetSerializer implements TypeSerializer<ActionSet> {

	@Override
	public ActionSet deserialize(TypeToken<?> type, ConfigurationNode node) throws ObjectMappingException {
		ActionSet actions = new ActionSet();
		actions.setRepeatActions(node.getNode("repeat_actions").getBoolean(true));

		node.getNode("current").getChildrenMap().forEach((uuid, child) -> actions.setCurrent(UUID.fromString((String)uuid), child.getInt(0)));
		node.getNode("cooldowns").getChildrenMap().forEach((uuid, child) -> actions.setCooldown(UUID.fromString((String)uuid), child.getLong(0)));

        for (int i = 0; i < node.getNode("actions").getChildrenMap().size(); i++) {
            try {
            	actions.addAction(Action.of(node.getNode("actions", i)));
            } catch (final ActionException exc) {
            	NPCs.getInstance().getLogger().error("Failed to read action for npc: ", exc);
            }
        }

		return actions;
	}

	@Override
	public void serialize(TypeToken<?> type, ActionSet actions, ConfigurationNode node) throws ObjectMappingException {
		node.getNode("repeat_actions").setValue(actions.isRepeatActions());

		for (int i = 0; i < actions.getActions().size(); i++) {
			actions.getActions().get(i).serialize(node.getNode("actions", Integer.toString(i)));
		}

		actions.getCurrent().forEach((uuid, current) -> node.getNode("current", uuid.toString()).setValue(current));
		actions.getCooldowns().forEach((uuid, end) -> node.getNode("cooldowns", uuid.toString()).setValue(end));
	}
}
