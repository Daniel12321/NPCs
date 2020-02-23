package me.mrdaniel.npcs.commands.main;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.commands.PlayerCommand;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class CommandDeselect extends PlayerCommand {

	@Override
	public void execute(final Player p, final CommandContext args) throws CommandException {
		if (NPCs.getInstance().getSelectedManager().deselect(p.getUniqueId())) {
			for (int i = 0; i < 20; i++) {
				p.sendMessage(Text.EMPTY);
			}
		}
	}

	public CommandSpec build() {
		return CommandSpec.builder()
				.description(Text.of(TextColors.GOLD, "NPCs | Deselect"))
				.permission("npc.edit.deselect")
				.executor(this)
				.build();
	}
}
