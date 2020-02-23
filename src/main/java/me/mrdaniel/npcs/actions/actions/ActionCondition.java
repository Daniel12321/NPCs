package me.mrdaniel.npcs.actions.actions;

import me.mrdaniel.npcs.actions.Action;
import me.mrdaniel.npcs.actions.Condition;
import me.mrdaniel.npcs.catalogtypes.actiontype.ActionTypes;
import me.mrdaniel.npcs.exceptions.ConditionException;
import me.mrdaniel.npcs.io.INPCData;
import me.mrdaniel.npcs.managers.ActionResult;
import me.mrdaniel.npcs.utils.TextUtils;
import ninja.leaping.configurate.ConfigurationNode;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

public class ActionCondition extends Action {

	private final Condition condition;
	private int goto_failed;
	private int goto_met;
	private boolean take;

	public ActionCondition(ConfigurationNode node) {
		super(ActionTypes.CONDITION);

		Condition condition = null;
		try {
			condition = Condition.of(node.getNode("Condition"));
		} catch (final ConditionException exc) {
			exc.printStackTrace();
		} finally {
			this.condition = condition;
		}

		this.goto_failed = node.getNode("GotoFailed").getInt(0);
		this.goto_met = node.getNode("GotoMet").getInt(0);
		this.take = node.getNode("Take").getBoolean(true);
	}

	public ActionCondition(Condition condition, int goto_failed, int goto_met, boolean take) {
		super(ActionTypes.CONDITION);

		this.condition = condition;
		this.goto_failed = goto_failed;
		this.goto_met = goto_met;
		this.take = take;
	}

	public void setGotoFailed(int goto_failed) {
		this.goto_failed = goto_failed;
	}

	public void setGotoMet(int goto_met) {
		this.goto_met = goto_met;
	}

	public void setTake(boolean take) {
		this.take = take;
	}

	@Override
	public void execute(Player p, INPCData data, ActionResult result) {
		if (this.condition.isMet(p)) {
			if (this.take) {
				this.condition.take(p);
			}
			result.setNextAction(this.goto_met);
		} else {
			result.setNextAction(this.goto_failed);
		}
	}

	@Override
	public void serializeValue(ConfigurationNode node) {
		this.condition.serialize(node.getNode("Condition"));
		node.getNode("GotoFailed").setValue(this.goto_failed);
		node.getNode("GotoMet").setValue(this.goto_met);
		node.getNode("Take").setValue(this.take);
	}

	@Override
	public Text getLine(int index) {
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