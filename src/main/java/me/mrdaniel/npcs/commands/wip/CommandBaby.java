//package me.mrdaniel.npcs.commands.wip;
//
//import javax.annotation.Nonnull;
//
//import org.spongepowered.api.command.CommandException;
//import org.spongepowered.api.command.args.CommandContext;
//import org.spongepowered.api.data.key.Keys;
//import org.spongepowered.api.entity.living.Living;
//import org.spongepowered.api.entity.living.player.Player;
//import org.spongepowered.api.text.Text;
//import org.spongepowered.api.text.format.TextColors;
//
//import me.mrdaniel.npcs.NPCs;
//import me.mrdaniel.npcs.commands.NPCCommand;
//import me.mrdaniel.npcs.event.NPCEvent;
//import me.mrdaniel.npcs.utils.TextUtils;
//
//public class CommandBaby extends NPCCommand {
//
//	public CommandBaby(@Nonnull final NPCs npcs) {
//		super(npcs);
//	}
//
//	@Override
//	public void execute(final Player player, final Living npc, final CommandContext args) throws CommandException {
//		if (!npc.supports(Keys.AGE)) throw new CommandException(Text.of(TextColors.RED, "You can only use this on NPC's that have a baby form."));
//		if (super.getGame().getEventManager().post(new NPCEvent.Edit(super.getContainer(), player, npc))) {
//			throw new CommandException(Text.of(TextColors.RED, "Could not edit NPC: Event was cancelled!"));
//		}
//
//		boolean baby = args.<Boolean>getOne("value").orElse(npc.get(Keys.AGE).orElse(0) > 0);
//		npc.offer(Keys.AGE, baby ? -32768 : 0);
//
//		player.sendMessage(TextUtils.getMessage(baby ? "The selected NPC is now a baby." : "The selected NPC is now an adult."));
//	}
//}