package me.mrdaniel.npcs.commands.action.edit;

import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;

import me.mrdaniel.npcs.actions.Action;
import me.mrdaniel.npcs.actions.ActionGoto;
import me.mrdaniel.npcs.catalogtypes.actions.ActionTypes;
import me.mrdaniel.npcs.commands.ActionCommand;
import me.mrdaniel.npcs.exceptions.ActionException;

public class CommandSetGoto extends ActionCommand {

	public CommandSetGoto() {
		super(ActionTypes.GOTO);
	}

	@Override
	public void execute(final Player p, final Action a, final CommandContext args) throws ActionException {
		((ActionGoto) a).setNext(args.<Integer>getOne("goto").get());
	}
}