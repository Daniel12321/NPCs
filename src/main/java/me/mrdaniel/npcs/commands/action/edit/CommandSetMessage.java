package me.mrdaniel.npcs.commands.action.edit;

import me.mrdaniel.npcs.actions.Action;
import me.mrdaniel.npcs.actions.actions.ActionMessage;
import me.mrdaniel.npcs.catalogtypes.actiontype.ActionTypes;
import me.mrdaniel.npcs.commands.action.ActionCommand;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class CommandSetMessage extends ActionCommand {

	public CommandSetMessage() {
		super(ActionTypes.MESSAGE);
	}

	@Override
	public void execute(final Player p, final Action a, final CommandContext args) throws CommandException {
		((ActionMessage) a).setMessage(args.<String>getOne("message").get());
	}

	public CommandSpec build() {
		return CommandSpec.builder()
				.description(Text.of(TextColors.GOLD, "NPCs | Set Message"))
				.permission("npc.action.edit.message")
				.arguments(GenericArguments.remainingRawJoinedStrings(Text.of("message")))
				.executor(this)
				.build();
	}
}
