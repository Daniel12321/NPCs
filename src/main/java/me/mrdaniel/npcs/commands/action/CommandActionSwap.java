package me.mrdaniel.npcs.commands.action;

import me.mrdaniel.npcs.actions.Action;
import me.mrdaniel.npcs.actions.ActionSet;
import me.mrdaniel.npcs.commands.NPCCommand;
import me.mrdaniel.npcs.events.NPCEditEvent;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
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

public class CommandActionSwap extends NPCCommand {

	public CommandActionSwap() {
		super(ActionsChatMenu::new, false);
	}

	@Override
	public void execute(Player p, INPCData data, NPCAble npc, CommandContext args) throws CommandException {
		if (Sponge.getEventManager().post(new NPCEditEvent(p, data, npc))) {
			throw new CommandException(Text.of(TextColors.RED, "Could not edit NPC: Event was cancelled!"));
		}

		ActionSet actions = data.getNPCActions();
		int first = args.<Integer>getOne("first").get();
		int second = args.<Integer>getOne("second").get();
		int size = actions.getAllActions().size();

		if (first < 0 || second < 0 || first >= size || second >= size) {
			return;
		}

		Action firstAction = actions.getAction(first);
		Action secondAction = actions.getAction(second);
		actions.setAction(first, secondAction);
		actions.setAction(second, firstAction);
		data.writeNPCActions().saveNPC();
	}

	public CommandSpec build() {
		return CommandSpec.builder()
				.description(Text.of(TextColors.GOLD, "NPCs | Swap Actions"))
				.permission("npc.action.swap")
				.arguments(GenericArguments.integer(Text.of("first")), GenericArguments.integer(Text.of("second")))
				.executor(this)
				.build();
	}
}
