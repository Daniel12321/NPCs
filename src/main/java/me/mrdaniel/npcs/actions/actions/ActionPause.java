package me.mrdaniel.npcs.actions.actions;

import me.mrdaniel.npcs.actions.Action;
import me.mrdaniel.npcs.catalogtypes.actiontype.ActionTypes;
import me.mrdaniel.npcs.io.INPCData;
import me.mrdaniel.npcs.managers.ActionResult;
import ninja.leaping.configurate.ConfigurationNode;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class ActionPause extends Action {

	public ActionPause(ConfigurationNode node) {
		this();
	}
	
	public ActionPause() {
		super(ActionTypes.PAUSE);
	}

	@Override
	public void execute(Player p, INPCData data, ActionResult result) {
		result.setNextAction(result.getCurrentAction()+1).setPerformNextAction(false);
	}

	@Override
	public void serializeValue(ConfigurationNode node) {}

	@Override
	public Text getLine(int index) {
		return Text.of(TextColors.GOLD, "Pause");
	}
}
