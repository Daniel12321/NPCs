package me.mrdaniel.npcs.actions;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.catalogtypes.conditiontype.ConditionType;
import me.mrdaniel.npcs.exceptions.ConditionException;
import ninja.leaping.configurate.ConfigurationNode;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import javax.annotation.Nullable;

public abstract class Condition {

	private final ConditionType type;

	public Condition(ConditionType type) {
		this.type = type;
	}

	public ConditionType getType() {
		return this.type;
	}

	public void serialize(ConfigurationNode node) {
		node.getNode("Type").setValue(this.type.getId());
		this.serializeValue(node);
	}

	public abstract Text getLine();
	public abstract boolean isMet(Player p);
	public abstract void take(Player p);
	public abstract void serializeValue(ConfigurationNode value);

	@Nullable
	public static Condition of(ConfigurationNode node) throws ConditionException {
		return NPCs.getInstance().getGame().getRegistry().getType(ConditionType.class, node.getNode("Type").getString("")).orElseThrow(() -> new ConditionException("Invalid ConditionType!")).getCondition().apply(node);
	}
}
