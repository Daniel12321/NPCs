package me.mrdaniel.npcs.commands;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.io.INPCData;
import me.mrdaniel.npcs.menu.chat.ChatMenu;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import javax.annotation.Nullable;
import java.util.function.Function;

public abstract class NPCCommand extends NPCFileCommand {

	public static CommandElement ID_ARG = GenericArguments.optionalWeak(GenericArguments.integer(Text.of("id")));

	private final boolean required;

	public NPCCommand(Function<INPCData, ChatMenu> menu, boolean required) {
		super(menu);

		this.required = required;
	}

	@Override
	public void execute(Player p, INPCData data, CommandContext args) throws CommandException {
		NPCAble npc = NPCs.getInstance().getNPCManager().getNPC(data).orElse(null);

		if (this.required && npc == null) {
			throw new CommandException(Text.of(TextColors.RED, "NPC is not loaded in!"));
		}

		this.execute(p, data, npc, args);
	}

	public abstract void execute(Player p, INPCData data, @Nullable NPCAble npc, CommandContext args) throws CommandException;
}
