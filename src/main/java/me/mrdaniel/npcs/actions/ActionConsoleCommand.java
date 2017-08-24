package me.mrdaniel.npcs.actions;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.catalogtypes.actiontype.ActionTypes;
import me.mrdaniel.npcs.io.NPCFile;
import me.mrdaniel.npcs.managers.ActionResult;
import me.mrdaniel.npcs.managers.PlaceholderManager;
import ninja.leaping.configurate.ConfigurationNode;

public class ActionConsoleCommand extends Action {

	private String command;

	public ActionConsoleCommand(@Nonnull final ConfigurationNode node) { this(node.getNode("Command").getString("")); }
	public ActionConsoleCommand(@Nonnull final String command) {
		super(ActionTypes.CONSOLECMD);

		this.command = command;
	}

	public void setCommand(@Nonnull final String command) { this.command = command; }

	@Override
	public void execute(final Player p, final NPCFile file, final ActionResult result) {
		NPCs.getInstance().getGame().getCommandManager().process(NPCs.getInstance().getGame().getServer().getConsole(), PlaceholderManager.getInstance().formatCommand(p, this.command));
		result.setNextAction(result.getCurrentAction()+1);
	}

	@Override
	public void serializeValue(final ConfigurationNode node) {
		node.getNode("Command").setValue(this.command);
	}

	@Override
	public Text getLine(final int index) {
		return Text.builder().append(Text.of(TextColors.GOLD, "Console Command: "),
				Text.builder().append(Text.of(TextColors.AQUA, this.command))
				.onHover(TextActions.showText(Text.of(TextColors.YELLOW, "Change")))
				.onClick(TextActions.suggestCommand("/npc action edit " + index + " consolecmd <command...>"))
				.build()).build();
	}
}