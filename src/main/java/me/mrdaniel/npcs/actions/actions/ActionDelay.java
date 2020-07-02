package me.mrdaniel.npcs.actions.actions;

import com.google.common.collect.Lists;
import me.mrdaniel.npcs.actions.Action;
import me.mrdaniel.npcs.catalogtypes.actiontype.ActionTypes;
import me.mrdaniel.npcs.io.INPCData;
import me.mrdaniel.npcs.managers.actions.ActionResult;
import ninja.leaping.configurate.ConfigurationNode;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.List;

public class ActionDelay extends Action {

	private int ticks;

	public ActionDelay(ConfigurationNode node) {
		this(node.getNode("ticks").getInt(20));
	}
	
	public ActionDelay(int ticks) {
		super(ActionTypes.DELAY);

		this.ticks = ticks;
	}

	public void setTicks(int ticks) {
		this.ticks = ticks;
	}

	@Override
	public void execute(Player p, INPCData data, ActionResult result) {
		result.setNextAction(result.getCurrentAction()+1).setWaitTicks(this.ticks);
	}

	@Override
	public void serializeValue(ConfigurationNode node) {
		node.getNode("ticks").setValue(this.ticks);
	}

	@Override
	public List<Text> getLines(int index) {
		return Lists.newArrayList(Text.of(TextColors.GOLD, "Delay: ", TextColors.AQUA, this.ticks, " ticks"));
	}
}
