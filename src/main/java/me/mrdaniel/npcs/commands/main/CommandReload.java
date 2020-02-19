package me.mrdaniel.npcs.commands.main;

import me.mrdaniel.npcs.commands.NPCCommand;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.npcs.NPCs;

public class CommandReload implements CommandExecutor {

	@Override
	public CommandResult execute(final CommandSource src, final CommandContext args) throws CommandException {
		NPCs.getInstance().onReload(null);
		src.sendMessage(Text.of(TextColors.GOLD, "NPCs reloaded successfully!"));
		return CommandResult.success();
	}

	public CommandSpec build() {
		return CommandSpec.builder()
				.description(Text.of(TextColors.GOLD, "NPCs | "))
				.permission("npc.")
				.executor(this)
				.build();
	}
}
