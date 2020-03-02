package me.mrdaniel.npcs.actions;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.catalogtypes.actiontype.ActionType;
import me.mrdaniel.npcs.io.INPCData;
import me.mrdaniel.npcs.managers.ActionResult;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import java.util.List;
import java.util.Optional;

public abstract class Action {

	private final ActionType type;

	public Action(ActionType type) {
		this.type = type;
	}

	public ActionType getType() {
		return this.type;
	}

	public abstract List<Text> getLines(final int index);
	public abstract void execute(Player p, INPCData file, ActionResult result);
	public abstract void serializeValue(ConfigurationNode node) throws ObjectMappingException;

	public static Optional<Action> deserialize(ConfigurationNode node) {
		return NPCs.getInstance().getGame().getRegistry()
				.getType(ActionType.class, node.getNode("Type").getString(""))
				.map(type -> type.deserializer().apply(node));
	}
}
