package me.mrdaniel.npcs.commands;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.npcs.NPCObject;
import me.mrdaniel.npcs.NPCs;

public abstract class PlayerCommand extends NPCObject implements CommandExecutor {

	public PlayerCommand(@Nonnull final NPCs npcs) {
		super(npcs);
	}

	@Override
	public CommandResult execute(final CommandSource src, final CommandContext args) throws CommandException {
		if (!(src instanceof Player)) { throw new CommandException(Text.of(TextColors.RED, "This command is for players only!")); }
		this.execute((Player)src, args);
		return CommandResult.success();
	}

	public abstract void execute(@Nonnull final Player p, @Nonnull final CommandContext args) throws CommandException;
}