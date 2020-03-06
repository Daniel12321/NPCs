package me.mrdaniel.npcs.commands.action;

import me.mrdaniel.npcs.actions.ActionSet;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class CommandActionRepeat extends ActionSetCommand {

	@Override
	public void execute(Player player, ActionSet actions, CommandContext args) throws CommandException {
		boolean value = args.<Boolean>getOne("repeat").orElse(!actions.isRepeatActions());
		actions.setRepeatActions(value);
	}

	public CommandSpec build() {
		return CommandSpec.builder()
				.description(Text.of(TextColors.GOLD, "NPCs | Set Repeat Actions"))
				.permission("npc.action.repeat")
				.arguments(GenericArguments.optional(GenericArguments.bool(Text.of("repeat"))))
				.executor(this)
				.build();
	}
}
