package me.mrdaniel.npcs.data.npc.actions.conditions;

import javax.annotation.Nonnull;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.npcs.catalogtypes.conditions.ConditionTypes;
import ninja.leaping.configurate.ConfigurationNode;

public class ConditionExp extends Condition {

	private final int exp;

	public ConditionExp(@Nonnull final ConfigurationNode node) { this(node.getNode("Exp").getInt(1)); }

	public ConditionExp(final int exp) {
		super(ConditionTypes.EXP);

		this.exp = exp;
	}

	@Override
	public boolean isMet(final Player p) {
		return p.get(Keys.TOTAL_EXPERIENCE).orElse(0) >= this.exp;
	}

	@Override
	public void take(final Player p) {
		p.offer(Keys.TOTAL_EXPERIENCE, p.get(Keys.TOTAL_EXPERIENCE).orElse(0) - this.exp);
	}

	@Override
	public void serializeValue(final ConfigurationNode node) {
		node.getNode("Exp").setValue(this.exp);
	}

	@Override
	public Text getLine() {
		return Text.of(TextColors.GOLD, "Exp: ", TextColors.AQUA, this.exp);
	}
}