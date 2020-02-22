package me.mrdaniel.npcs.actions;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.catalogtypes.actiontype.ActionTypes;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyTypes;
import me.mrdaniel.npcs.io.INPCData;
import me.mrdaniel.npcs.managers.ActionResult;
import ninja.leaping.configurate.ConfigurationNode;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class ActionCooldown extends Action {

	private int seconds;
	private String message;

	public ActionCooldown(ConfigurationNode node) {
		this(node.getNode("Seconds").getInt(5), node.getNode("Message").getString(""));
	}
	
	public ActionCooldown(int seconds, String message) {
		super(ActionTypes.DELAY);

		this.seconds = seconds;
		this.message = message;
	}

	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public void execute(Player p, INPCData data, ActionResult result) {
		Long end = data.getCooldowns().get(p.getUniqueId());
		if (end == null || end > System.currentTimeMillis()) {
			p.sendMessage(NPCs.getInstance().getPlaceholderManager().formatNPCMessage(p, this.message, data.getProperty(PropertyTypes.NAME).orElse("NPC")));
			result.setPerformNextAction(false);
		} else {
			result.setNextAction(result.getCurrentAction() + 1);
		}
	}

	@Override
	public void serializeValue(ConfigurationNode node) {
		node.getNode("Seconds").setValue(this.seconds);
		node.getNode("Message").setValue(this.message);
	}

	@Override
	public Text getLine(int index) {
		return Text.of(TextColors.GOLD, "Cooldown: ", TextColors.AQUA, this.seconds, " seconds");
	}
}
