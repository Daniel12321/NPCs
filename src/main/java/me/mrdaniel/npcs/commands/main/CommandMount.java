package me.mrdaniel.npcs.commands.main;

import me.mrdaniel.npcs.commands.NPCCommand;
import me.mrdaniel.npcs.gui.chat.npc.PropertiesPage;
import me.mrdaniel.npcs.io.INPCData;
import me.mrdaniel.npcs.mixin.interfaces.NPCAble;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class CommandMount extends NPCCommand {

	public CommandMount() {
		super(PropertiesPage.class, true);
	}

	@Override
	public boolean execute(Player p, INPCData data, NPCAble npc, CommandContext args) throws CommandException {
		((Living)npc).addPassenger(p);

		return true;
	}

	public CommandSpec build() {
		return CommandSpec.builder()
				.description(Text.of(TextColors.GOLD, "NPCs | Mount"))
				.permission("npc.mount")
				.arguments(NPCCommand.ID_ARG)
				.executor(this)
				.build();
	}
}
