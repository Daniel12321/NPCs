package me.mrdaniel.npcs.commands.main;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.commands.NPCCommand;
import me.mrdaniel.npcs.commands.NPCFileCommand;
import me.mrdaniel.npcs.exceptions.NPCException;
import me.mrdaniel.npcs.io.INPCData;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class CommandRemove extends NPCFileCommand {

	public CommandRemove() {
		super(null);
	}

	@Override
	public boolean execute(Player p, INPCData data, CommandContext args) throws CommandException {
		try {
			NPCs.getInstance().getNPCManager().remove(p, data);
		} catch (NPCException exc) {
			throw new CommandException(Text.of(TextColors.RED, "Failed to remove NPC: ", exc.getMessage()), exc);
		}

		return false;
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
