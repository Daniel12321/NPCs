package me.mrdaniel.npcs.commands.edit;

import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyTypes;
import me.mrdaniel.npcs.commands.NPCCommand;
import me.mrdaniel.npcs.events.NPCEditEvent;
import me.mrdaniel.npcs.mixin.interfaces.NPCAble;
import me.mrdaniel.npcs.io.INPCData;
import me.mrdaniel.npcs.menu.chat.ChatMenu;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import javax.annotation.Nullable;
import java.util.function.Function;

public class CommandEdit<T> extends NPCCommand {

	private final PropertyType<T> option;

	public CommandEdit(Function<INPCData, ChatMenu> menu, PropertyType<T> option) {
		super(menu, false);

		this.option = option;
	}

	@Override
	public void execute(Player p, INPCData data, @Nullable NPCAble npc, CommandContext args) throws CommandException {
		if (!this.option.isSupported(data.getProperty(PropertyTypes.TYPE).get())) {
			throw new CommandException(Text.of(TextColors.RED, "That NPC does not support that option!"));
		} else if (Sponge.getEventManager().post(new NPCEditEvent(p, data, npc))) {
			throw new CommandException(Text.of(TextColors.RED, "Event was cancelled!"));
		}

		T value = args.<T>getOne(this.option.getId()).orElseThrow(() -> new CommandException(Text.of(TextColors.RED, "Invalid value!")));
		if (npc == null) {
			data.setProperty(this.option, value).save();
		} else {
			npc.setProperty(this.option, value).save();
		}
	}

	public static <T> CommandSpec build(Function<INPCData, ChatMenu> page, PropertyType<T> option) {
		return CommandSpec.builder()
				.description(Text.of(TextColors.GOLD, "NPCs | Set ", option.getName()))
				.permission("npc.edit." + option.getId())
				.arguments(option.getArgs())
				.executor(new CommandEdit<>(page, option))
				.build();
	}
}
