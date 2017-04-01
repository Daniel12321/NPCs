package me.mrdaniel.npcs.commands;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.utils.TextUtils;

public class CommandDeselect extends PlayerCommand {

	public CommandDeselect(@Nonnull final NPCs npcs) {
		super(npcs);
	}

	@Override
	public void execute(final Player player, final CommandContext args) throws CommandException {
		super.getNPCs().getNPCManager().deselect(player.getUniqueId());

		player.sendMessage(TextUtils.getMessage("No more NPC is selected."));
	}
}