package me.mrdaniel.npcs.commands.action;

import me.mrdaniel.npcs.actions.Action;
import me.mrdaniel.npcs.actions.ActionSet;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class CommandActionSwap extends ActionSetCommand {

	@Override
	public void execute(Player player, ActionSet actions, CommandContext args) throws CommandException {
		int first = args.<Integer>getOne("first").get();
		int second = args.<Integer>getOne("second").get();
		int size = actions.getAllActions().size();

		if (first < 0 || second < 0 || first >= size || second >= size || first == second) {
			return;
		}

		Action temp = actions.getAction(first);
		actions.setAction(first, actions.getAction(second));
		actions.setAction(second, temp);
	}

	public CommandSpec build() {
		return CommandSpec.builder()
				.description(Text.of(TextColors.GOLD, "NPCs | Swap Actions"))
				.permission("npc.action.swap")
				.arguments(GenericArguments.integer(Text.of("first")), GenericArguments.integer(Text.of("second")))
				.executor(this)
				.build();
	}
}
