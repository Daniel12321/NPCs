package me.mrdaniel.npcs.commands.main;

import java.util.Optional;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.npcs.exceptions.NPCException;
import me.mrdaniel.npcs.managers.MenuManager;
import me.mrdaniel.npcs.managers.NPCManager;
import me.mrdaniel.npcs.managers.menu.NPCMenu;

public class CommandRemove implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		if (args.getOne("id").isPresent()) {
			try { NPCManager.getInstance().remove(src, args.<Integer>getOne("id").get()); CommandList.getInstance().sendNPCList(src); }
			catch (final NPCException exc) { throw new CommandException(Text.of(TextColors.RED, "Failed to remove NPC: ", exc.getMessage())); }
		}
		else if (src instanceof Player) {
			Optional<NPCMenu> menu = MenuManager.getInstance().get(((Player)src).getUniqueId());
			if (menu.isPresent()) {
				try { NPCManager.getInstance().remove(src, menu.get().getNpc(), true); CommandList.getInstance().sendNPCList(src); }
				catch (final NPCException exc) { throw new CommandException(Text.of(TextColors.RED, "Failed to remove NPC: ", exc.getMessage())); }
			}
			else { throw new CommandException(Text.of(TextColors.RED, "You don't have an NPC selected!")); }
		}
		else { throw new CommandException(Text.of(TextColors.RED, "Specify an NPC ID to remove an entity without selecting one ingame.")); }
		return CommandResult.success();
	}
}