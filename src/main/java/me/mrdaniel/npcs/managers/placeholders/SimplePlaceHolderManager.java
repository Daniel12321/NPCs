package me.mrdaniel.npcs.managers.placeholders;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import me.mrdaniel.npcs.io.Config;
import me.mrdaniel.npcs.managers.PlaceHolderManager;
import me.mrdaniel.npcs.utils.TextUtils;

public class SimplePlaceHolderManager implements PlaceHolderManager {

	private final String msg_format;
	private final String choice_format;

	public SimplePlaceHolderManager(@Nonnull final Config config) {
		this.msg_format = config.getNode("messages", "npc_message_format").getString("%npc_name%&7: %message%");
		this.choice_format = config.getNode("messages", "npc_choice_format").getString("&6&lChoose: &c&%choices%");
	}

	@Override
	public String formatCommand(@Nonnull final Player p, @Nonnull final String txt) {
		return this.format(p, txt);
	}

	@Override
	public Text formatNPCMessage(@Nonnull final Player p, @Nonnull final String message, @Nonnull final String npc_name) {
		return TextUtils.toText(this.format(p, this.msg_format)
				.replace("%message%", message)
				.replace("%npc_name%", npc_name));
	}

	@Override
	public Text formatChoiceMessage(@Nonnull final Player p, @Nonnull final Text choices) {
		String[] formatted = this.format(p, this.choice_format).split("%choices%");
		if (formatted.length < 2) { return Text.builder().append(TextUtils.toText(formatted[0])).append(choices).build(); }
		return Text.builder().append(TextUtils.toText(formatted[0])).append(choices).append(TextUtils.toText(formatted[1])).build();
	}

	@Nonnull
	private String format(@Nonnull final Player p, @Nonnull final String message) {
		return message
				.replace("%player_name%", p.getName())
				.replace("%player_uuid%", p.getUniqueId().toString())
				.replace("%player_world%", p.getWorld().getName());
	}
}