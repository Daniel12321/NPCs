package me.mrdaniel.npcs.commands.action;

import me.mrdaniel.npcs.catalogtypes.menupagetype.PageTypes;
import me.mrdaniel.npcs.commands.NPCCommand;
import me.mrdaniel.npcs.events.NPCEditEvent;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class CommandActionRepeat extends NPCCommand {

	public CommandActionRepeat() {
		super(PageTypes.ACTIONS);
	}

	@Override
	public void execute(Player p, NPCAble npc, CommandContext args) throws CommandException {
		if (new NPCEditEvent(p, npc).post()) {
			throw new CommandException(Text.of(TextColors.RED, "Could not edit NPC: Event was cancelled!"));
		}

		npc.getNPCFile().setRepeatActions(args.<Boolean>getOne("repeat").orElse(!npc.getNPCFile().getRepeatActions())).save();
	}
}
