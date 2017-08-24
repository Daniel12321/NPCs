package me.mrdaniel.npcs.actions;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.npcs.catalogtypes.actiontype.ActionTypes;
import me.mrdaniel.npcs.io.NPCFile;
import me.mrdaniel.npcs.managers.ActionResult;
import ninja.leaping.configurate.ConfigurationNode;

public class ActionGoto extends Action {

	private int next;

	public ActionGoto(@Nonnull final ConfigurationNode node) { this(node.getNode("Next").getInt(0)); }

	public ActionGoto(final int next) {
		super(ActionTypes.GOTO);

		this.next = next;
	}

	public void setNext(final int next) { this.next = next; }

	@Override
	public void execute(final Player p, final NPCFile file, final ActionResult result) {
		result.setNextAction(this.next);
	}

	@Override
	public void serializeValue(final ConfigurationNode node) {
		node.getNode("Next").setValue(this.next);
	}

	@Override
	public Text getLine(final int index) {
		return Text.builder().append(Text.of(TextColors.GOLD, "Goto: "), 
				Text.builder().append(Text.of(TextColors.AQUA, this.next))
				.onHover(TextActions.showText(Text.of(TextColors.YELLOW, "Change")))
				.onClick(TextActions.suggestCommand("/npc action edit " + index + " goto <goto>")).build()).build();
	}
}