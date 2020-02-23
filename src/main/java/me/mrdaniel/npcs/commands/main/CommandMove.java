package me.mrdaniel.npcs.commands.main;

import me.mrdaniel.npcs.commands.NPCCommand;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.menu.chat.npc.PropertiesChatMenu;
import me.mrdaniel.npcs.utils.Position;
import net.minecraft.entity.Entity;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class CommandMove extends NPCCommand {

	public CommandMove() {
		super(PropertiesChatMenu::new);
	}

	@Override
	public void execute(final Player p, final NPCAble npc, final CommandContext args) throws CommandException {
		Position pos = new Position(p.getWorld().getName(), p.getLocation().getPosition(), p.getHeadRotation());

		((Entity)npc).setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(), pos.getYaw(), pos.getPitch());
		npc.getNPCData().setNPCPosition(pos);
	}

	public CommandSpec build() {
		return CommandSpec.builder()
				.description(Text.of(TextColors.GOLD, "NPCs | Move"))
				.permission("npc.edit.move")
				.arguments(NPCCommand.ID_ARG)
				.executor(this)
				.build();
	}
}
