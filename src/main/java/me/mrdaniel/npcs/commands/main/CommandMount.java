package me.mrdaniel.npcs.commands.main;

import com.flowpowered.math.vector.Vector3d;
import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.catalogtypes.menupagetype.PageTypes;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyTypes;
import me.mrdaniel.npcs.commands.NPCCommand;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.utils.Position;
import net.minecraft.entity.Entity;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class CommandMount extends NPCCommand {

	public CommandMount() {
		super(PageTypes.MAIN);
	}

	@Override
	public void execute(final Player p, final NPCAble npc, final CommandContext args) throws CommandException {
		Living living = (Living) npc;
		living.addPassenger(p);
		Task.builder().delayTicks(0).execute(() -> {
			living.setVelocity(new Vector3d(0, 0, 0));
			Position pos = npc.getPosition();
			((Entity)npc).setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(), pos.getYaw(), pos.getPitch());
		}).submit(NPCs.getInstance());
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
