package me.mrdaniel.npcs.actions;

import me.mrdaniel.npcs.catalogtypes.actiontype.ActionTypes;
import me.mrdaniel.npcs.io.NPCFile;
import me.mrdaniel.npcs.managers.ActionResult;
import ninja.leaping.configurate.ConfigurationNode;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

public class ActionGoto extends Action {

	private int next;

	public ActionGoto(ConfigurationNode node) {
		this(node.getNode("Next").getInt(0));
	}

	public ActionGoto(int next) {
		super(ActionTypes.GOTO);

		this.next = next;
	}

	public void setNext(int next) {
		this.next = next;
	}

	@Override
	public void execute(Player p, NPCFile file, ActionResult result) {
		result.setNextAction(this.next);
	}

	@Override
	public void serializeValue(ConfigurationNode node) {
		node.getNode("Next").setValue(this.next);
	}

	@Override
	public Text getLine(int index) {
		return Text.builder().append(Text.of(TextColors.GOLD, "Goto: "), 
				Text.builder().append(Text.of(TextColors.AQUA, this.next))
				.onHover(TextActions.showText(Text.of(TextColors.YELLOW, "Change")))
				.onClick(TextActions.suggestCommand("/npc action edit " + index + " goto <goto>")).build()).build();
	}
}
