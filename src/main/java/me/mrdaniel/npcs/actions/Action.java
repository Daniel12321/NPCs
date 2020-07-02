package me.mrdaniel.npcs.actions;

import me.mrdaniel.npcs.catalogtypes.actiontype.ActionType;
import me.mrdaniel.npcs.io.INPCData;
import me.mrdaniel.npcs.managers.actions.ActionResult;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import java.util.List;

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
}
