//package me.mrdaniel.npcs.commands.wip;
//
//import java.util.Optional;
//
//import javax.annotation.Nonnull;
//
//import org.spongepowered.api.command.CommandException;
//import org.spongepowered.api.command.args.CommandContext;
//import org.spongepowered.api.data.key.Keys;
//import org.spongepowered.api.entity.living.Agent;
//import org.spongepowered.api.entity.living.Living;
//import org.spongepowered.api.entity.living.player.Player;
//import org.spongepowered.api.text.Text;
//import org.spongepowered.api.text.format.TextColors;
//
//import me.mrdaniel.npcs.NPCs;
//import me.mrdaniel.npcs.commands.NPCCommand;
//import me.mrdaniel.npcs.data.WalkingData;
//import me.mrdaniel.npcs.manager.NPCManager;
//
//public class CommandWalk extends NPCCommand {
//
//	public CommandWalk(@Nonnull final NPCs npcs) {
//		super(npcs);
//	}
//
//	@Override
//	public void execute(final Player player, final Living npc, final CommandContext args) throws CommandException {
//		if (npc instanceof Agent) {
//			Optional<WalkingData> data = npc.get(WalkingData.class);
//			if (data.isPresent()) {
//				NPCManager.removeTarget(npc);
//				npc.remove(WalkingData.class);
//				npc.offer(Keys.AI_ENABLED, false);
//				player.sendMessage(Text.of(TextColors.YELLOW, "The selected NPC will no longer walk."));
//			}
//			else {
//				npc.offer(new WalkingData());
//				npc.offer(Keys.AI_ENABLED, true);
//				player.sendMessage(Text.of(TextColors.YELLOW, "The selected NPC will now walk, once move points are added."));
//			}
//		}
//		else { player.sendMessage(Text.of(TextColors.RED, "The selected NPC can not walk!")); }
//	}
//}