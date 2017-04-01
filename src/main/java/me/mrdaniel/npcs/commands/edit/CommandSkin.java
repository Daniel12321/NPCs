package me.mrdaniel.npcs.commands.edit;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.Human;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.commands.NPCCommand;
import me.mrdaniel.npcs.event.NPCEvent;
import me.mrdaniel.npcs.utils.TextUtils;

public class CommandSkin extends NPCCommand {

	public CommandSkin(@Nonnull final NPCs npcs) {
		super(npcs);
	}

	@Override
	public void execute(final Player player, final Living npc, final CommandContext args) throws CommandException {
		if (!(npc instanceof Human)) { throw new CommandException(Text.of(TextColors.RED, "You can only give human NPC's a skin.")); }
		String name = args.<String>getOne("name").get();

		if (super.getGame().getEventManager().post(new NPCEvent.Edit(super.getContainer(), player, npc))) {
			throw new CommandException(Text.of(TextColors.RED, "Could not edit NPC: Event was cancelled!"));
		}
		super.getServer().getGameProfileManager().get(name).thenAccept(gp -> {
			npc.offer(Keys.SKIN_UNIQUE_ID, gp.getUniqueId());
			player.sendMessage(TextUtils.getMessage("You successfully gave the selected NPC a skin."));
		});
	}
}