package me.mrdaniel.npcs.utils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.serializer.TextSerializers;

public class TextUtils {

	@Nullable
	public static Text toText(@Nullable final String msg) {
		return msg == null ? null : TextSerializers.formattingCode('&').deserialize(msg);
	}

	@Nullable
	public static String toString(@Nullable final Text txt) {
		return txt == null ? null : TextSerializers.formattingCode('&').serialize(txt);
	}

	@SuppressWarnings("deprecation")
	@Nullable
	public static String toLegacy(@Nullable final Text txt) {
		return txt == null ? null : TextSerializers.LEGACY_FORMATTING_CODE.serialize(txt);
	}

	@Nonnull
	public static String capitalize(@Nonnull final String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1, str.length());
	}

	@Nonnull
	public static Text getToggleText(@Nonnull final String name, @Nonnull final String cmd, final boolean value) {
		return Text.builder()
				.append(Text.of(TextColors.GOLD, name, ": ", value ? TextColors.DARK_GREEN : TextColors.RED, value ? "Enabled" : "Disabled"))
				.onHover(TextActions.showText(Text.of(value ? TextColors.RED : TextColors.DARK_GREEN, value ? "Disable" : "Enable")))
				.onClick(TextActions.runCommand(cmd + " " + !value))
				.build();
	}

	@Nonnull
	public static Text getOptionsText(@Nonnull final String name, @Nonnull final String cmd, @Nonnull final String value) {
		return Text.builder()
				.append(Text.of(TextColors.GOLD, name, ": ", TextColors.AQUA, value))
				.onHover(TextActions.showText(Text.of(TextColors.YELLOW, "Change")))
				.onClick(TextActions.suggestCommand(cmd))
				.build();
	}
}