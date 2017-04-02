package me.mrdaniel.npcs.commands.action;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.pagination.PaginationList;
import org.spongepowered.api.service.pagination.PaginationService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.commands.PlayerCommand;

public class CommandAction extends PlayerCommand {

	private final PaginationList list;

	public CommandAction(@Nonnull final NPCs npcs, @Nonnull final PaginationService service) {
		super(npcs);

		this.list = service.builder().title(Text.of("[ ", TextColors.RED, "NPC Actions", TextColors.RESET, " ]"))
				.padding(Text.of("-"))
				.linesPerPage(20)
				.contents(Text.of(TextColors.AQUA, " /npc action set command <command>"),
						Text.of(TextColors.AQUA, " /npc action set talk <message>"),
						Text.of(TextColors.AQUA, " /npc action remove"))
				.build();
	}

	@Override
	public void execute(final Player player, final CommandContext args) throws CommandException {
		player.sendMessage(Text.EMPTY);
		this.list.sendTo(player);
	}
}