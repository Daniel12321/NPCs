package me.mrdaniel.npcs.commands;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.io.INPCData;
import me.mrdaniel.npcs.menu.chat.npc.NPCChatMenu;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.Optional;
import java.util.function.Function;

public abstract class NPCFileCommand extends PlayerCommand {

	private final Function<INPCData, NPCChatMenu> menu;

	public NPCFileCommand(Function<INPCData, NPCChatMenu> menu) {
		this.menu = menu;
	}

	@Override
	public void execute(Player p, CommandContext args) throws CommandException {
		Optional<Integer> id = args.getOne("id");
		INPCData selected = NPCs.getInstance().getSelectedManager().get(p.getUniqueId()).orElse(null);

		if (id.isPresent()) {
			INPCData file = NPCs.getInstance().getNPCManager().getData(id.get()).orElseThrow(() -> new CommandException(Text.of(TextColors.RED, "No NPC with that ID exists!")));
			this.execute(p, file, args);
		} else if (selected != null) {
			this.execute(p, selected, args);
			this.menu.apply(selected).send(p);
		}  else {
			throw new CommandException(Text.of(TextColors.RED, "You don't have an NPC selected!"));
		}
	}

	public abstract void execute(Player p, INPCData data, CommandContext args) throws CommandException;
}
