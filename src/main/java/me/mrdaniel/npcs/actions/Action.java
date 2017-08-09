package me.mrdaniel.npcs.actions;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import me.mrdaniel.npcs.catalogtypes.actions.ActionType;
import me.mrdaniel.npcs.catalogtypes.actions.ActionTypeRegistryModule;
import me.mrdaniel.npcs.exceptions.ActionException;
import me.mrdaniel.npcs.io.NPCFile;
import me.mrdaniel.npcs.managers.ActionResult;
import ninja.leaping.configurate.ConfigurationNode;

public abstract class Action {

	private final ActionType type;

	public Action(@Nonnull final ActionType type) {
		this.type = type;
	}

	@Nonnull public ActionType getType() { return this.type; }
	@Nonnull public abstract Text getLine(final int index);

	public void serialize(@Nonnull final ConfigurationNode node) {
		node.getNode("Type").setValue(this.type.getId());
		this.serializeValue(node);
	}

	public abstract void execute(final ActionResult result, @Nonnull final Player p, @Nonnull final NPCFile file);
	public abstract void serializeValue(@Nonnull final ConfigurationNode node);

	@Nonnull
	public static Action of(@Nonnull final ConfigurationNode node) throws ActionException {
		return ActionTypeRegistryModule.getInstance().getById(node.getNode("Type").getString("")).orElseThrow(() -> new ActionException("Invalid ActionType!")).getAction().apply(node);
	}
}