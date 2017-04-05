package me.mrdaniel.npcs.commands.action;

import java.util.List;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.commands.NPCCommand;
import me.mrdaniel.npcs.data.action.Action;
import me.mrdaniel.npcs.data.action.NPCIterateActions;
import me.mrdaniel.npcs.data.action.NPCRandomActions;
import me.mrdaniel.npcs.data.npc.NPCData;

public class CommandMode extends NPCCommand {

	public CommandMode(@Nonnull final NPCs npcs) {
		super(npcs);
	}

	@Override
	public void execute(final Player player, final Living npc, final CommandContext args) throws CommandException {
		String mode = args.<String>getOne("mode").get();

		NPCData data = npc.get(NPCData.class).get();
		List<Action> old = data.getActions().getActions();

		if (mode.equalsIgnoreCase("random")) { data.setActions(new NPCRandomActions(old)); }
		else if (mode.equalsIgnoreCase("inorder")) { data.setActions(new NPCIterateActions(old)); }
		else throw new CommandException(Text.of(TextColors.RED, "Invalid NPC Actions Mode!"));

		npc.offer(data);
	}
}