package me.mrdaniel.npcs.commands.action.edit;

import me.mrdaniel.npcs.actions.Action;
import me.mrdaniel.npcs.actions.actions.ActionGoto;
import me.mrdaniel.npcs.catalogtypes.actiontype.ActionTypes;
import me.mrdaniel.npcs.commands.action.ActionCommand;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class CommandSetGoto extends ActionCommand {

	public CommandSetGoto() {
		super(ActionTypes.GOTO);
	}

	@Override
	public void execute(final Player p, final Action a, final CommandContext args) throws CommandException {
		((ActionGoto) a).setNext(args.<Integer>getOne("goto").get());
	}

	public CommandSpec build() {
		return CommandSpec.builder()
				.description(Text.of(TextColors.GOLD, "NPCs | Set Goto"))
				.permission("npc.action.edit.goto")
				.arguments(GenericArguments.integer(Text.of("goto")))
				.executor(this)
				.build();
	}
}
