package me.mrdaniel.npcs.commands.action.edit;

import me.mrdaniel.npcs.actions.Action;
import me.mrdaniel.npcs.actions.actions.ActionDelay;
import me.mrdaniel.npcs.catalogtypes.actiontype.ActionTypes;
import me.mrdaniel.npcs.commands.action.ActionCommand;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class CommandSetDelay extends ActionCommand {

	public CommandSetDelay() {
		super(ActionTypes.DELAY);
	}

	@Override
	public void execute(final Player p, final Action a, final CommandContext args) throws CommandException {
		((ActionDelay) a).setTicks(args.<Integer>getOne("ticks").get());
	}

	public CommandSpec build() {
		return CommandSpec.builder()
				.description(Text.of(TextColors.GOLD, "NPCs | Set Delay"))
				.permission("npc.action.edit.delay")
				.arguments(GenericArguments.integer(Text.of("ticks")))
				.executor(this)
				.build();
	}
}
