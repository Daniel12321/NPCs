package me.mrdaniel.npcs.actions.actions;

import com.google.common.collect.Lists;
import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.actions.Action;
import me.mrdaniel.npcs.actions.ActionSet;
import me.mrdaniel.npcs.catalogtypes.actiontype.ActionTypes;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyTypes;
import me.mrdaniel.npcs.io.INPCData;
import me.mrdaniel.npcs.managers.ActionResult;
import ninja.leaping.configurate.ConfigurationNode;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.List;
import java.util.Optional;

public class ActionCooldown extends Action {

	private int seconds;
	private String message;

	public ActionCooldown(ConfigurationNode node) {
		this(node.getNode("seconds").getInt(5), node.getNode("message").getString(""));
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
		ActionSet actions = data.getProperty(PropertyTypes.ACTION_SET).orElse(new ActionSet());
		Optional<Long> end = actions.getCooldown(p.getUniqueId());
		if (end.isPresent() && end.get() > System.currentTimeMillis()) {
			p.sendMessage(NPCs.getInstance().getPlaceholderManager().formatNPCMessage(p, this.message, data.getProperty(PropertyTypes.NAME).orElse("NPC")));
			result.setPerformNextAction(false);
		} else {
			result.setNextAction(result.getCurrentAction() + 1);
		}
	}

	@Override
	public void serializeValue(ConfigurationNode node) {
		node.getNode("seconds").setValue(this.seconds);
		node.getNode("message").setValue(this.message);
	}

	@Override
	public List<Text> getLines(int index) {
		return Lists.newArrayList(Text.of(TextColors.GOLD, "Cooldown: ", TextColors.AQUA, this.seconds, " seconds"));
	}
}
