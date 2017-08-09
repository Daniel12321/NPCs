package me.mrdaniel.npcs.commands;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.npcs.catalogtypes.menupages.PageType;
import me.mrdaniel.npcs.catalogtypes.options.OptionType;
import me.mrdaniel.npcs.events.NPCEvent;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;

public class CommandEdit<T> extends NPCCommand {

	private final OptionType<T> option;

	public CommandEdit(@Nonnull final PageType page, @Nonnull final OptionType<T> option) {
		super(page);

		this.option = option;
	}

	@Override
	public void execute(final Player p, final NPCAble npc, final CommandContext args) throws CommandException {
		if (!this.option.supports(npc)) {
			throw new CommandException(Text.of(TextColors.RED, "That NPC does not support that option!"));
		}
		if (new NPCEvent.Edit(p, npc).post()) {
			throw new CommandException(Text.of(TextColors.RED, "Event was cancelled!"));
		}

		this.option.setFileAndNPC(npc, args.<T>getOne(this.option.getId()).orElseThrow(() -> new CommandException(Text.of(TextColors.RED, "Invalid value!"))));
	}
}