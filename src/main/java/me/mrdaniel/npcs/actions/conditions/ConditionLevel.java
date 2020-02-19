package me.mrdaniel.npcs.actions.conditions;

import me.mrdaniel.npcs.catalogtypes.conditiontype.ConditionTypes;
import ninja.leaping.configurate.ConfigurationNode;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class ConditionLevel extends Condition {

	private final int level;

	public ConditionLevel(ConfigurationNode node) {
		this(node.getNode("Level").getInt(1));
	}

	public ConditionLevel(int level) {
		super(ConditionTypes.LEVEL);

		this.level = level;
	}

	@Override
	public boolean isMet(Player p) {
		return p.get(Keys.EXPERIENCE_LEVEL).orElse(0) >= this.level;
	}

	@Override
	public void take(Player p) {
		p.offer(Keys.EXPERIENCE_LEVEL, p.get(Keys.EXPERIENCE_LEVEL).orElse(0) - this.level);
	}

	@Override
	public void serializeValue(ConfigurationNode node) {
		node.getNode("Level").setValue(this.level);
	}

	@Override
	public Text getLine() {
		return Text.of(TextColors.GOLD, "Level: ", TextColors.AQUA, this.level);
	}
}
