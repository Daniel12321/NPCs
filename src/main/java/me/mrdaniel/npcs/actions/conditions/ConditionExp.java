package me.mrdaniel.npcs.actions.conditions;

import me.mrdaniel.npcs.actions.Condition;
import me.mrdaniel.npcs.catalogtypes.conditiontype.ConditionTypes;
import ninja.leaping.configurate.ConfigurationNode;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class ConditionExp extends Condition {

	private final int exp;

	public ConditionExp(ConfigurationNode node) {
		this(node.getNode("exp").getInt(1));
	}

	public ConditionExp(int exp) {
		super(ConditionTypes.EXP);

		this.exp = exp;
	}

	@Override
	public boolean isMet(Player p) {
		return p.get(Keys.TOTAL_EXPERIENCE).orElse(0) >= this.exp;
	}

	@Override
	public void take(Player p) {
		p.offer(Keys.TOTAL_EXPERIENCE, p.get(Keys.TOTAL_EXPERIENCE).orElse(0) - this.exp);
	}

	@Override
	public void serializeValue(ConfigurationNode node) {
		node.getNode("exp").setValue(this.exp);
	}

	@Override
	public Text getLine() {
		return Text.of(TextColors.GOLD, "Exp: ", TextColors.AQUA, this.exp);
	}
}
