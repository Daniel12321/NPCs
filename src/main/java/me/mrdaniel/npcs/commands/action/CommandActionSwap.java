package me.mrdaniel.npcs.commands.action;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.npcs.catalogtypes.menupages.PageTypes;
import me.mrdaniel.npcs.commands.NPCCommand;
import me.mrdaniel.npcs.events.NPCEvent;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.io.NPCFile;

public class CommandActionSwap extends NPCCommand {

	public CommandActionSwap() {
		super(PageTypes.ACTIONS);
	}

	@Override
	public void execute(final Player p, final NPCAble npc, final CommandContext args) throws CommandException {
		if (new NPCEvent.Edit(p, npc).post()) {
			throw new CommandException(Text.of(TextColors.RED, "Could not edit NPC: Event was cancelled!"));
		}

		NPCFile file = npc.getNPCFile();
		int first = args.<Integer>getOne("first").get();
		int second = args.<Integer>getOne("second").get();
		int size = file.getActions().size();

		if (first < 0 || second < 0 || first >= size || second >= size) { throw new CommandException(Text.of(TextColors.RED, "No Action was found for one of the two numbers.")); }

		file.getActions().set(first, file.getActions().set(second, file.getActions().get(first)));
		file.writeActions().save();
	}
}