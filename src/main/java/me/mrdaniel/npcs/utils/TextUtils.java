package me.mrdaniel.npcs.utils;

import javax.annotation.Nonnull;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.serializer.TextSerializers;

public class TextUtils {

	private static Object[] PREFIX = {TextColors.DARK_GRAY, "[", TextColors.GOLD, "NPCs", TextColors.DARK_GRAY, "] ", TextColors.YELLOW};

	@Nonnull
	public static Text getMessage(@Nonnull final Object... objects) {
		Object[] txt = new Object[objects.length+7];
		for (int i = 0; i < 7; i++) { txt[i] = PREFIX[i]; }
		for (int i = 0; i < objects.length; i++) { txt[i+7] = objects[i]; }
		return Text.of(txt);
	}

	@Nonnull
	public static Text toText(@Nonnull final String message) {
		return TextSerializers.formattingCode('&').deserialize(message);
	}

	@Nonnull
	public static String toString(@Nonnull final Text text) {
		return TextSerializers.formattingCode('&').serialize(text);
	}
}