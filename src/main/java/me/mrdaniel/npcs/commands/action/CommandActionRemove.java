package me.mrdaniel.npcs.commands.action;

import me.mrdaniel.npcs.commands.NPCCommand;
import me.mrdaniel.npcs.events.NPCEditEvent;
import me.mrdaniel.npcs.mixin.interfaces.NPCAble;
import me.mrdaniel.npcs.io.INPCData;
import me.mrdaniel.npcs.menu.chat.npc.ActionsChatMenu;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class CommandActionRemove extends NPCCommand {

	public CommandActionRemove() {
		super(ActionsChatMenu::new, false);
	}

	@Override
	public void execute(Player p, INPCData data, NPCAble npc, CommandContext args) throws CommandException {
		if (Sponge.getEventManager().post(new NPCEditEvent(p, data, npc))) {
			throw new CommandException(Text.of(TextColors.RED, "Could not edit NPC: Event was cancelled!"));
		}

		int id = args.<Integer>getOne("number").get();
		if (id < 0 || id >= data.getActions().getAllActions().size()) {
			throw new CommandException(Text.of(TextColors.RED, "No Action with this number exists."));
		}

		data.getActions().removeAction(id);
		data.writeActions().save();
	}

	public CommandSpec build() {
		return CommandSpec.builder()
				.description(Text.of(TextColors.GOLD, "NPCs | Remove Action"))
				.permission("npc.action.remove")
				.arguments(GenericArguments.integer(Text.of("number")))
				.executor(this)
				.build();
	}
}
