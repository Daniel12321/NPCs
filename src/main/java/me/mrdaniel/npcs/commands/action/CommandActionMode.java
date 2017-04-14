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

public class CommandActionMode extends NPCCommand {

	public CommandActionMode(@Nonnull final NPCs npcs) {
		super(npcs);
	}

	@Override
	public void execute(final Player player, final Living npc, final CommandContext args) throws CommandException {
		if (super.getGame().getEventManager().post(new NPCEvent.Edit(super.getContainer(), player, npc))) {
			throw new CommandException(Text.of(TextColors.RED, "Could not edit NPC: Event was cancelled!"));
		}
		String mode = args.<String>getOne("mode").get();
		NPCData data = npc.get(NPCData.class).get();

		if (!data.setMode(mode)) { throw new CommandException(Text.of(TextColors.RED, "Invalid Mode.")); }

		npc.offer(data);
	}
}