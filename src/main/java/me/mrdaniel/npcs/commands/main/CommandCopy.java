package me.mrdaniel.npcs.commands.main;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.npcs.catalogtypes.menupagetype.PageTypes;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCTypes;
import me.mrdaniel.npcs.commands.NPCCommand;
import me.mrdaniel.npcs.exceptions.NPCException;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.managers.NPCManager;

public class CommandCopy extends NPCCommand {

	public CommandCopy() {
		super(PageTypes.MAIN);
	}

	@Override
	public void execute(final Player p, final NPCAble npc, final CommandContext args) throws CommandException {
		try { NPCManager.getInstance().create(p, npc.getNPCFile().getType().orElse(NPCTypes.HUMAN)); }
		catch (final NPCException exc) { throw new CommandException(Text.of(TextColors.RED, "Failed to copy NPC: {}", exc)); }
	}
}