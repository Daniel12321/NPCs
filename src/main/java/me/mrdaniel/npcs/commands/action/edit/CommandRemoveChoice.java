package me.mrdaniel.npcs.commands.action.edit;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.catalogtypes.actions.ActionTypes;
import me.mrdaniel.npcs.commands.ActionCommand;
import me.mrdaniel.npcs.data.npc.actions.Action;
import me.mrdaniel.npcs.data.npc.actions.ActionChoices;
import me.mrdaniel.npcs.exceptions.ActionException;

public class CommandRemoveChoice extends ActionCommand {

	public CommandRemoveChoice(@Nonnull final NPCs npcs) {
		super(npcs, ActionTypes.CHOICES);
	}

	@Override
	public void execute(final Player p, final Action a, final CommandContext args) throws ActionException {
		ActionChoices ac = (ActionChoices) a;

		if (ac.getChoices().size() <= 2) { throw new ActionException("There must always be more than 1 choice!"); }
		ac.getChoices().remove(args.<String>getOne("name").get());
	}
}