package me.mrdaniel.npcs.data.npc.actions;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.catalogtypes.actions.ActionTypes;
import me.mrdaniel.npcs.io.NPCFile;
import me.mrdaniel.npcs.managers.ActionResult;
import ninja.leaping.configurate.ConfigurationNode;

public class ActionPause extends Action {

	public ActionPause(@Nonnull final ConfigurationNode node) { this(); }

	public ActionPause() {
		super(ActionTypes.PAUSE);
	}

	@Override
	public void execute(final NPCs npcs, final ActionResult result, final Player p, final NPCFile file) {
		result.setNext(result.getCurrent()+1).setPerformNext(false);
	}

	@Override
	public void serializeValue(final ConfigurationNode node) {}

	@Override
	public Text getLine(final int index) {
		return Text.of(TextColors.GOLD, "Pause");
	}
}