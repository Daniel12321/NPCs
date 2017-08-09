package me.mrdaniel.npcs.commands;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.npcs.catalogtypes.menupages.PageType;
import me.mrdaniel.npcs.io.NPCFile;
import me.mrdaniel.npcs.managers.MenuManager;
import me.mrdaniel.npcs.managers.NPCManager;
import me.mrdaniel.npcs.managers.menu.NPCMenu;

public abstract class NPCFileCommand extends PlayerCommand {

	protected final PageType page;

	public NPCFileCommand(@Nonnull final PageType page) {
		this.page = page;
	}

	@Override
	public void execute(Player p, CommandContext args) throws CommandException {
		Optional<NPCMenu> menu = MenuManager.getInstance().get(p.getUniqueId());
		Optional<Integer> id = args.getOne("id");

		if (menu.isPresent()) {
			this.execute(p, menu.get().getNpc().getNPCFile(), args);
			menu.get().updateAndSend(p, this.page);
		}
		else if (id.isPresent()) {
			NPCFile file = NPCManager.getInstance().getFile(id.get()).orElseThrow(() -> new CommandException(Text.of(TextColors.RED, "No NPC with that ID exists!")));
			this.execute(p, file, args);
		}
		else throw new CommandException(Text.of(TextColors.RED, "You don't have an NPC selected!"));
	}

	public abstract void execute(@Nonnull final Player p, @Nonnull final NPCFile file, @Nonnull final CommandContext args) throws CommandException;
}