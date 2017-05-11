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

public class CommandActionRemove extends NPCCommand {

	public CommandActionRemove(@Nonnull final NPCs npcs) {
		super(npcs, PageTypes.ACTIONS);
	}

	@Override
	public void execute(final Player p, final NPCMenu menu, final CommandContext args) throws CommandException {
		if (super.getGame().getEventManager().post(new NPCEvent.Edit(super.getContainer(), p, menu.getNPC(), menu.getFile()))) {
			throw new CommandException(Text.of(TextColors.RED, "Could not edit NPC: Event was cancelled!"));
		}

		int id = args.<Integer>getOne("number").get();
		if (id < 0 || id >= menu.getFile().getActions().size()) { throw new CommandException(Text.of(TextColors.RED, "No Action with this number exists.")); }

		menu.getFile().getActions().remove(id);
		menu.getFile().writeActions();
		menu.getFile().save();
	}
}