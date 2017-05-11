package me.mrdaniel.npcs.commands.main;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;

import me.mrdaniel.npcs.NPCObject;
import me.mrdaniel.npcs.NPCs;

public class CommandList extends NPCObject implements CommandExecutor {

	public CommandList(@Nonnull final NPCs npcs) {
		super(npcs);
	}

	@Override
	public CommandResult execute(final CommandSource src, final CommandContext args) throws CommandException {
		
		
		
		
		return CommandResult.success();
	}
}