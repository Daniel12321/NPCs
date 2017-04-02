package me.mrdaniel.npcs.utils;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.chat.ChatTypes;
import org.spongepowered.api.text.serializer.TextSerializers;

import ninja.leaping.configurate.ConfigurationNode;

public class TextUtils {

	private static String PREFIX = "&8[&6NPCs&8]&e";
	private static boolean ACTION_BAR = true;

	public static void setValues(@Nonnull final ConfigurationNode node) {
		PREFIX = node.getNode("prefix").getString(PREFIX);
		if (!PREFIX.equals("")) { PREFIX += " "; }
		ACTION_BAR = node.getNode("actionbar").getBoolean(ACTION_BAR);
	}

	@Nonnull
	public static void sendMessage(@Nonnull final Player p, @Nonnull final String message) {
		p.sendMessage(ACTION_BAR ? ChatTypes.ACTION_BAR : ChatTypes.CHAT, toText(PREFIX + message));
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