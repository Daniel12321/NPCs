package me.mrdaniel.npcs.commands.main;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import me.mrdaniel.npcs.commands.PlayerCommand;
import me.mrdaniel.npcs.managers.MenuManager;

public class CommandDeselect extends PlayerCommand {

	@Override
	public void execute(final Player p, final CommandContext args) throws CommandException {
		if (MenuManager.getInstance().deselect(p.getUniqueId())) {
			for (int i = 0; i < 20; i++) {
				p.sendMessage(Text.EMPTY);
			}
		}
	}
}