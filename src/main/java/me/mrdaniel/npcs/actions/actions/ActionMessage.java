package me.mrdaniel.npcs.actions.actions;

import com.google.common.collect.Lists;
import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.actions.Action;
import me.mrdaniel.npcs.catalogtypes.actiontype.ActionTypes;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyTypes;
import me.mrdaniel.npcs.io.INPCData;
import me.mrdaniel.npcs.managers.actions.ActionResult;
import ninja.leaping.configurate.ConfigurationNode;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

import java.util.List;

public class ActionMessage extends Action {

	private String message;

	public ActionMessage(ConfigurationNode node) {
		this(node.getNode("message").getString(""));
	}
	
	public ActionMessage(String message) {
		super(ActionTypes.MESSAGE);

		this.message = message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public void execute(Player p, INPCData data, ActionResult result) {
		p.sendMessage(NPCs.getInstance().getPlaceholderManager().formatNPCMessage(p, this.message, data.getProperty(PropertyTypes.NAME).orElse("")));
		result.setNextAction(result.getCurrentAction()+1);
	}

	@Override
	public void serializeValue(ConfigurationNode node) {
		node.getNode("message").setValue(this.message);
	}

	@Override
	public List<Text> getLines(int index) {
		return Lists.newArrayList(Text.builder().append(Text.of(TextColors.GOLD, "Message: "),
				Text.builder().append(Text.of(TextColors.AQUA, this.message))
				.onHover(TextActions.showText(Text.of(TextColors.YELLOW, "Change")))
				.onClick(TextActions.suggestCommand("/npc action edit " + index + " message <message...>"))
				.build()).build());
	}
}
