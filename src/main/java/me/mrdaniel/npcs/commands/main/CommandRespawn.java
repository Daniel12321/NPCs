package me.mrdaniel.npcs.commands.main;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.npcs.catalogtypes.menupagetype.PageTypes;
import me.mrdaniel.npcs.commands.NPCFileCommand;
import me.mrdaniel.npcs.exceptions.NPCException;
import me.mrdaniel.npcs.io.NPCFile;
import me.mrdaniel.npcs.managers.NPCManager;

public class CommandRespawn extends NPCFileCommand {

	public CommandRespawn() {
		super(PageTypes.MAIN);
	}

	@Override
	public void execute(final Player p, final NPCFile file, final CommandContext args) throws CommandException {
		try { NPCManager.getInstance().spawn(file); }
		catch (final NPCException exc) { throw new CommandException(Text.of(TextColors.RED, "Failed to spawn NPC: ", exc.getMessage())); }
	}
}