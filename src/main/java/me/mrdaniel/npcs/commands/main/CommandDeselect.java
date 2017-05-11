package me.mrdaniel.npcs.commands.main;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.commands.PlayerCommand;

public class CommandDeselect extends PlayerCommand {

	public CommandDeselect(@Nonnull final NPCs npcs) {
		super(npcs);
	}

	@Override
	public void execute(final Player p, final CommandContext args) throws CommandException {
		super.getNPCs().getMenuManager().deselect(p.getUniqueId());
	}
}