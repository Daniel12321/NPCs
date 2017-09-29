package me.mrdaniel.npcs.commands.action;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.npcs.catalogtypes.menupagetype.PageTypes;
import me.mrdaniel.npcs.commands.NPCCommand;
import me.mrdaniel.npcs.events.NPCEditEvent;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;

public class CommandActionRemove extends NPCCommand {

	public CommandActionRemove() {
		super(PageTypes.ACTIONS);
	}

	@Override
	public void execute(final Player p, final NPCAble npc, final CommandContext args) throws CommandException {
		if (new NPCEditEvent(p, npc).post()) {
			throw new CommandException(Text.of(TextColors.RED, "Could not edit NPC: Event was cancelled!"));
		}

		int id = args.<Integer>getOne("number").get();
		if (id < 0 || id >= npc.getNPCFile().getActions().size()) { throw new CommandException(Text.of(TextColors.RED, "No Action with this number exists.")); }

		npc.getNPCFile().getActions().remove(id);
		npc.getNPCFile().writeActions().save();
	}
}