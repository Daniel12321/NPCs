package me.mrdaniel.npcs.commands.action;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import com.google.common.collect.Maps;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.commands.NPCCommand;
import me.mrdaniel.npcs.data.npc.NPCData;
import me.mrdaniel.npcs.data.npc.actions.IterateActions;
import me.mrdaniel.npcs.data.npc.actions.ListActions;
import me.mrdaniel.npcs.data.npc.actions.OnlyOnceActions;

public class CommandActionRepeating extends NPCCommand {

	public CommandActionRepeating(@Nonnull final NPCs npcs) {
		super(npcs);
	}

	@Override
	public void execute(final Player player, final Living npc, final CommandContext args) throws CommandException {
		boolean value = args.<Boolean>getOne("repeating").get();

		NPCData data = npc.get(NPCData.class).get();
		if (!(data.getActions() instanceof ListActions)) { throw new CommandException(Text.of(TextColors.RED, "This selected NPC's Action Mode doesnt support this option!")); }

		if (value) { data.setActions(new IterateActions(data.getActions().getActions(), Maps.newHashMap())); }
		else { data.setActions(new OnlyOnceActions(data.getActions().getActions(), Maps.newHashMap())); }

		npc.offer(data);
	}
}