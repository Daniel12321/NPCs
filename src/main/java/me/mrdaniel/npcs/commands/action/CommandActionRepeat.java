package me.mrdaniel.npcs.commands.action;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.npcs.catalogtypes.menupagetype.PageTypes;
import me.mrdaniel.npcs.commands.NPCCommand;
import me.mrdaniel.npcs.events.NPCEvent;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;

public class CommandActionRepeat extends NPCCommand {

	public CommandActionRepeat() {
		super(PageTypes.ACTIONS);
	}

	@Override
	public void execute(final Player p, final NPCAble npc, final CommandContext args) throws CommandException {
		if (new NPCEvent.Edit(p, npc).post()) {
			throw new CommandException(Text.of(TextColors.RED, "Could not edit NPC: Event was cancelled!"));
		}

		boolean repeat = args.<Boolean>getOne("repeat").orElse(!npc.getNPCFile().getRepeatActions());

		npc.getNPCFile().setRepeatActions(repeat).save();
	}
}