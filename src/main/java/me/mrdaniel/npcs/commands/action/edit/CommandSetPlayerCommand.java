package me.mrdaniel.npcs.commands.action.edit;

import me.mrdaniel.npcs.actions.Action;
import me.mrdaniel.npcs.actions.ActionPlayerCommand;
import me.mrdaniel.npcs.catalogtypes.actiontype.ActionTypes;
import me.mrdaniel.npcs.commands.ActionCommand;
import me.mrdaniel.npcs.exceptions.ActionException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;

public class CommandSetPlayerCommand extends ActionCommand {

	public CommandSetPlayerCommand() {
		super(ActionTypes.PLAYERCMD);
	}

	@Override
	public void execute(final Player p, final Action a, final CommandContext args) throws ActionException {
		((ActionPlayerCommand) a).setCommand(args.<String>getOne("command").get());
	}
}
