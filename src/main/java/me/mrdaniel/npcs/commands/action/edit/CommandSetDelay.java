package me.mrdaniel.npcs.commands.action.edit;

import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;

import me.mrdaniel.npcs.actions.Action;
import me.mrdaniel.npcs.actions.ActionDelay;
import me.mrdaniel.npcs.catalogtypes.actiontype.ActionTypes;
import me.mrdaniel.npcs.commands.ActionCommand;
import me.mrdaniel.npcs.exceptions.ActionException;

public class CommandSetDelay extends ActionCommand {

	public CommandSetDelay() {
		super(ActionTypes.DELAY);
	}

	@Override
	public void execute(final Player p, final Action a, final CommandContext args) throws ActionException {
		((ActionDelay) a).setTicks(args.<Integer>getOne("ticks").get());
	}
}