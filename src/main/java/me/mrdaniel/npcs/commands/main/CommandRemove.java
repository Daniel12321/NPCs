package me.mrdaniel.npcs.commands.main;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.commands.NPCCommand;
import me.mrdaniel.npcs.exceptions.NPCException;
import me.mrdaniel.npcs.io.INPCData;
import me.mrdaniel.npcs.managers.NPCManager;
import me.mrdaniel.npcs.menu.chat.list.ListMenu;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.Optional;

public class CommandRemove implements CommandExecutor {

	// TODO: Rewrite to use NPCFileCommand
	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		NPCManager npcManager = NPCs.getInstance().getNPCManager();
		if (args.getOne("id").isPresent()) {
			try {
				npcManager.remove(src, args.<Integer>getOne("id").get());
				new ListMenu().send(src);
			} catch (final NPCException exc) {
				throw new CommandException(Text.of(TextColors.RED, "Failed to remove NPC: ", exc.getMessage()));
			}
		} else if (src instanceof Player) {
			Optional<INPCData> selected = NPCs.getInstance().getSelectedManager().get(((Player)src).getUniqueId());
			if (selected.isPresent()) {
				try {
					npcManager.remove(src, selected.get());
					new ListMenu().send(src);
				} catch (final NPCException exc) {
					throw new CommandException(Text.of(TextColors.RED, "Failed to remove NPC: ", exc.getMessage()));
				}
			} else {
				throw new CommandException(Text.of(TextColors.RED, "You don't have an NPC selected!"));
			}
		} else {
			throw new CommandException(Text.of(TextColors.RED, "Specify an NPC ID to remove an entity without selecting one ingame."));
		}
		return CommandResult.success();
	}

	public CommandSpec build() {
		return CommandSpec.builder()
				.description(Text.of(TextColors.GOLD, "NPCs | Remove"))
				.permission("npc.remove")
				.arguments(NPCCommand.ID_ARG)
				.executor(this)
				.build();
	}
}
