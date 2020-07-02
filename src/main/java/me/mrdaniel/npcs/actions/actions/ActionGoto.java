package me.mrdaniel.npcs.actions.actions;

import com.google.common.collect.Lists;
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

public class ActionGoto extends Action {

	private int next;

	public ActionGoto(ConfigurationNode node) {
		this(node.getNode("next").getInt(0));
	}

	public ActionGoto(int next) {
		super(ActionTypes.GOTO);

		this.next = next;
	}

	public void setNext(int next) {
		this.next = next;
	}

	@Override
	public void execute(Player p, INPCData data, ActionResult result) {
		result.setNextAction(this.next);
	}

	@Override
	public void serializeValue(ConfigurationNode node) {
		node.getNode("next").setValue(this.next);
	}

	@Override
	public List<Text> getLines(int index) {
		return Lists.newArrayList(Text.builder().append(Text.of(TextColors.GOLD, "Goto: "),
				Text.builder().append(Text.of(TextColors.AQUA, this.next))
				.onHover(TextActions.showText(Text.of(TextColors.YELLOW, "Change")))
				.onClick(TextActions.suggestCommand("/npc action edit " + index + " goto <goto>")).build()).build());
	}
}
