package me.mrdaniel.npcs.commands.action;

import me.mrdaniel.npcs.commands.NPCCommand;
import me.mrdaniel.npcs.events.NPCEditEvent;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.menu.chat.npc.ActionsChatMenu;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class CommandActionRemove extends NPCCommand {

	public CommandActionRemove() {
		super(ActionsChatMenu::new);
	}

	@Override
	public void execute(final Player p, final NPCAble npc, final CommandContext args) throws CommandException {
		if (new NPCEditEvent(p, npc).post()) {
			throw new CommandException(Text.of(TextColors.RED, "Could not edit NPC: Event was cancelled!"));
		}

		int id = args.<Integer>getOne("number").get();
		if (id < 0 || id >= npc.getNPCData().getNPCActions().getAllActions().size()) {
			throw new CommandException(Text.of(TextColors.RED, "No Action with this number exists."));
		}

		npc.getNPCData().getNPCActions().removeAction(id);
		npc.getNPCData().writeNPCActions().saveNPC();
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
