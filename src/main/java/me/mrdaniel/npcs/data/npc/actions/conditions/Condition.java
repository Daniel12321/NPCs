package me.mrdaniel.npcs.data.npc.actions.conditions;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import me.mrdaniel.npcs.catalogtypes.conditions.ConditionType;
import me.mrdaniel.npcs.catalogtypes.conditions.ConditionTypes;
import me.mrdaniel.npcs.exceptions.ConditionException;
import ninja.leaping.configurate.ConfigurationNode;

public abstract class Condition {

	private final ConditionType type;

	public Condition(@Nonnull final ConditionType type) {
		this.type = type;
	}

	@Nonnull public ConditionType getType() { return this.type; }
	@Nonnull public abstract Text getLine();

	public abstract boolean isMet(@Nonnull final Player p);
	public abstract void take(@Nonnull final Player p);

	public void serialize(@Nonnull final ConfigurationNode node) {
		node.getNode("Type").setValue(this.type.getId());
		this.serializeValue(node);
	}

	public abstract void serializeValue(@Nonnull final ConfigurationNode value);

	@Nullable
	public static Condition of(@Nonnull final ConfigurationNode node) throws ConditionException {
		ConditionType type = ConditionTypes.of(node.getNode("Type").getString("")).orElseThrow(() -> new ConditionException("Invalid ConditionType!"));
		try { return type.getConditionClass().getConstructor(ConfigurationNode.class).newInstance(node); }
		catch (final Exception exc) { throw new ConditionException("Failed to read condition value!", exc); }
	}
}