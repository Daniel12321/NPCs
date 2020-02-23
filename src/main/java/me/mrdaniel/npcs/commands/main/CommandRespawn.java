package me.mrdaniel.npcs.commands.main;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.commands.NPCCommand;
import me.mrdaniel.npcs.commands.NPCFileCommand;
import me.mrdaniel.npcs.exceptions.NPCException;
import me.mrdaniel.npcs.io.INPCData;
import me.mrdaniel.npcs.menu.chat.npc.PropertiesChatMenu;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class CommandRespawn extends NPCFileCommand {

	public CommandRespawn() {
		super(PropertiesChatMenu::new);
	}

	@Override
	public void execute(final Player p, final INPCData data, final CommandContext args) throws CommandException {
		try {
			NPCs.getInstance().getNPCManager().spawn(data);
		} catch (final NPCException exc) {
			throw new CommandException(Text.of(TextColors.RED, "Failed to spawn NPC: ", exc.getMessage()));
		}
	}

	public CommandSpec build() {
		return CommandSpec.builder()
				.description(Text.of(TextColors.GOLD, "NPCs | Respawn"))
				.permission("npc.edit.respawn")
				.arguments(NPCCommand.ID_ARG)
				.executor(this)
				.build();
	}
}
