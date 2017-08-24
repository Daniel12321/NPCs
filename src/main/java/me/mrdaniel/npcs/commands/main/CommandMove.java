package me.mrdaniel.npcs.commands.main;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;

import com.flowpowered.math.vector.Vector3d;
import com.flowpowered.math.vector.Vector3f;

import me.mrdaniel.npcs.catalogtypes.menupagetype.PageTypes;
import me.mrdaniel.npcs.catalogtypes.optiontype.OptionTypes;
import me.mrdaniel.npcs.commands.NPCCommand;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.utils.Position;

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
}