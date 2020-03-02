package me.mrdaniel.npcs.commands.action;

import me.mrdaniel.npcs.actions.ActionSet;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyTypes;
import me.mrdaniel.npcs.commands.NPCCommand;
import me.mrdaniel.npcs.events.NPCEditEvent;
import me.mrdaniel.npcs.io.INPCData;
import me.mrdaniel.npcs.menu.chat.npc.ActionsChatMenu;
import me.mrdaniel.npcs.mixin.interfaces.NPCAble;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class CommandActionRepeat extends NPCCommand {

	public CommandActionRepeat() {
		super(ActionsChatMenu::new, false);
	}

	@Override
	public void execute(Player p, INPCData data, NPCAble npc, CommandContext args) throws CommandException {
		if (Sponge.getEventManager().post(new NPCEditEvent(p, data, npc))) {
			throw new CommandException(Text.of(TextColors.RED, "Could not edit NPC: Event was cancelled!"));
		}

		ActionSet actions = data.getProperty(PropertyTypes.ACTION_SET).orElse(new ActionSet());
		Boolean value = args.<Boolean>getOne("repeat").orElse(!actions.isRepeatActions());
		actions.setRepeatActions(value);
		data.setProperty(PropertyTypes.ACTION_SET, actions);
		data.save();
	}

	public CommandSpec build() {
		return CommandSpec.builder()
				.description(Text.of(TextColors.GOLD, "NPCs | Set Repeat Actions"))
				.permission("npc.action.repeat")
				.arguments(GenericArguments.optional(GenericArguments.bool(Text.of("repeat"))))
				.executor(this)
				.build();
	}
}
