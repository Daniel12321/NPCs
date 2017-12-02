package me.mrdaniel.npcs.utils;

import java.util.function.Consumer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.serializer.TextSerializers;

public class TextUtils {

	@Nullable
	public static Text toText(@Nonnull final String msg) {
		return TextSerializers.formattingCode('&').deserialize(msg);
	}

	@Nullable
	public static String toString(@Nonnull final Text txt) {
		return TextSerializers.formattingCode('&').serialize(txt);
	}

	public static String capitalize(@Nonnull final String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1, str.length());
	}

	public static Text getToggleText(@Nonnull final String name, @Nonnull final String cmd, final boolean value) {
		return Text.builder().append(Text.of(TextColors.GOLD, name, ": "), 
				Text.builder().append(Text.of(value ? TextColors.DARK_GREEN : TextColors.RED, value ? "Enabled" : "Disabled"))
				.onHover(TextActions.showText(Text.of(value ? TextColors.RED : TextColors.DARK_GREEN, value ? "Disable" : "Enable")))
				.onClick(TextActions.runCommand(cmd + " " + Boolean.toString(!value))).build())
				.build();
	}

	public static Text getOptionsText(@Nonnull final String name, @Nonnull final String cmd, @Nonnull final String value) {
		return Text.builder()
				.append(Text.of(TextColors.GOLD, name, ": ", TextColors.AQUA, value))
				.onHover(TextActions.showText(Text.of(TextColors.YELLOW, "Change")))
				.onClick(TextActions.suggestCommand(cmd))
				.build();
	}

	public static Text getButton(@Nonnull final String name, @Nonnull final Consumer<CommandSource> cmd) {
		return Text.builder().append(Text.of(TextColors.RED, name))
				.onHover(TextActions.showText(Text.of(TextColors.YELLOW, "Open")))
				.onClick(TextActions.executeCallback(cmd)).build();
	}
}