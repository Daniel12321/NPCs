package me.mrdaniel.npcs.commands;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;

import me.mrdaniel.npcs.NPCs;

public class CommandDeselect extends PlayerCommand {

	public CommandDeselect(@Nonnull final NPCs npcs) {
		super(npcs);
	}

	@Override
	public void execute(final Player player, final CommandContext args) throws CommandException {
		super.getNPCs().getNPCManager().deselect(player.getUniqueId());
	}
}