package me.mrdaniel.npcs.commands.edit;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyTypes;
import me.mrdaniel.npcs.commands.NPCCommand;
import me.mrdaniel.npcs.exceptions.NPCException;
import me.mrdaniel.npcs.io.INPCData;
import me.mrdaniel.npcs.gui.chat.npc.PropertiesChatMenu;
import me.mrdaniel.npcs.mixin.interfaces.NPCAble;
import me.mrdaniel.npcs.utils.Position;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class CommandMove extends NPCCommand {

	public CommandMove() {
		super(PropertiesChatMenu::new, false);
	}

	@Override
	public void execute(Player p, INPCData data, NPCAble npc, CommandContext args) throws CommandException {
		Position pos = new Position(p);

		if (npc != null) {
			npc.setProperty(PropertyTypes.POSITION, pos).save();
		} else {
			data.setProperty(PropertyTypes.POSITION, pos).save();
			try {
				NPCs.getInstance().getNPCManager().spawn(data);
			} catch (NPCException exc) {
				throw new CommandException(Text.of(TextColors.RED, "Failed to respawn NPC: ", exc.getMessage()), exc);
			}
		}
	}

	public CommandSpec build() {
		return CommandSpec.builder()
				.description(Text.of(TextColors.GOLD, "NPCs | Move"))
				.permission("npc.edit.move")
				.arguments(NPCCommand.ID_ARG)
				.executor(this)
				.build();
	}
}
