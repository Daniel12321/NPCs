package me.mrdaniel.npcs.commands;

import me.mrdaniel.npcs.actions.Action;
import me.mrdaniel.npcs.catalogtypes.actiontype.ActionType;
import me.mrdaniel.npcs.catalogtypes.menupagetype.PageTypes;
import me.mrdaniel.npcs.exceptions.ActionException;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public abstract class ActionCommand extends NPCCommand {

	protected final ActionType type;

	public ActionCommand(ActionType type) {
		super(PageTypes.ACTIONS);

		this.type = type;
	}

	@Override
	public void execute(Player p, NPCAble npc, CommandContext args) throws CommandException {
		Action a = npc.getNPCFile().getActions().get(args.<Integer>getOne("index").get());
		if (a.getType() != this.type) {
			throw new CommandException(Text.of(TextColors.RED, "This action does not match the required action for this command!"));
		}

		try {
			this.execute(p, a, args); npc.getNPCFile().writeActions().save();
		} catch (final ActionException exc) {
			throw new CommandException(Text.of(TextColors.RED, "Failed to edit action!"), exc);
		}
	}

	public abstract void execute(Player p, Action a, CommandContext args) throws ActionException;
}
