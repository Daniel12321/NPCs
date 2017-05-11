package me.mrdaniel.npcs.commands.edit;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.catalogtypes.menupages.PageTypes;
import me.mrdaniel.npcs.commands.NPCCommand;
import me.mrdaniel.npcs.events.NPCEvent;
import me.mrdaniel.npcs.managers.menu.NPCMenu;

public class CommandSkin extends NPCCommand {

	public CommandSkin(@Nonnull final NPCs npcs) {
		super(npcs, PageTypes.MAIN);
	}

	@Override
	public void execute(final Player p, final NPCMenu menu, final CommandContext args) throws CommandException {
		if (!menu.getNPC().supports(Keys.SKIN_UNIQUE_ID)) { throw new CommandException(Text.of(TextColors.RED, "You can only give human NPC's a skin.")); }
		if (super.getGame().getEventManager().post(new NPCEvent.Edit(super.getContainer(), p, menu.getNPC(), menu.getFile()))) {
			throw new CommandException(Text.of(TextColors.RED, "Could not edit NPC: Event was cancelled!"));
		}

		String name = args.<String>getOne("name").get();
		menu.getFile().setSkinName(name);

		super.getServer().getGameProfileManager().get(name).thenAccept(gp -> Task.builder().delayTicks(0).execute(() -> {
			menu.getNPC().offer(Keys.SKIN_UNIQUE_ID, gp.getUniqueId());
			menu.getFile().setSkinUUID(gp.getUniqueId());
			menu.getFile().save();
		}).submit(super.getNPCs()));
	}
}