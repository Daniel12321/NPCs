package me.mrdaniel.npcs.actions;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.catalogtypes.actiontype.ActionType;
import me.mrdaniel.npcs.exceptions.ActionException;
import me.mrdaniel.npcs.io.INPCData;
import me.mrdaniel.npcs.managers.ActionResult;
import ninja.leaping.configurate.ConfigurationNode;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

public abstract class Action {

	private final ActionType type;

	public Action(ActionType type) {
		this.type = type;
	}

	public ActionType getType() {
		return this.type;
	}

	public void serialize(ConfigurationNode node) {
		node.getNode("Type").setValue(this.type.getId());
		this.serializeValue(node);
	}

	public abstract Text getLine(final int index);
	public abstract void execute(Player p, INPCData file, ActionResult result);
	public abstract void serializeValue(ConfigurationNode node);

	public static Action of(ConfigurationNode node) throws ActionException {
		return NPCs.getInstance().getGame().getRegistry().getType(ActionType.class, node.getNode("Type").getString("")).orElseThrow(() -> new ActionException("Invalid ActionType!")).getAction().apply(node);
	}
}
