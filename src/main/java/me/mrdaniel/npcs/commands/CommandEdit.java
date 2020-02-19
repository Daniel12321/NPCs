package me.mrdaniel.npcs.commands;

import me.mrdaniel.npcs.catalogtypes.menupagetype.PageType;
import me.mrdaniel.npcs.catalogtypes.optiontype.OptionType;
import me.mrdaniel.npcs.events.NPCEditEvent;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class CommandEdit<T> extends NPCCommand {

	private final OptionType<T> option;

	public CommandEdit(PageType page, OptionType<T> option) {
		super(page);

		this.option = option;
	}

	@Override
	public void execute(Player p, NPCAble npc, CommandContext args) throws CommandException {
		if (!this.option.isSupported(npc)) {
			throw new CommandException(Text.of(TextColors.RED, "That NPC does not support that option!"));
		}
		if (new NPCEditEvent(p, npc).post()) {
			throw new CommandException(Text.of(TextColors.RED, "Event was cancelled!"));
		}

		this.option.writeToFileAndNPC(npc, args.<T>getOne(this.option.getId()).orElseThrow(() -> new CommandException(Text.of(TextColors.RED, "Invalid value!"))));
	}

	public static <T> CommandSpec build(PageType page, OptionType<T> option) {
		return CommandSpec.builder()
				.description(Text.of(TextColors.GOLD, "NPCs | Set ", option.getName()))
				.permission("npc.edit." + option.getId())
				.arguments(option.getArgs())
				.executor(new CommandEdit<>(page, option))
				.build();
	}
}
