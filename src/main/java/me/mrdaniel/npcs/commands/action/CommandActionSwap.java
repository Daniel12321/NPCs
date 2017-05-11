package me.mrdaniel.npcs.commands.action;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.catalogtypes.menupages.PageTypes;
import me.mrdaniel.npcs.commands.NPCCommand;
import me.mrdaniel.npcs.events.NPCEvent;
import me.mrdaniel.npcs.managers.menu.NPCMenu;

public class CommandActionSwap extends NPCCommand {

	public CommandActionSwap(@Nonnull final NPCs npcs) {
		super(npcs, PageTypes.ACTIONS);
	}

	@Override
	public void execute(final Player p, final NPCMenu menu, final CommandContext args) throws CommandException {
		if (super.getGame().getEventManager().post(new NPCEvent.Edit(super.getContainer(), p, menu.getNPC(), menu.getFile()))) {
			throw new CommandException(Text.of(TextColors.RED, "Could not edit NPC: Event was cancelled!"));
		}

		int first = args.<Integer>getOne("first").get();
		int second = args.<Integer>getOne("second").get();
		int size = menu.getFile().getActions().size();

		if (first < 0 || second < 0 || first >= size || second >= size) { throw new CommandException(Text.of(TextColors.RED, "No Action was found for one of the two numbers.")); }

		menu.getFile().getActions().set(first, menu.getFile().getActions().set(second, menu.getFile().getActions().get(first)));
		menu.getFile().writeActions();
		menu.getFile().save();
	}
}