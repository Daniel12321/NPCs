package me.mrdaniel.npcs.commands.main;

import me.mrdaniel.npcs.commands.NPCCommand;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.io.INPCData;
import me.mrdaniel.npcs.menu.chat.npc.PropertiesChatMenu;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class CommandMount extends NPCCommand {

	public CommandMount() {
		super(PropertiesChatMenu::new, true);
	}

	@Override
	public void execute(Player p, INPCData data, NPCAble npc, CommandContext args) throws CommandException {
		((Living)npc).addPassenger(p);

		// TODO: Remove
//		Living living = (Living) npc;
//		living.addPassenger(p);
//		Task.builder().delayTicks(0).execute(() -> {
//			living.setVelocity(new Vector3d(0, 0, 0));
//			Position pos = npc.getNPCPosition();
//			((Entity)npc).setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(), pos.getYaw(), pos.getPitch());
//		}).submit(NPCs.getInstance());
	}

	public CommandSpec build() {
		return CommandSpec.builder()
				.description(Text.of(TextColors.GOLD, "NPCs | Mount"))
				.permission("npc.mount")
				.arguments(NPCCommand.ID_ARG)
				.executor(this)
				.build();
	}
}
