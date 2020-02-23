package me.mrdaniel.npcs.utils;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.util.function.Consumer;

public class TextUtils {

	public static Text toText(String msg) {
		return TextSerializers.formattingCode('&').deserialize(msg);
	}

	public static String toString(Text txt) {
		return TextSerializers.formattingCode('&').serialize(txt);
	}

	public static String capitalize(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}

	public static Text getToggleText(String name, String cmd, boolean value) {
		return Text.builder().append(Text.of(TextColors.GOLD, name, ": "), 
				Text.builder().append(Text.of(value ? TextColors.DARK_GREEN : TextColors.RED, value ? "Enabled" : "Disabled"))
				.onHover(TextActions.showText(Text.of(value ? TextColors.RED : TextColors.DARK_GREEN, value ? "Disable" : "Enable")))
				.onClick(TextActions.runCommand(cmd + " " + !value)).build())
				.build();
	}

	public static Text getOptionsText(String name, String cmd, String value) {
		return Text.builder()
				.append(Text.of(TextColors.GOLD, name, ": ", TextColors.AQUA, value))
				.onHover(TextActions.showText(Text.of(TextColors.YELLOW, "Change")))
				.onClick(TextActions.suggestCommand(cmd))
				.build();
	}

	public static Text getButton(String name, Consumer<CommandSource> cmd) {
		return Text.builder().append(Text.of(TextColors.RED, name))
				.onHover(TextActions.showText(Text.of(TextColors.YELLOW, "Open")))
				.onClick(TextActions.executeCallback(cmd)).build();
	}
}
