package me.mrdaniel.npcs.actions;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.npcs.catalogtypes.actiontype.ActionTypes;
import me.mrdaniel.npcs.io.NPCFile;
import me.mrdaniel.npcs.managers.ActionResult;
import ninja.leaping.configurate.ConfigurationNode;

public class ActionPause extends Action {

	public ActionPause(@Nonnull final ConfigurationNode node) { this(); }
	public ActionPause() {
		super(ActionTypes.PAUSE);
	}

	@Override
	public void execute(final Player p, final NPCFile file, final ActionResult result) {
		result.setNextAction(result.getCurrentAction()+1).setPerformNextAction(false);
	}

	@Override
	public void serializeValue(final ConfigurationNode node) {}

	@Override
	public Text getLine(final int index) {
		return Text.of(TextColors.GOLD, "Pause");
	}
}