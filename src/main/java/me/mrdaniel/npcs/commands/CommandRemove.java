package me.mrdaniel.npcs.commands;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.event.NPCEvent;

public class CommandRemove extends NPCCommand {

	public CommandRemove(@Nonnull final NPCs npcs) {
		super(npcs);
	}

	@Override
	public void execute(final Player player, final Living npc, final CommandContext args) throws CommandException {
		if (super.getGame().getEventManager().post(new NPCEvent.Remove(super.getContainer(), player, npc))) {
			throw new CommandException(Text.of(TextColors.RED, "Could not remove NPC: Event was cancelled!"));
		}
		super.getNPCs().getNPCManager().deselect(npc);
		npc.remove();
	}
}