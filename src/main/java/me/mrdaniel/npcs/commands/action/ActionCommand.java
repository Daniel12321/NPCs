package me.mrdaniel.npcs.commands.action;

import me.mrdaniel.npcs.actions.Action;
import me.mrdaniel.npcs.actions.ActionSet;
import me.mrdaniel.npcs.catalogtypes.actiontype.ActionType;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public abstract class ActionCommand extends ActionSetCommand {

	protected final ActionType type;

	public ActionCommand(ActionType type) {
		this.type = type;
	}

	@Override
	public void execute(Player player, ActionSet actions, CommandContext args) throws CommandException {
		Action action = actions.getAction(args.<Integer>getOne("index").get());
		if (action.getType() != this.type) {
			throw new CommandException(Text.of(TextColors.RED, "This action does not match the required action for this command!"));
		}

		this.execute(player, action, args);
		actions.setActionsModified(true);
	}

	public abstract void execute(Player player, Action action, CommandContext args) throws CommandException;
}
