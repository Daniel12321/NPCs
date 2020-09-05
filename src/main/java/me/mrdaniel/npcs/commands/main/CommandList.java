package me.mrdaniel.npcs.commands.main;

import me.mrdaniel.npcs.commands.PlayerCommand;
import me.mrdaniel.npcs.gui.chat.list.ListMenu;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class CommandList extends PlayerCommand {

	@Override
	public void execute(Player player, CommandContext args) throws CommandException {
		new ListMenu(player).open();
	}

	public CommandSpec build() {
		return CommandSpec.builder()
				.description(Text.of(TextColors.GOLD, "NPCs | List"))
				.permission("npc.list")
				.executor(this)
				.build();
	}
}
