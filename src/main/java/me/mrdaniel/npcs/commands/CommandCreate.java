package me.mrdaniel.npcs.commands;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.event.NPCEvent;

public class CommandCreate extends PlayerCommand {

	public CommandCreate(@Nonnull final NPCs npcs) {
		super(npcs);
	}

	@Override
	public void execute(final Player p, final CommandContext args) throws CommandException {
		EntityType type = args.<EntityType>getOne("type").orElse(EntityTypes.HUMAN);

		if (super.getGame().getEventManager().post(new NPCEvent.Create(super.getContainer(), p, type))) {
			throw new CommandException(Text.of(TextColors.RED, "Could not create NPC: Event was cancelled"));
		}
		super.getNPCs().getNPCManager().spawn(p, type);
	}
}