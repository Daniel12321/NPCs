package me.mrdaniel.npcs.utils;

import javax.annotation.Nonnull;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

public class TextUtils {

	@Nonnull
	public static Text toText(@Nonnull final String message) {
		return TextSerializers.formattingCode('&').deserialize(message);
	}

	@Nonnull
	public static String toString(@Nonnull final Text text) {
		return TextSerializers.formattingCode('&').serialize(text);
	}
}