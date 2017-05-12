package me.mrdaniel.npcs.data.npc.actions;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.catalogtypes.actions.ActionTypes;
import me.mrdaniel.npcs.data.npc.actions.conditions.Condition;
import me.mrdaniel.npcs.exceptions.ConditionException;
import me.mrdaniel.npcs.io.NPCFile;
import me.mrdaniel.npcs.managers.ActionResult;
import me.mrdaniel.npcs.utils.TextUtils;
import ninja.leaping.configurate.ConfigurationNode;

public class ActionCondition extends Action {

	private final Condition condition;
	private int goto_failed;
	private int goto_met;
	private boolean take;

	public ActionCondition(@Nonnull final ConfigurationNode node) throws ConditionException { this(Condition.of(node.getNode("Condition")), node.getNode("GotoFailed").getInt(0), node.getNode("GotoMet").getInt(0), node.getNode("Take").getBoolean(true)); }

	public ActionCondition(@Nonnull final Condition condition, final int goto_failed, final int goto_met, final boolean take) {
		super(ActionTypes.CONDITION);

		this.condition = condition;
		this.goto_failed = goto_failed;
		this.goto_met = goto_met;
		this.take = take;
	}

	public void setGotoFailed(final int goto_failed) { this.goto_failed = goto_failed; }
	public void setGotoMet(final int goto_met) { this.goto_met = goto_met; }
	public void setTake(final boolean take) { this.take = take; }

	@Override
	public void execute(final NPCs npcs, final ActionResult result, final Player p, final NPCFile file) {
		if (this.condition.isMet(p)) {
			if (this.take) { this.condition.take(p); }
			result.setNext(this.goto_met);
		}
		else { result.setNext(this.goto_failed); }
	}

	@Override
	public void serializeValue(final ConfigurationNode node) {
		this.condition.serialize(node.getNode("Condition"));
		node.getNode("GotoFailed").setValue(this.goto_failed);
		node.getNode("GotoMet").setValue(this.goto_met);
		node.getNode("Take").setValue(this.take);
	}

	@Override
	public Text getLine(final int index) {
		return Text.builder().append(
				Text.of(TextColors.GOLD, "Condition: "),
				this.condition.getLine(),
				Text.of(TextColors.GOLD, " Failed"),
				Text.builder().append(Text.of(TextColors.AQUA, "➡", this.goto_failed))
				.onHover(TextActions.showText(Text.of(TextColors.YELLOW, "Change")))
				.onClick(TextActions.suggestCommand("/npc action edit " + index + " goto_failed <goto>")).build(),
				Text.of(TextColors.GOLD, " Met"),
				Text.builder().append(Text.of(TextColors.AQUA, "➡", this.goto_met))
				.onHover(TextActions.showText(Text.of(TextColors.YELLOW, "Change")))
				.onClick(TextActions.suggestCommand("/npc action edit " + index + " goto_met <goto>")).build(),
				Text.of(" "),
				TextUtils.getToggleText("Take", "/npc action edit " + index + " take", this.take)).build();
	}
}