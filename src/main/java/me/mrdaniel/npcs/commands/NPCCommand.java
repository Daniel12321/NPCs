package me.mrdaniel.npcs.commands;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.npcs.NPCs;

public abstract class NPCCommand extends PlayerCommand {

	public NPCCommand(@Nonnull final NPCs npcs) {
		super(npcs);
	}

	@Override
	public void execute(final Player player, final CommandContext args) throws CommandException {
		Living npc = super.getNPCs().getNPCManager().getSelected(player.getUniqueId()).orElseThrow(() -> new CommandException(Text.of(TextColors.RED, "No NPC was selected.")));
		this.execute(player, npc, args);
	}

	public abstract void execute(Player player, Living npc, CommandContext args) throws CommandException;
}