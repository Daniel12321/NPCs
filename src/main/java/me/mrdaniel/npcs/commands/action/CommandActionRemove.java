package me.mrdaniel.npcs.commands.action;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.commands.NPCCommand;
import me.mrdaniel.npcs.data.npc.NPCData;
import me.mrdaniel.npcs.events.NPCEvent;

public class CommandActionRemove extends NPCCommand {

	public CommandActionRemove(@Nonnull final NPCs npcs) {
		super(npcs);
	}

	@Override
	public void execute(final Player player, final Living npc, final CommandContext args) throws CommandException {
		if (super.getGame().getEventManager().post(new NPCEvent.Edit(super.getContainer(), player, npc))) {
			throw new CommandException(Text.of(TextColors.RED, "Could not edit NPC: Event was cancelled!"));
		}
		NPCData data = npc.get(NPCData.class).get();

		int i = args.<Integer>getOne("number").get() - 1;
		if (i < 0 || i >= data.getActions().getActions().size()) { throw new CommandException(Text.of(TextColors.RED, "No Action with this number exists.")); }

		data.getActions().getActions().remove(i);
		npc.offer(data);
	}
}