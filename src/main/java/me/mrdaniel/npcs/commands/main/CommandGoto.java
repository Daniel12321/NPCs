package me.mrdaniel.npcs.commands.main;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.player.Player;

import me.mrdaniel.npcs.catalogtypes.menupages.PageTypes;
import me.mrdaniel.npcs.commands.NPCCommand;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;

public class CommandGoto extends NPCCommand {

	public CommandGoto() {
		super(PageTypes.MAIN);
	}

	@Override
	public void execute(final Player p, final NPCAble npc, final CommandContext args) throws CommandException {
		p.setLocation(((Living) npc).getLocation());
	}
}