package me.mrdaniel.npcs.commands.main;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.commands.NPCCommand;
import me.mrdaniel.npcs.exceptions.NPCException;
import me.mrdaniel.npcs.io.INPCStore;
import me.mrdaniel.npcs.managers.menu.NPCMenu;
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

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		INPCStore npcStore = NPCs.getInstance().getNpcStore();
		if (args.getOne("id").isPresent()) {
			try {
				npcStore.remove(src, args.<Integer>getOne("id").get());
				CommandList.sendNPCList(src);
			} catch (final NPCException exc) {
				throw new CommandException(Text.of(TextColors.RED, "Failed to remove NPC: ", exc.getMessage()));
			}
		} else if (src instanceof Player) {
			Optional<NPCMenu> menu = NPCs.getInstance().getMenuManager().get(((Player)src).getUniqueId());
			if (menu.isPresent()) {
				try {
					npcStore.remove(src, menu.get().getNpc());
					CommandList.sendNPCList(src);
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
