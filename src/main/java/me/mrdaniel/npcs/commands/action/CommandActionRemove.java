package me.mrdaniel.npcs.commands.action;

import me.mrdaniel.npcs.actions.ActionSet;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class CommandActionRemove extends ActionSetCommand {

	@Override
	public void execute(Player player, ActionSet actions, CommandContext args) throws CommandException {
		int id = args.<Integer>getOne("number").get();
		if (id < 0 || id >= actions.getAllActions().size()) {
			throw new CommandException(Text.of(TextColors.RED, "No Action with this number exists."));
		}

		actions.removeAction(id);
	}

	public CommandSpec build() {
		return CommandSpec.builder()
				.description(Text.of(TextColors.GOLD, "NPCs | Remove Action"))
				.permission("npc.action.remove")
				.arguments(GenericArguments.integer(Text.of("number")))
				.executor(this)
				.build();
	}
}
