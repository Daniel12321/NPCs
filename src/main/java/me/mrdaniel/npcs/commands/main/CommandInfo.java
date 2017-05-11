package me.mrdaniel.npcs.commands.main;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.catalogtypes.menupages.PageTypes;
import me.mrdaniel.npcs.commands.PlayerCommand;
import me.mrdaniel.npcs.managers.menu.NPCMenu;

public class CommandInfo extends PlayerCommand {

	private final Text[] lines;

	public CommandInfo(@Nonnull final NPCs npcs) {
		super(npcs);

		this.lines = new Text[]{
				Text.EMPTY,
				Text.of(TextColors.YELLOW, "---------------=====[ ", TextColors.RED, "NPC Info", TextColors.YELLOW, " ]=====---------------"),
				Text.of(TextColors.AQUA, "You have currently no selected NPC."),
				Text.of(TextColors.AQUA, "You can select an NPC by shift right-clicking it."),
				Text.of(TextColors.AQUA, "You can create an NPC by doing: ", TextColors.YELLOW, "/npc create ", TextColors.GOLD, "[entitytype]"),
				Text.of(TextColors.YELLOW, "--------------------------------------------------")
		};
	}

	@Override
	public void execute(@Nonnull final Player p, @Nonnull final CommandContext args) throws CommandException {
		Optional<NPCMenu> menu = super.getNPCs().getMenuManager().get(p.getUniqueId());
		if (menu.isPresent()) { menu.get().send(p, PageTypes.MAIN); }
		else { p.sendMessages(this.lines); }
	}
}