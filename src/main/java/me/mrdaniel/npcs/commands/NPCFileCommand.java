package me.mrdaniel.npcs.commands;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.catalogtypes.menupagetype.PageType;
import me.mrdaniel.npcs.io.INPCData;
import me.mrdaniel.npcs.managers.menu.NPCMenu;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.Optional;

public abstract class NPCFileCommand extends PlayerCommand {

	protected final PageType page;

	public NPCFileCommand(PageType page) {
		this.page = page;
	}

	@Override
	public void execute(Player p, CommandContext args) throws CommandException {
		Optional<NPCMenu> menu = NPCs.getInstance().getMenuManager().get(p.getUniqueId());
		Optional<Integer> id = args.getOne("id");

		if (menu.isPresent()) {
			this.execute(p, menu.get().getNpc().getNPCData(), args);
			menu.get().updateAndSend(p, this.page);
		} else if (id.isPresent()) {
			INPCData file = NPCs.getInstance().getNPCManager().getData(id.get()).orElseThrow(() -> new CommandException(Text.of(TextColors.RED, "No NPC with that ID exists!")));
			this.execute(p, file, args);
		} else {
			throw new CommandException(Text.of(TextColors.RED, "You don't have an NPC selected!"));
		}
	}

	public abstract void execute(Player p, INPCData file, CommandContext args) throws CommandException;
}
