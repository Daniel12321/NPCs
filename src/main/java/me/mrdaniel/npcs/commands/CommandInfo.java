package me.mrdaniel.npcs.commands;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.npcs.NPCs;

public class CommandInfo extends PlayerCommand {

	private final Text[] lines;

	public CommandInfo(@Nonnull final NPCs npcs) {
		super(npcs);

		this.lines = new Text[]{
				Text.EMPTY,
				Text.of("---------------===[ ", TextColors.RED, "NPC Info", TextColors.RESET, " ]===---------------"),
				Text.of(TextColors.AQUA, "There is currently no selected NPC."),
				Text.of(TextColors.AQUA, "You can select an NPC by shift right-clicking it."),
				Text.EMPTY,
				Text.of(TextColors.AQUA, "You can create an NPC by doing: "),
				Text.of(TextColors.YELLOW, "  /npc create ", TextColors.GOLD, "[entitytype]"),
				Text.of("----------------------------------------")
		};
	}

	@Override
	public void execute(@Nonnull final Player player, @Nonnull final CommandContext args) throws CommandException {
		Optional<Living> npc = super.getNPCs().getNPCManager().getSelected(player.getUniqueId());
		if (npc.isPresent()) { super.getNPCs().getNPCManager().sendMenu(player, npc.get()); }
		else { player.sendMessages(this.lines); }
	}
}