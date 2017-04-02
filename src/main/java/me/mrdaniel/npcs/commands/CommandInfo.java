package me.mrdaniel.npcs.commands;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.service.pagination.PaginationList;
import org.spongepowered.api.service.pagination.PaginationService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

public class CommandInfo implements CommandExecutor {

	private final PaginationList list;

	public CommandInfo(@Nonnull final PaginationService service) {
		this.list = service.builder().title(Text.of("[ ", TextColors.RED, "NPCs", TextColors.RESET, " ]"))
				.padding(Text.of("-"))
				.linesPerPage(20)
				.contents(Text.of(TextColors.GOLD, "    [ General Commands ]"),
						Text.builder().append(Text.of(TextColors.AQUA, " /npc create [entitytype]")).onClick(TextActions.suggestCommand("/npc create [type]")).build(),
						Text.builder().append(Text.of(TextColors.AQUA, " /npc remove")).onClick(TextActions.suggestCommand("/npc remove")).build(),
						Text.builder().append(Text.of(TextColors.AQUA, " /npc copy")).onClick(TextActions.suggestCommand("/npc copy")).build(),
						Text.builder().append(Text.of(TextColors.AQUA, " /npc mount")).onClick(TextActions.suggestCommand("/npc mount")).build(),
						Text.builder().append(Text.of(TextColors.AQUA, " /npc move")).onClick(TextActions.suggestCommand("/npc move")).build(),
						Text.builder().append(Text.of(TextColors.AQUA, " /npc deselect")).onClick(TextActions.suggestCommand("/npc deselect")).build(),
						Text.builder().append(Text.of(TextColors.AQUA, " /npc name <name...>")).onClick(TextActions.suggestCommand("/npc name <name...>")).build(),
						Text.builder().append(Text.of(TextColors.AQUA, " /npc look [true|false]")).onClick(TextActions.suggestCommand("/npc look true")).build(),
						Text.builder().append(Text.of(TextColors.AQUA, " /npc interact [true|false]")).onClick(TextActions.suggestCommand("/npc interact true")).build(),
						Text.builder().append(Text.of(TextColors.AQUA, " /npc glow [true|false]")).onClick(TextActions.suggestCommand("/npc glow true")).build(),
						Text.of(" "),
						Text.of(TextColors.GOLD, "    [ Entity Specific Commands ]"),
						Text.builder().append(Text.of(TextColors.AQUA, " /npc helmet - Armor Equipables")).onClick(TextActions.suggestCommand("/npc helmet")).build(),
						Text.builder().append(Text.of(TextColors.AQUA, " /npc chestplate - Armor Equipables")).onClick(TextActions.suggestCommand("/npc chestplate")).build(),
						Text.builder().append(Text.of(TextColors.AQUA, " /npc leggings - Armor Equipables")).onClick(TextActions.suggestCommand("/npc leggings")).build(),
						Text.builder().append(Text.of(TextColors.AQUA, " /npc boots - Armor Equipables")).onClick(TextActions.suggestCommand("/npc boots")).build(),
						Text.builder().append(Text.of(TextColors.AQUA, " /npc hand - Armor Equipables")).onClick(TextActions.suggestCommand("/npc hand")).build(),
						Text.builder().append(Text.of(TextColors.AQUA, " /npc offhand - Armor Equipables")).onClick(TextActions.suggestCommand("/npc offhand")).build(),
						Text.builder().append(Text.of(TextColors.AQUA, " /npc skin <playername> - Humans")).onClick(TextActions.suggestCommand("/npc skin <name>")).build(),
						Text.builder().append(Text.of(TextColors.AQUA, " /npc career <career> - Villagers")).onClick(TextActions.suggestCommand("/npc career <type>")).build(),
						Text.builder().append(Text.of(TextColors.AQUA, " /npc cat <cattype> - Ocelots")).onClick(TextActions.suggestCommand("/npc cat <type>")).build(),
						Text.builder().append(Text.of(TextColors.AQUA, " /npc llama <llamatype> - Llamas")).onClick(TextActions.suggestCommand("/npc llama <type>")).build(),
						Text.builder().append(Text.of(TextColors.AQUA, " /npc size <size> - Slimes")).onClick(TextActions.suggestCommand("/npc size 1")).build(),
						Text.builder().append(Text.of(TextColors.AQUA, " /npc sit [true|false] - Wolfs/Cats")).onClick(TextActions.suggestCommand("/npc sit true")).build(),
						Text.builder().append(Text.of(TextColors.AQUA, " /npc charge [true|false] - Creepers")).onClick(TextActions.suggestCommand("/npc charge true")).build())
				.build();
	}

	@Override
	public CommandResult execute(final CommandSource src, final CommandContext args) throws CommandException {
		src.sendMessage(Text.EMPTY);
		this.list.sendTo(src);
		return CommandResult.success();
	}
}