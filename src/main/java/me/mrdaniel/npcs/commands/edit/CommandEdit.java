package me.mrdaniel.npcs.commands.edit;

import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyTypes;
import me.mrdaniel.npcs.commands.NPCCommand;
import me.mrdaniel.npcs.events.NPCEditEvent;
import me.mrdaniel.npcs.gui.chat.MenuPage;
import me.mrdaniel.npcs.io.INPCData;
import me.mrdaniel.npcs.mixin.interfaces.NPCAble;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import javax.annotation.Nullable;

public class CommandEdit<T> extends NPCCommand {

	private final PropertyType<T> property;

	public CommandEdit(Class<? extends MenuPage> menuPage, PropertyType<T> property) {
		super(menuPage, false);

		this.property = property;
	}

	@Override
	public boolean execute(Player p, INPCData data, @Nullable NPCAble npc, CommandContext args) throws CommandException {
		if (!this.property.isSupported(data.getProperty(PropertyTypes.TYPE).get())) {
			throw new CommandException(Text.of(TextColors.RED, "That NPC does not support that option!"));
		} else if (Sponge.getEventManager().post(new NPCEditEvent(p, data, npc))) {
			throw new CommandException(Text.of(TextColors.RED, "Event was cancelled!"));
		}

		T value = args.<T>getOne(this.property.getId()).orElseThrow(() -> new CommandException(Text.of(TextColors.RED, "Invalid value!")));
		if (npc == null) {
			data.setProperty(this.property, value).save();
		} else {
			npc.setProperty(this.property, value).save();
		}

		return true;
	}

	public static <T> CommandSpec build(Class<? extends MenuPage> page, PropertyType<T> option) {
		return CommandSpec.builder()
				.description(Text.of(TextColors.GOLD, "NPCs | Set ", option.getName()))
				.permission("npc.edit." + option.getId())
				.arguments(option.getArgs())
				.executor(new CommandEdit<>(page, option))
				.build();
	}
}
