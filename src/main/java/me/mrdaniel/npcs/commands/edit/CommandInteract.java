package me.mrdaniel.npcs.commands.edit;

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
import me.mrdaniel.npcs.event.NPCEvent;
import me.mrdaniel.npcs.utils.TextUtils;

public class CommandInteract extends NPCCommand {

	public CommandInteract(@Nonnull final NPCs npcs) {
		super(npcs);
	}

	@Override
	public void execute(final Player player, final Living npc, final CommandContext args) throws CommandException {
		if (super.getGame().getEventManager().post(new NPCEvent.Edit(super.getContainer(), player, npc))) {
			throw new CommandException(Text.of(TextColors.RED, "Could not edit NPC: Event was cancelled!"));
		}
		NPCData data = npc.get(NPCData.class).get();
		data.setInteract(args.<Boolean>getOne("value").orElse(!data.canInteract()));
		npc.offer(data);

		player.sendMessage(TextUtils.getMessage(data.canInteract() ? "The selected NPC can now be interacted with." : "The selected NPC will no longer be interacted with."));
	}
}