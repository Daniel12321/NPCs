package me.mrdaniel.npcs.commands.main;

import me.mrdaniel.npcs.commands.NPCCommand;
import me.mrdaniel.npcs.commands.NPCFileCommand;
import me.mrdaniel.npcs.io.INPCData;
import me.mrdaniel.npcs.menu.chat.npc.PropertiesChatMenu;
import me.mrdaniel.npcs.utils.Position;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class CommandGoto extends NPCFileCommand {

	public CommandGoto() {
		super(PropertiesChatMenu::new);
	}

	@Override
	public void execute(Player p, INPCData data, CommandContext args) throws CommandException {
		Position pos = data.getNPCPosition();
		World world = pos.getWorld().orElseThrow(() -> new CommandException(Text.of(TextColors.RED, "Failed to find world '", pos.getWorldName(), "'")));
		p.setLocation(new Location<>(world, pos.getX(), pos.getY(), pos.getZ()));
	}

	public CommandSpec build() {
		return CommandSpec.builder()
				.description(Text.of(TextColors.GOLD, "NPCs | GoTo"))
				.permission("npc.goto")
				.arguments(NPCCommand.ID_ARG)
				.executor(this)
				.build();
	}
}
