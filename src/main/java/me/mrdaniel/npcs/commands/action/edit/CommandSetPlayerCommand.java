package me.mrdaniel.npcs.commands.action.edit;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.catalogtypes.actions.ActionTypes;
import me.mrdaniel.npcs.commands.ActionCommand;
import me.mrdaniel.npcs.data.npc.actions.Action;
import me.mrdaniel.npcs.data.npc.actions.ActionPlayerCommand;
import me.mrdaniel.npcs.exceptions.ActionException;

public class CommandSetPlayerCommand extends ActionCommand {

	public CommandSetPlayerCommand(@Nonnull final NPCs npcs) {
		super(npcs, ActionTypes.PLAYERCMD);
	}

	@Override
	public void execute(final Player p, final Action a, final CommandContext args) throws ActionException {
		ActionPlayerCommand ac = (ActionPlayerCommand) a;
		ac.setCommand(args.<String>getOne("command").get());
	}
}