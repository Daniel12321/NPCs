//package me.mrdaniel.npcs.commands.wip;
//
//import java.util.List;
//
//import javax.annotation.Nonnull;
//
//import org.spongepowered.api.command.CommandException;
//import org.spongepowered.api.command.args.CommandContext;
//import org.spongepowered.api.entity.living.Living;
//import org.spongepowered.api.entity.living.player.Player;
//import org.spongepowered.api.text.Text;
//import org.spongepowered.api.text.format.TextColors;
//
//import com.flowpowered.math.vector.Vector3d;
//
//import me.mrdaniel.npcs.NPCs;
//import me.mrdaniel.npcs.commands.NPCCommand;
//import me.mrdaniel.npcs.data.WalkingData;
//
//public class CommandWalkPosRemove extends NPCCommand {
//
//	public CommandWalkPosRemove(@Nonnull final NPCs npcs) {
//		super(npcs);
//	}
//
//	@Override
//	public void execute(final Player player, final Living npc, final CommandContext args) throws CommandException {
//		WalkingData data = npc.get(WalkingData.class).orElseThrow(() -> new CommandException(Text.of(TextColors.RED, "You must make the selected NPC walk first.")));
//
//		List<Vector3d> walk_positions = data.getWalkPositions();
//		if (walk_positions.isEmpty()) { throw new CommandException(Text.of(TextColors.RED, "No Walk Positions were found for the selected NPC.")); }
//
//		Vector3d ppos = player.getLocation().getPosition();
//		int closest = 0;
//		double close = walk_positions.get(0).distance(ppos);
//
//		for (int i = 1; i < walk_positions.size(); i++) {
//			Vector3d pos = walk_positions.get(i);
//			double distance = pos.distance(ppos);
//			if (distance < close) { closest = i; close = distance; }
//		}
//
//		walk_positions.remove(closest);
//		data.setWalkPositions(walk_positions);
//		npc.offer(data);
//
//		player.sendMessage(Text.of(TextColors.YELLOW, "You removed the nearest Walk Position from the selected NPC."));
//	}
//}