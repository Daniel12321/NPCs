package me.mrdaniel.npcs.commands.edit;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.commands.NPCCommand;
import me.mrdaniel.npcs.event.NPCEvent;
import me.mrdaniel.npcs.utils.TextUtils;

public class CommandGlow extends NPCCommand {

	public CommandGlow(@Nonnull final NPCs npcs) {
		super(npcs);
	}

	@Override
	public void execute(final Player player, final Living npc, final CommandContext args) throws CommandException {
		if (!npc.supports(Keys.GLOWING)) throw new CommandException(Text.of(TextColors.RED, "You can only use this on NPC's that can glow."));
		if (super.getGame().getEventManager().post(new NPCEvent.Edit(super.getContainer(), player, npc))) {
			throw new CommandException(Text.of(TextColors.RED, "Could not edit NPC: Event was cancelled!"));
		}
		boolean value = args.<Boolean>getOne("value").orElse(!npc.get(Keys.GLOWING).orElse(false));
		npc.offer(Keys.GLOWING, value);

		player.sendMessage(TextUtils.getMessage(value ? "The selected NPC is now glowing." : "The selected NPC is no longer glowing."));
	}
}