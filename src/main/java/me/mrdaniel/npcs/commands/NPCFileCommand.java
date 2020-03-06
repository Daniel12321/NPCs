package me.mrdaniel.npcs.commands;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.io.INPCData;
import me.mrdaniel.npcs.menu.chat.ChatMenu;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.Optional;
import java.util.function.Function;

public abstract class NPCFileCommand extends PlayerCommand {

	private final Function<INPCData, ChatMenu> menu;

	public NPCFileCommand(Function<INPCData, ChatMenu> menu) {
		this.menu = menu;
	}

	@Override
	public void execute(Player player, CommandContext args) throws CommandException {
		Optional<Integer> id = args.getOne("id");
		INPCData selected = NPCs.getInstance().getSelectedManager().get(player.getUniqueId()).orElse(null);

		if (id.isPresent()) {
			INPCData file = NPCs.getInstance().getNPCManager().getData(id.get()).orElseThrow(() -> new CommandException(Text.of(TextColors.RED, "No NPC with that ID exists!")));
			this.execute(player, file, args);
		} else if (selected != null) {
			this.execute(player, selected, args);
			this.menu.apply(selected).send(player);
		}  else {
			throw new CommandException(Text.of(TextColors.RED, "You don't have an NPC selected!"));
		}
	}

	public abstract void execute(Player player, INPCData data, CommandContext args) throws CommandException;
}
