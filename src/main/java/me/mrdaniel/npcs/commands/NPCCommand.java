package me.mrdaniel.npcs.commands;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.gui.chat.MenuPage;
import me.mrdaniel.npcs.io.INPCData;
import me.mrdaniel.npcs.mixin.interfaces.NPCAble;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import javax.annotation.Nullable;

public abstract class NPCCommand extends NPCFileCommand {

	public static CommandElement ID_ARG = GenericArguments.optionalWeak(GenericArguments.integer(Text.of("id")));

	private final boolean required;

	public NPCCommand(Class<? extends MenuPage> menuPage, boolean required) {
		super(menuPage);

		this.required = required;
	}

	@Override
	public boolean execute(Player player, INPCData data, CommandContext args) throws CommandException {
		NPCAble npc = NPCs.getInstance().getNPCManager().getNPC(data).orElse(null);

		if (this.required && npc == null) {
			throw new CommandException(Text.of(TextColors.RED, "NPC is not loaded in!"));
		}

		return this.execute(player, data, npc, args);
	}

	/**
	 * @param player The Player
	 * @param data The NPC Data
	 * @param npc The NPC
	 * @param args The command arguments
	 * @return Whether to open the NPCChatMenu
	 * @throws CommandException
	 */
	public abstract boolean execute(Player player, INPCData data, @Nullable NPCAble npc, CommandContext args) throws CommandException;
}
