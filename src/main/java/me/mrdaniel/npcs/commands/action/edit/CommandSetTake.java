package me.mrdaniel.npcs.commands.action.edit;

import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;

import me.mrdaniel.npcs.actions.Action;
import me.mrdaniel.npcs.actions.ActionCondition;
import me.mrdaniel.npcs.catalogtypes.actiontype.ActionTypes;
import me.mrdaniel.npcs.commands.ActionCommand;
import me.mrdaniel.npcs.exceptions.ActionException;

public class CommandSetTake extends ActionCommand {

	public CommandSetTake() {
		super(ActionTypes.CONDITION);
	}

	@Override
	public void execute(final Player p, final Action a, final CommandContext args) throws ActionException {
		((ActionCondition) a).setTake(args.<Boolean>getOne("take").get());
	}
}