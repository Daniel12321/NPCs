package me.mrdaniel.npcs.actions;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.catalogtypes.conditiontype.ConditionType;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
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

	public abstract Text getLine();
	public abstract boolean isMet(Player p);
	public abstract void take(Player p);
	public abstract void serializeValue(ConfigurationNode value);

	@Nullable
	public static Condition of(ConfigurationNode node) throws ObjectMappingException {
		return NPCs.getInstance().getGame().getRegistry().getType(ConditionType.class, node.getNode("Type").getString("")).orElseThrow(() -> new ObjectMappingException("Invalid ConditionType!")).getCondition().apply(node);
	}
}
