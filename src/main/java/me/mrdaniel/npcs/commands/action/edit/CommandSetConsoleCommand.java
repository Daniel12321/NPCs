package me.mrdaniel.npcs.commands.action.edit;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.catalogtypes.actions.ActionTypes;
import me.mrdaniel.npcs.commands.ActionCommand;
import me.mrdaniel.npcs.data.npc.actions.Action;
import me.mrdaniel.npcs.data.npc.actions.ActionConsoleCommand;
import me.mrdaniel.npcs.exceptions.ActionException;

public class CommandSetConsoleCommand extends ActionCommand {

	public CommandSetConsoleCommand(@Nonnull final NPCs npcs) {
		super(npcs, ActionTypes.CONSOLECMD);
	}

	@Override
	public void execute(final Player p, final Action a, final CommandContext args) throws ActionException {
		ActionConsoleCommand ac = (ActionConsoleCommand) a;
		ac.setCommand(args.<String>getOne("command").get());
	}
}