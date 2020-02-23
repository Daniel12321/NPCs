package me.mrdaniel.npcs.commands.main;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCTypes;
import me.mrdaniel.npcs.commands.PlayerCommand;
import me.mrdaniel.npcs.events.NPCCreateEvent;
import me.mrdaniel.npcs.exceptions.NPCException;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class CommandCreate extends PlayerCommand {

	@Override
	public void execute(final Player p, final CommandContext args) throws CommandException {
		NPCType type = args.<NPCType>getOne("type").orElse(NPCTypes.HUMAN);

		if (new NPCCreateEvent(p, type).post()) {
			throw new CommandException(Text.of(TextColors.RED, "Failed to create NPC: Event was cancelled"));
		}

		try {
			NPCs.getInstance().getNPCManager().create(p, type);
		} catch (final NPCException exc) {
			throw new CommandException(Text.of(TextColors.RED, "Failed to create NPC: ", exc.getMessage()));
		}
	}

	public CommandSpec build() {
		return CommandSpec.builder()
				.description(Text.of(TextColors.GOLD, "NPCs | Create"))
				.permission("npc.create")
				.arguments(GenericArguments.optional(GenericArguments.catalogedElement(Text.of("type"), NPCType.class)))
				.executor(this)
				.build();
	}
}
