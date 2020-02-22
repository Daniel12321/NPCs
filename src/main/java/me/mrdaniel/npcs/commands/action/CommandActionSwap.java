package me.mrdaniel.npcs.commands.action;

import me.mrdaniel.npcs.catalogtypes.menupagetype.PageTypes;
import me.mrdaniel.npcs.commands.NPCCommand;
import me.mrdaniel.npcs.events.NPCEditEvent;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.io.INPCData;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class CommandActionSwap extends NPCCommand {

	public CommandActionSwap() {
		super(PageTypes.ACTIONS);
	}

	@Override
	public void execute(Player p, NPCAble npc, CommandContext args) throws CommandException {
		if (new NPCEditEvent(p, npc).post()) {
			throw new CommandException(Text.of(TextColors.RED, "Could not edit NPC: Event was cancelled!"));
		}

		INPCData file = npc.getNPCData();
		int first = args.<Integer>getOne("first").get();
		int second = args.<Integer>getOne("second").get();
		int size = file.getActions().size();

		if (first < 0 || second < 0 || first >= size || second >= size) {
			return;
		}

		file.getActions().set(first, file.getActions().set(second, file.getActions().get(first)));
		file.writeActions().save();
	}
}
