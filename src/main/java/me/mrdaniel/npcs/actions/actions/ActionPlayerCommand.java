package me.mrdaniel.npcs.actions.actions;

import com.google.common.collect.Lists;
import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.actions.Action;
import me.mrdaniel.npcs.catalogtypes.actiontype.ActionTypes;
import me.mrdaniel.npcs.io.INPCData;
import me.mrdaniel.npcs.managers.actions.ActionResult;
import ninja.leaping.configurate.ConfigurationNode;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

import java.util.List;

public class ActionPlayerCommand extends Action {

	private String command;

	public ActionPlayerCommand(ConfigurationNode node) {
		this(node.getNode("command").getString(""));
	}

	public ActionPlayerCommand(String command) {
		super(ActionTypes.PLAYERCMD);

		this.command = command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	@Override
	public void execute(Player p, INPCData data, final ActionResult result) {
		NPCs.getInstance().getGame().getCommandManager().process(p, NPCs.getInstance().getPlaceholderManager().formatCommand(p, this.command));
		result.setNextAction(result.getCurrentAction()+1);
	}

	@Override
	public void serializeValue(ConfigurationNode node) {
		node.getNode("command").setValue(this.command);
	}

	@Override
	public List<Text> getLines(int index) {
		return Lists.newArrayList(Text.builder().append(Text.of(TextColors.GOLD, "Player Command: "),
				Text.builder().append(Text.of(TextColors.AQUA, this.command))
				.onHover(TextActions.showText(Text.of(TextColors.YELLOW, "Change")))
				.onClick(TextActions.suggestCommand("/npc action edit " + index + " playercmd <command...>"))
				.build()).build());
	}
}
