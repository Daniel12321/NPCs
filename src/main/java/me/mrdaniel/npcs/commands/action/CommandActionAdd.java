package me.mrdaniel.npcs.commands.action;

import com.google.common.collect.Maps;
import me.mrdaniel.npcs.actions.Action;
import me.mrdaniel.npcs.actions.ActionSet;
import me.mrdaniel.npcs.actions.actions.*;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.Map;

public abstract class CommandActionAdd extends ActionSetCommand {

	@Override
	public void execute(Player player, ActionSet actions, CommandContext args) throws CommandException {
		actions.addAction(this.create(player, args));
	}

	public abstract Action create(Player player, CommandContext args) throws CommandException;

	public static class PlayerCommand extends CommandActionAdd {
		@Override public Action create(Player player, CommandContext args) { return new ActionPlayerCommand(args.<String>getOne("command").get()); }

		public CommandSpec build() {
			return CommandSpec.builder()
					.description(Text.of(TextColors.GOLD, "NPCs | Add Player Command Action"))
					.permission("npc.action.command.player")
					.arguments(GenericArguments.remainingRawJoinedStrings(Text.of("command")))
					.executor(this)
					.build();
		}
	}

	public static class ConsoleCommand extends CommandActionAdd {
		@Override public Action create(Player player, CommandContext args) { return new ActionConsoleCommand(args.<String>getOne("command").get()); }

		public CommandSpec build() {
			return CommandSpec.builder()
					.description(Text.of(TextColors.GOLD, "NPCs | Add Console Command Action"))
					.permission("npc.action.command.console")
					.arguments(GenericArguments.remainingRawJoinedStrings(Text.of("command")))
					.executor(this)
					.build();
		}
	}

	public static class Message extends CommandActionAdd {
		@Override public Action create(Player player, CommandContext args) { return new ActionMessage(args.<String>getOne("message").get()); }

		public CommandSpec build() {
			return CommandSpec.builder()
					.description(Text.of(TextColors.GOLD, "NPCs | Add Message Action"))
					.permission("npc.action.messager")
					.arguments(GenericArguments.remainingRawJoinedStrings(Text.of("message")))
					.executor(this)
					.build();
		}
	}

	public static class Delay extends CommandActionAdd {
		@Override public Action create(Player player, CommandContext args) { return new ActionDelay(args.<Integer>getOne("ticks").get()); }

		public CommandSpec build() {
			return CommandSpec.builder()
					.description(Text.of(TextColors.GOLD, "NPCs | Add Delay Action"))
					.permission("npc.action.delay")
					.arguments(GenericArguments.integer(Text.of("ticks")))
					.executor(this)
					.build();
		}
	}

	public static class Cooldown extends CommandActionAdd {
		@Override public Action create(Player player, CommandContext args) { return new ActionCooldown(args.<Integer>getOne("seconds").get(), args.<String>getOne("message").get()); }

		public CommandSpec build() {
			return CommandSpec.builder()
					.description(Text.of(TextColors.GOLD, "NPCs | Add Cooldown Action"))
					.permission("npc.action.cooldown")
					.arguments(GenericArguments.integer(Text.of("seconds")), GenericArguments.string(Text.of("message")))
					.executor(this)
					.build();
		}
	}

	public static class Pause extends CommandActionAdd {
		@Override public Action create(Player player, CommandContext args) { return new ActionPause(); }

		public CommandSpec build() {
			return CommandSpec.builder()
					.description(Text.of(TextColors.GOLD, "NPCs | Add Pause Action"))
					.permission("npc.action.pause")
					.executor(this)
					.build();
		}
	}

	public static class Goto extends CommandActionAdd {
		@Override public Action create(Player player, CommandContext args) { return new ActionGoto(args.<Integer>getOne("next").get()); }

		public CommandSpec build() {
			return CommandSpec.builder()
					.description(Text.of(TextColors.GOLD, "NPCs | Add Goto Action"))
					.permission("npc.action.goto")
					.arguments(GenericArguments.integer(Text.of("next")))
					.executor(this)
					.build();
		}
	}

	public static class Choices extends CommandActionAdd {

		@Override
		public Action create(Player player, CommandContext args) {
			Map<String, Integer> choices = Maps.newHashMap();
			choices.put(args.<String>getOne("first").get(), args.<Integer>getOne("goto_first").get());
			choices.put(args.<String>getOne("second").get(), args.<Integer>getOne("goto_second").get());
			return new ActionChoices(choices);
		}

		public CommandSpec build() {
			return CommandSpec.builder()
					.description(Text.of(TextColors.GOLD, "NPCs | Add Choices Action"))
					.permission("npc.action.choices")
					.arguments(
							GenericArguments.string(Text.of("first")),
							GenericArguments.integer(Text.of("goto_first")),
							GenericArguments.string(Text.of("second")),
							GenericArguments.integer(Text.of("goto_second")))
					.executor(this)
					.build();
		}
	}
}
