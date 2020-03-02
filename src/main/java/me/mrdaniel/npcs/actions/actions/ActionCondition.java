package me.mrdaniel.npcs.actions.actions;

import com.google.common.collect.Lists;
import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.actions.Action;
import me.mrdaniel.npcs.actions.Condition;
import me.mrdaniel.npcs.catalogtypes.actiontype.ActionTypes;
import me.mrdaniel.npcs.io.INPCData;
import me.mrdaniel.npcs.managers.ActionResult;
import me.mrdaniel.npcs.utils.TextUtils;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

import java.util.List;

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
		} catch (final ObjectMappingException exc) {
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
	public void serializeValue(ConfigurationNode node) throws ObjectMappingException {
		node.getNode("Condition").setValue(TypeToken.of(Condition.class), this.condition);
		node.getNode("GotoFailed").setValue(this.goto_failed);
		node.getNode("GotoMet").setValue(this.goto_met);
		node.getNode("Take").setValue(this.take);
	}

	@Override
	public List<Text> getLines(int index) {
		List<Text> lines = Lists.newArrayList();

		lines.add(Text.of(TextColors.GOLD, "Condition: ", this.condition.getLine()));
		lines.add(Text.builder().append(
				Text.of(TextColors.GOLD, "                Failed"),
				Text.builder().append(Text.of(TextColors.AQUA, "➡", this.goto_failed))
				.onHover(TextActions.showText(Text.of(TextColors.YELLOW, "Change")))
				.onClick(TextActions.suggestCommand("/npc action edit " + index + " goto_failed <goto>")).build(),
				Text.of(TextColors.GOLD, " Met"),
				Text.builder().append(Text.of(TextColors.AQUA, "➡", this.goto_met))
				.onHover(TextActions.showText(Text.of(TextColors.YELLOW, "Change")))
				.onClick(TextActions.suggestCommand("/npc action edit " + index + " goto_met <goto>")).build(),
				Text.of(" "),
				TextUtils.getToggleText("Take", "/npc action edit " + index + " take", this.take)).build());

		return lines;
	}
}
