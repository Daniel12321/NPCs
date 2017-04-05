package me.mrdaniel.npcs.commands.action;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.player.Player;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.commands.NPCCommand;
import me.mrdaniel.npcs.data.action.Action;
import me.mrdaniel.npcs.data.action.ActionType;
import me.mrdaniel.npcs.data.npc.NPCData;

public class CommandAddMessageAction extends NPCCommand {

	public CommandAddMessageAction(@Nonnull final NPCs npcs) {
		super(npcs);
	}

	@Override
	public void execute(final Player player, final Living npc, final CommandContext args) throws CommandException {
		NPCData data = npc.get(NPCData.class).get();
		data.getActions().add(new Action(ActionType.MESSAGE, args.<String>getOne("message").get()));
		npc.offer(data);
	}
}