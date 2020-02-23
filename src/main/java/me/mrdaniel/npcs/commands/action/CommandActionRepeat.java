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

public class CommandActionRepeat extends NPCCommand {

	public CommandActionRepeat() {
		super(ActionsChatMenu::new);
	}

	@Override
	public void execute(Player p, NPCAble npc, CommandContext args) throws CommandException {
		if (new NPCEditEvent(p, npc).post()) {
			throw new CommandException(Text.of(TextColors.RED, "Could not edit NPC: Event was cancelled!"));
		}

		Boolean value = args.<Boolean>getOne("repeat").orElse(!npc.getNPCData().getNPCActions().isRepeatActions());
		npc.getNPCData().getNPCActions().setRepeatActions(value);
		npc.getNPCData().writeNPCActions().saveNPC();
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
