package me.mrdaniel.npcs.commands.action.edit;

import me.mrdaniel.npcs.actions.Action;
import me.mrdaniel.npcs.actions.actions.ActionChoices;
import me.mrdaniel.npcs.catalogtypes.actiontype.ActionTypes;
import me.mrdaniel.npcs.commands.action.ActionCommand;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class CommandRemoveChoice extends ActionCommand {

	public CommandRemoveChoice() {
		super(ActionTypes.CHOICES);
	}

	@Override
	public void execute(final Player p, final Action a, final CommandContext args) throws CommandException {
		ActionChoices ac = (ActionChoices) a;

		if (ac.getChoices().size() <= 2) {
			throw new CommandException(Text.of(TextColors.RED, "There must always be more than 1 choice!"));
		}
		ac.getChoices().remove(args.<String>getOne("name").get());
	}

	public CommandSpec build() {
		return CommandSpec.builder()
				.description(Text.of(TextColors.GOLD, "NPCs | Remove Choice"))
				.permission("npc.action.edit.choice.remove")
				.arguments(GenericArguments.string(Text.of("name")))
				.executor(this)
				.build();
	}
}
