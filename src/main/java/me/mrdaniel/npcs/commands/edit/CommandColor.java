package me.mrdaniel.npcs.commands.edit;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.HorseColor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.catalogtypes.menupages.PageTypes;
import me.mrdaniel.npcs.commands.NPCCommand;
import me.mrdaniel.npcs.events.NPCEvent;
import me.mrdaniel.npcs.managers.menu.NPCMenu;

public class CommandColor extends NPCCommand {

	public CommandColor(@Nonnull final NPCs npcs) {
		super(npcs, PageTypes.MAIN);
	}

	@Override
	public void execute(final Player p, final NPCMenu menu, final CommandContext args) throws CommandException {
		if (!menu.getNPC().supports(Keys.HORSE_COLOR)) throw new CommandException(Text.of(TextColors.RED, "You can only use this on horse NPC's."));
		if (super.getGame().getEventManager().post(new NPCEvent.Edit(super.getContainer(), p, menu.getNPC(), menu.getFile()))) {
			throw new CommandException(Text.of(TextColors.RED, "Could not edit NPC: Event was cancelled!"));
		}

		HorseColor color = args.<HorseColor>getOne("color").get();

		menu.getNPC().offer(Keys.HORSE_COLOR, color);
		menu.getFile().setHorseColor(color);
		menu.getFile().save();
	}
}