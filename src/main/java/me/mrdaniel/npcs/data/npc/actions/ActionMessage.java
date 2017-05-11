package me.mrdaniel.npcs.data.npc.actions;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.catalogtypes.actions.ActionTypes;
import me.mrdaniel.npcs.io.NPCFile;
import me.mrdaniel.npcs.managers.ActionResult;
import me.mrdaniel.npcs.utils.TextUtils;
import ninja.leaping.configurate.ConfigurationNode;

public class ActionMessage extends Action {

	private String message;

	public ActionMessage(@Nonnull final ConfigurationNode node) { this(node.getNode("Message").getString("")); }

	public ActionMessage(@Nonnull final String message) {
		super(ActionTypes.MESSAGE);

		this.message = message;
	}

	public void setMessage(@Nonnull final String message) { this.message = message; }

	@Override
	public void execute(final NPCs npcs, final ActionResult result, final Player p, final NPCFile file) {
		p.sendMessage(npcs.getPlaceHolderManager().formatNPCMessage(p, this.message, TextUtils.toString(file.getName().orElse(Text.EMPTY))));
		result.setNext(result.getCurrent()+1);
	}

	@Override
	public void serializeValue(final ConfigurationNode node) {
		node.getNode("Message").setValue(this.message);
	}

	@Override
	public Text getLine(final int index) {
		return Text.builder().append(Text.of(TextColors.GOLD, "Message: "),
				Text.builder().append(Text.of(TextColors.AQUA, this.message))
				.onHover(TextActions.showText(Text.of(TextColors.YELLOW, "Change")))
				.onClick(TextActions.suggestCommand("/npc action edit " + index + " message <message...>"))
				.build()).build();
	}
}