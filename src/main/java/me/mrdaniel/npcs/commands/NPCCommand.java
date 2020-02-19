package me.mrdaniel.npcs.commands;

import me.mrdaniel.npcs.catalogtypes.menupagetype.PageType;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.io.NPCFile;
import me.mrdaniel.npcs.managers.MenuManager;
import me.mrdaniel.npcs.managers.NPCManager;
import me.mrdaniel.npcs.managers.menu.NPCMenu;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.Optional;

public abstract class NPCCommand extends PlayerCommand {

	public static CommandElement ID_ARG = GenericArguments.optionalWeak(GenericArguments.integer(Text.of("id")));

	protected final PageType page;

	public NPCCommand(PageType page) {
		this.page = page;
	}

	@Override
	public void execute(final Player p, final CommandContext args) throws CommandException {
		Optional<NPCMenu> menu = MenuManager.getInstance().get(p.getUniqueId());
		Optional<Integer> id = args.getOne("id");

		if (menu.isPresent()) {
			this.execute(p, menu.get().getNpc(), args);
			menu.get().updateAndSend(p, this.page);
		} else if (id.isPresent()) {
			NPCFile file = NPCManager.getInstance().getFile(id.get()).orElseThrow(() -> new CommandException(Text.of(TextColors.RED, "No NPC with that ID exists!")));
			NPCAble npc = NPCManager.getInstance().getNPC(file).orElseThrow(() -> new CommandException(Text.of(TextColors.RED, "NPC is not spawned in!")));
			this.execute(p, npc, args);
		} else {
			throw new CommandException(Text.of(TextColors.RED, "You don't have an NPC selected!"));
		}
	}

	public abstract void execute(Player p, NPCAble npc, CommandContext args) throws CommandException;
}
