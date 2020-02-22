package me.mrdaniel.npcs.commands.main;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.catalogtypes.menupagetype.PageTypes;
import me.mrdaniel.npcs.commands.NPCFileCommand;
import me.mrdaniel.npcs.exceptions.NPCException;
import me.mrdaniel.npcs.io.INPCData;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class CommandRespawn extends NPCFileCommand {

	// TODO: Add to command list
	public CommandRespawn() {
		super(PageTypes.MAIN);
	}

	@Override
	public void execute(final Player p, final INPCData data, final CommandContext args) throws CommandException {
		try {
			NPCs.getInstance().getNpcStore().spawn(data);
		} catch (final NPCException exc) {
			throw new CommandException(Text.of(TextColors.RED, "Failed to spawn NPC: ", exc.getMessage()));
		}
	}
}
