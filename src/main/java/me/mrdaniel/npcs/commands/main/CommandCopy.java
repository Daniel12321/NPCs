package me.mrdaniel.npcs.commands.main;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCTypes;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyTypes;
import me.mrdaniel.npcs.commands.NPCCommand;
import me.mrdaniel.npcs.commands.NPCFileCommand;
import me.mrdaniel.npcs.exceptions.NPCException;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.io.INPCData;
import me.mrdaniel.npcs.menu.chat.npc.PropertiesChatMenu;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class CommandCopy extends NPCFileCommand {

	public CommandCopy() {
		super(PropertiesChatMenu::new);
	}

	@Override
	public void execute(Player p, INPCData data, CommandContext args) throws CommandException {
		try {
			NPCAble copy = NPCs.getInstance().getNPCManager().create(p, data.getProperty(PropertyTypes.TYPE).orElse(NPCTypes.HUMAN));
			PropertyTypes.NPC_INIT.forEach(prop -> data.getProperty(prop).ifPresent(value -> copy.setProperty(prop, value)));
			data.getActions().getAllActions().forEach(action -> copy.getData().getActions().addAction(action));
		} catch (final NPCException exc) {
			throw new CommandException(Text.of(TextColors.RED, "Failed to copy NPC: ", exc.getMessage()));
		}
	}

	public CommandSpec build() {
		return CommandSpec.builder()
				.description(Text.of(TextColors.GOLD, "NPCs | Copy"))
				.permission("npc.copy")
				.arguments(NPCCommand.ID_ARG)
				.executor(this)
				.build();
	}
}
