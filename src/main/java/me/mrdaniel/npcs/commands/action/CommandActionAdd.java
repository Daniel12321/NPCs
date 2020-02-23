package me.mrdaniel.npcs.commands.action;

import com.google.common.collect.Maps;
import me.mrdaniel.npcs.actions.*;
import me.mrdaniel.npcs.actions.actions.*;
import me.mrdaniel.npcs.catalogtypes.menupagetype.PageTypes;
import me.mrdaniel.npcs.commands.NPCCommand;
import me.mrdaniel.npcs.events.NPCEditEvent;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.Map;

public abstract class CommandActionAdd extends NPCCommand {

	public CommandActionAdd() {
		super(PageTypes.ACTIONS);
	}

	@Override
	public void execute(Player p, NPCAble npc, CommandContext args) throws CommandException {
		if (new NPCEditEvent(p, npc).post()) {
			throw new CommandException(Text.of(TextColors.RED, "Could not edit NPC: Event was cancelled!"));
		}

		npc.getNPCData().getActions().add(this.create(args));
		npc.getNPCData().writeActions().save();
	}

	public abstract Action create(CommandContext args);

	public static class PlayerCommand extends CommandActionAdd {
		@Override public Action create(CommandContext args) { return new ActionPlayerCommand(args.<String>getOne("command").get()); }
	}

	public static class ConsoleCommand extends CommandActionAdd {
		@Override public Action create(CommandContext args) { return new ActionConsoleCommand(args.<String>getOne("command").get()); }
	}

	public static class Message extends CommandActionAdd {
		@Override public Action create(CommandContext args) { return new ActionMessage(args.<String>getOne("message").get()); }
	}

	public static class Delay extends CommandActionAdd {
		@Override public Action create(CommandContext args) { return new ActionDelay(args.<Integer>getOne("ticks").get()); }
	}

	public static class Cooldown extends CommandActionAdd {
		@Override public Action create(CommandContext args) { return new ActionCooldown(args.<Integer>getOne("seconds").get(), args.<String>getOne("message").get()); }
	}

	public static class Pause extends CommandActionAdd {
		@Override public Action create(CommandContext args) { return new ActionPause(); }
	}

	public static class Goto extends CommandActionAdd {
		@Override public Action create(CommandContext args) { return new ActionGoto(args.<Integer>getOne("next").get()); }
	}

	public static class Choices extends CommandActionAdd {

		@Override
		public Action create(CommandContext args) {
			Map<String, Integer> choices = Maps.newHashMap();
			choices.put(args.<String>getOne("first").get(), args.<Integer>getOne("goto_first").get());
			choices.put(args.<String>getOne("second").get(), args.<Integer>getOne("goto_second").get());
			return new ActionChoices(choices);
		}
	}
}
