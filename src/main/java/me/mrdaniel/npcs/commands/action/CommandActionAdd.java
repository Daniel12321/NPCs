package me.mrdaniel.npcs.commands.action;

import java.util.Map;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import com.google.common.collect.Maps;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.catalogtypes.menupages.PageTypes;
import me.mrdaniel.npcs.commands.NPCCommand;
import me.mrdaniel.npcs.data.npc.actions.Action;
import me.mrdaniel.npcs.data.npc.actions.ActionChoices;
import me.mrdaniel.npcs.data.npc.actions.ActionConsoleCommand;
import me.mrdaniel.npcs.data.npc.actions.ActionDelay;
import me.mrdaniel.npcs.data.npc.actions.ActionGoto;
import me.mrdaniel.npcs.data.npc.actions.ActionMessage;
import me.mrdaniel.npcs.data.npc.actions.ActionPause;
import me.mrdaniel.npcs.data.npc.actions.ActionPlayerCommand;
import me.mrdaniel.npcs.events.NPCEvent;
import me.mrdaniel.npcs.managers.menu.NPCMenu;

public abstract class CommandActionAdd extends NPCCommand {

	public CommandActionAdd(@Nonnull final NPCs npcs) {
		super(npcs, PageTypes.ACTIONS);
	}

	@Override
	public void execute(final Player p, final NPCMenu menu, final CommandContext args) throws CommandException {
		if (super.getGame().getEventManager().post(new NPCEvent.Edit(super.getContainer(), p, menu.getNPC(), menu.getFile()))) {
			throw new CommandException(Text.of(TextColors.RED, "Could not edit NPC: Event was cancelled!"));
		}

		menu.getFile().getActions().add(this.create(args));
		menu.getFile().writeActions();
		menu.getFile().save();
	}

	@Nonnull
	public abstract Action create(@Nonnull final CommandContext args);

	public static class PlayerCommand extends CommandActionAdd {
		public PlayerCommand(@Nonnull final NPCs npcs) {
			super(npcs);
		}

		@Override public Action create(final CommandContext args) { return new ActionPlayerCommand(args.<String>getOne("command").get()); }
	}

	public static class ConsoleCommand extends CommandActionAdd {
		public ConsoleCommand(@Nonnull final NPCs npcs) {
			super(npcs);
		}

		@Override public Action create(final CommandContext args) { return new ActionConsoleCommand(args.<String>getOne("command").get()); }
	}

	public static class Message extends CommandActionAdd {
		public Message(@Nonnull final NPCs npcs) {
			super(npcs);
		}

		@Override public Action create(final CommandContext args) { return new ActionMessage(args.<String>getOne("message").get()); }
	}

	public static class Delay extends CommandActionAdd {
		public Delay(@Nonnull final NPCs npcs) {
			super(npcs);
		}

		@Override public Action create(final CommandContext args) { return new ActionDelay(args.<Integer>getOne("ticks").get()); }
	}

	public static class Pause extends CommandActionAdd {
		public Pause(@Nonnull final NPCs npcs) {
			super(npcs);
		}

		@Override public Action create(final CommandContext args) { return new ActionPause(); }
	}

	public static class Goto extends CommandActionAdd {
		public Goto(@Nonnull final NPCs npcs) {
			super(npcs);
		}

		@Override public Action create(final CommandContext args) { return new ActionGoto(args.<Integer>getOne("next").get()); }
	}

	public static class Choices extends CommandActionAdd {
		public Choices(@Nonnull final NPCs npcs) {
			super(npcs);
		}

		@Override
		public Action create(final CommandContext args) {
			Map<String, Integer> choices = Maps.newHashMap();
			choices.put(args.<String>getOne("first").get(), args.<Integer>getOne("goto_first").get());
			choices.put(args.<String>getOne("second").get(), args.<Integer>getOne("goto_second").get());
			return new ActionChoices(choices); 
		}
	}
}