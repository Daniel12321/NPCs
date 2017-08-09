package me.mrdaniel.npcs.actions;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.npcs.catalogtypes.actions.ActionTypes;
import me.mrdaniel.npcs.io.NPCFile;
import me.mrdaniel.npcs.managers.ActionResult;
import me.mrdaniel.npcs.managers.PlaceholderManager;
import ninja.leaping.configurate.ConfigurationNode;

public class ActionCooldown extends Action {

	private int seconds;
	private String message;

	public ActionCooldown(@Nonnull final ConfigurationNode node) { this(node.getNode("Seconds").getInt(5), node.getNode("Message").getString("")); }
	public ActionCooldown(final int seconds, @Nonnull final String message) {
		super(ActionTypes.DELAY);

		this.seconds = seconds;
		this.message = message;
	}

	public void setSeconds(final int seconds) { this.seconds = seconds; }
	public void setMessage(@Nonnull final String message) { this.message = message; }

	@Override
	public void execute(final ActionResult result, final Player p, final NPCFile file) {
		Long end = file.getCooldowns().get(p.getUniqueId());
		if (end == null || end > System.currentTimeMillis()) {
			p.sendMessage(PlaceholderManager.getInstance().formatNPCMessage(p, this.message, file.getName().orElse("NPC")));
			result.setPerformNext(false);
		}
		else { result.setNext(result.getCurrent() + 1); }
	}

	@Override
	public void serializeValue(final ConfigurationNode node) {
		node.getNode("Seconds").setValue(this.seconds);
		node.getNode("Message").setValue(this.message);
	}

	@Override
	public Text getLine(final int index) {
		return Text.of(TextColors.GOLD, "Cooldown: ", TextColors.AQUA, this.seconds, " seconds");
	}
}