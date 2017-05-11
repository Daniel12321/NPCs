package me.mrdaniel.npcs.commands;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.catalogtypes.menupages.PageType;
import me.mrdaniel.npcs.managers.menu.NPCMenu;

public abstract class NPCCommand extends PlayerCommand {

	protected final PageType page;

	public NPCCommand(@Nonnull final NPCs npcs, @Nonnull final PageType page) {
		super(npcs);

		this.page = page;
	}

	@Override
	public void execute(final Player p, final CommandContext args) throws CommandException {
		NPCMenu menu = super.getNPCs().getMenuManager().get(p.getUniqueId()).orElseThrow(() -> new CommandException(Text.of(TextColors.RED, "You don't have an NPC selected!")));
		this.execute(p, menu, args);
		menu.updateAndSend(p, this.page);
	}

	public abstract void execute(@Nonnull final Player p, @Nonnull final NPCMenu menu, @Nonnull final CommandContext args) throws CommandException;
}