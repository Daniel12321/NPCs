package me.mrdaniel.npcs.commands.main;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

import com.flowpowered.math.vector.Vector3i;

import me.mrdaniel.npcs.NPCObject;
import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.exceptions.NPCException;
import me.mrdaniel.npcs.io.NPCFile;

public class CommandList extends NPCObject implements CommandExecutor {

	public CommandList(@Nonnull final NPCs npcs) {
		super(npcs);
	}

	@Override
	public CommandResult execute(final CommandSource src, final CommandContext args) throws CommandException {
		src.sendMessage(Text.of(Text.of(TextColors.YELLOW, "---------------=====[ ", TextColors.RED, "NPC List", TextColors.YELLOW, " ]=====---------------")));
		super.getNPCs().getNPCManager().getAll().forEach(file -> src.sendMessage(this.getNPCText(file)));
		src.sendMessage(Text.of(TextColors.YELLOW, "--------------------------------------------------"));
		return CommandResult.success();
	}

	@Nonnull
	private Text getNPCText(@Nonnull final NPCFile file) {
		final Vector3i pos = file.getPosition().toInt();

		Text.Builder b = Text.builder().append(Text.of(TextColors.BLUE, file.getId(), ": "), 
				Text.of(TextColors.GOLD, "Location=", TextColors.RED, file.getWorldName(), " ", pos.getX(), " ", pos.getY(), " ", pos.getZ()));
		file.getType().ifPresent(type -> b.append(Text.of(TextColors.GOLD, " Type=", TextColors.RED, type.getName())));
		file.getName().ifPresent(name -> b.append(Text.of(TextColors.GOLD, " Name=", TextColors.RED), name));
		return b.onHover(TextActions.showText(Text.of(TextColors.YELLOW, "Select")))
				.onClick(TextActions.executeCallback(src -> {
					try { super.getNPCs().getMenuManager().select((Player)src, file); }
					catch (final NPCException exc) { src.sendMessage(Text.of(TextColors.RED, exc.getMessage())); }
				})).build();
	}
}