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
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import javax.annotation.Nullable;

public abstract class ActionSetCommand extends NPCCommand {

	public ActionSetCommand() {
		super(ActionsChatMenu::new, false);
	}

	@Override
	public void execute(Player player, INPCData data, @Nullable NPCAble npc, CommandContext args) throws CommandException {
		if (Sponge.getEventManager().post(new NPCEditEvent(player, data, npc))) {
			throw new CommandException(Text.of(TextColors.RED, "Could not edit NPC: Event was cancelled!"));
		}

		ActionSet actions = data.getProperty(PropertyTypes.ACTION_SET).orElse(new ActionSet());
		this.execute(player, actions, args);
		data.setProperty(PropertyTypes.ACTION_SET, actions).save();
	}

	public abstract void execute(Player player, ActionSet actions, CommandContext args) throws CommandException;
}
