package me.mrdaniel.npcs.commands.main;

import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.commands.NPCCommand;
import me.mrdaniel.npcs.events.NPCEditEvent;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.menu.chat.npc.NPCChatMenu;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.function.Function;

public class CommandEdit<T> extends NPCCommand {

	private final PropertyType<T> option;

	public CommandEdit(Function<NPCAble, NPCChatMenu> menu, PropertyType<T> option) {
		super(menu);

		this.option = option;
	}

	@Override
	public void execute(Player p, NPCAble npc, CommandContext args) throws CommandException {
		if (!this.option.isSupported(npc)) {
			throw new CommandException(Text.of(TextColors.RED, "That NPC does not support that option!"));
		}
		if (new NPCEditEvent(p, npc).post()) {
			throw new CommandException(Text.of(TextColors.RED, "Event was cancelled!"));
		}

		npc.setNPCProperty(this.option, args.<T>getOne(this.option.getId()).orElseThrow(() -> new CommandException(Text.of(TextColors.RED, "Invalid value!")))).saveNPC();
	}

	public static <T> CommandSpec build(Function<NPCAble, NPCChatMenu> page, PropertyType<T> option) {
		return CommandSpec.builder()
				.description(Text.of(TextColors.GOLD, "NPCs | Set ", option.getName()))
				.permission("npc.edit." + option.getId())
				.arguments(option.getArgs())
				.executor(new CommandEdit<>(page, option))
				.build();
	}
}
