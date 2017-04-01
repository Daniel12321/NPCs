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
//import com.flowpowered.math.vector.Vector3d;
//
//import me.mrdaniel.npcs.NPCs;
//import me.mrdaniel.npcs.commands.NPCCommand;
//import me.mrdaniel.npcs.event.NPCEvent;
//import me.mrdaniel.npcs.utils.TextUtils;
//
//public class CommandArms extends NPCCommand {
//
//	public CommandArms(@Nonnull final NPCs npcs) {
//		super(npcs);
//	}
//
//	@Override
//	public void execute(final Player player, final Living npc, final CommandContext args) throws CommandException {
//		if (super.getGame().getEventManager().post(new NPCEvent.Edit(super.getContainer(), player, npc))) {
//			throw new CommandException(Text.of(TextColors.RED, "Could not edit NPC: Event was cancelled!"));
//		}
//		npc.offer(Keys.LEFT_ARM_ROTATION, args.<Vector3d>getOne("left").get());
//		npc.offer(Keys.RIGHT_ARM_ROTATION, args.<Vector3d>getOne("right").get());
//
//		player.sendMessage(TextUtils.getMessage("You successfully changed the selected NPC's arm locations."));
//	}
//}