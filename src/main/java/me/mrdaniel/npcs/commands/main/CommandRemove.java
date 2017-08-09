package me.mrdaniel.npcs.commands.main;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.npcs.catalogtypes.menupages.PageTypes;
import me.mrdaniel.npcs.commands.NPCCommand;
import me.mrdaniel.npcs.exceptions.NPCException;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.managers.NPCManager;

public class CommandRemove extends NPCCommand {

	public CommandRemove() {
		super(PageTypes.MAIN);
	}

	@Override
	public void execute(Player p, NPCAble npc, CommandContext args) throws CommandException {
		if (args.getOne("id").isPresent()) {
			try { NPCManager.getInstance().remove(p, args.<Integer>getOne("id").get()); CommandList.getInstance().sendNPCList(p); }
			catch (final NPCException exc) { throw new CommandException(Text.of(TextColors.RED, "Failed to remove NPC: ", exc.getMessage())); }
		}
		else {
			try { NPCManager.getInstance().remove(p, npc.getNPCFile()); CommandList.getInstance().sendNPCList(p); }
			catch (final NPCException exc) { throw new CommandException(Text.of(TextColors.RED, "Failed to remove NPC: ", exc.getMessage())); }
		}		
	}
}