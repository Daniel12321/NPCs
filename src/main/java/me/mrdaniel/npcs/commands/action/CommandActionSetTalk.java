package me.mrdaniel.npcs.commands.action;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.player.Player;

import com.google.common.collect.Lists;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.commands.NPCCommand;
import me.mrdaniel.npcs.data.action.NPCTalkAction;
import me.mrdaniel.npcs.data.npc.NPCData;
import me.mrdaniel.npcs.utils.TextUtils;

public class CommandActionSetTalk extends NPCCommand {

	public CommandActionSetTalk(@Nonnull final NPCs npcs) {
		super(npcs);
	}

	@Override
	public void execute(final Player player, final Living npc, final CommandContext args) throws CommandException {
		NPCData data = npc.get(NPCData.class).get();
		data.setAction(Optional.of(new NPCTalkAction(Lists.newArrayList(TextUtils.toText(args.<String>getOne("message").get())), false, false, 0)));
		npc.offer(data);

		TextUtils.sendMessage(player, "You successfully set the selected NPC's action to 'talk'.");
	}
}