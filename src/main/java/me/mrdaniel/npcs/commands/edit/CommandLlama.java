package me.mrdaniel.npcs.commands.edit;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.LlamaVariant;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.animal.Llama;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.commands.NPCCommand;
import me.mrdaniel.npcs.event.NPCEvent;
import me.mrdaniel.npcs.utils.TextUtils;

public class CommandLlama extends NPCCommand {

	public CommandLlama(@Nonnull final NPCs npcs) {
		super(npcs);
	}

	@Override
	public void execute(final Player player, final Living npc, final CommandContext args) throws CommandException {
		if (!(npc instanceof Llama)) throw new CommandException(Text.of(TextColors.RED, "You can only use this on llama NPC's."));
		if (super.getGame().getEventManager().post(new NPCEvent.Edit(super.getContainer(), player, npc))) {
			throw new CommandException(Text.of(TextColors.RED, "Could not edit NPC: Event was cancelled!"));
		}
		npc.offer(Keys.LLAMA_VARIANT, args.<LlamaVariant>getOne("type").get());

		player.sendMessage(TextUtils.getMessage("You successfully changed the selected NPC's llama type."));
	}
}