package me.mrdaniel.npcs.commands.main;

import me.mrdaniel.npcs.catalogtypes.menupagetype.PageTypes;
import me.mrdaniel.npcs.commands.PlayerCommand;
import me.mrdaniel.npcs.managers.MenuManager;
import me.mrdaniel.npcs.managers.menu.NPCMenu;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.Optional;

public class CommandInfo extends PlayerCommand {

	private final Text[] lines;

	public CommandInfo() {
		this.lines = new Text[]{
				Text.EMPTY,
				Text.of(TextColors.YELLOW, "---------------=====[ ", TextColors.RED, "NPC Info", TextColors.YELLOW, " ]=====---------------"),
				Text.of(TextColors.AQUA, "You currently have not selected an NPC."),
				Text.of(TextColors.AQUA, "You can select an NPC by shift right clicking it."),
				Text.of(TextColors.AQUA, "You can see a list of NPC's by doing: ", TextColors.YELLOW, "/npc list"),
				Text.of(TextColors.AQUA, "You can create an NPC by doing: ", TextColors.YELLOW, "/npc create ", TextColors.GOLD, "[entitytype]"),
				Text.of(TextColors.YELLOW, "--------------------------------------------------")
		};
	}

	@Override
	public void execute(Player p, CommandContext args) throws CommandException {
		Optional<NPCMenu> menu = MenuManager.getInstance().get(p.getUniqueId());

		if (menu.isPresent()) {
			menu.get().send(p, PageTypes.MAIN);
		} else {
			p.sendMessages(this.lines);
		}
	}
}
