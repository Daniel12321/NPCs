package me.mrdaniel.npcs.commands.main;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;

import com.flowpowered.math.vector.Vector3d;
import com.flowpowered.math.vector.Vector3f;

import me.mrdaniel.npcs.catalogtypes.menupagetype.PageTypes;
import me.mrdaniel.npcs.catalogtypes.optiontype.OptionTypes;
import me.mrdaniel.npcs.commands.NPCCommand;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.utils.Position;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class CommandMove extends NPCCommand {

	public CommandMove() {
		super(PageTypes.MAIN);
	}

	@Override
	public void execute(final Player p, final NPCAble npc, final CommandContext args) throws CommandException {
		Vector3d loc = p.getLocation().getPosition();
		Vector3f rot = p.getHeadRotation().toFloat();
		OptionTypes.POSITION.writeToFileAndNPC(npc, new Position(loc.getX(), loc.getY(), loc.getZ(), rot.getY(), rot.getX()));
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
