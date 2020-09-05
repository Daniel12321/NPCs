package me.mrdaniel.npcs.commands;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.gui.chat.MenuPage;
import me.mrdaniel.npcs.gui.chat.npc.NPCChatMenu;
import me.mrdaniel.npcs.io.INPCData;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public abstract class NPCFileCommand extends PlayerCommand {

	private final Class<? extends MenuPage> menuPage;

	public NPCFileCommand(Class<? extends MenuPage> menuPage) {
		this.menuPage = menuPage;
	}

	@Override
	public void execute(Player player, CommandContext args) throws CommandException {
		int id = args.<Integer>getOne("id").orElse(0);

		if (id > 0) {
			INPCData data = NPCs.getInstance().getNPCManager().getData(id).orElseThrow(() -> new CommandException(Text.of(TextColors.RED, "No NPC with that ID exists!")));
			this.execute(player, data, args);
			return;
		}

		NPCChatMenu menu = NPCs.getInstance().getSelectedManager().get(player.getUniqueId()).orElse(null);
		if (menu != null) {
			if (this.execute(player, menu.getData(), args)) {
				menu.setPage(this.menuPage);
				menu.setPlayer(player);
				menu.open();
			}
			return;
		}

		throw new CommandException(Text.of(TextColors.RED, "You don't have an NPC selected!"));
	}

	/**
	 * @param player The Player
	 * @param data The NPC data
	 * @param args The command arguments
	 * @return Whether to open the NPCChatMenu
	 * @throws CommandException
	 */
	public abstract boolean execute(Player player, INPCData data, CommandContext args) throws CommandException;
}
