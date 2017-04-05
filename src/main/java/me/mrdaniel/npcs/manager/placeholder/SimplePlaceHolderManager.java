package me.mrdaniel.npcs.manager.placeholder;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import me.mrdaniel.npcs.io.Config;
import me.mrdaniel.npcs.manager.PlaceHolderManager;
import me.mrdaniel.npcs.utils.TextUtils;

public class SimplePlaceHolderManager implements PlaceHolderManager {

	private final String msg_format;

	public SimplePlaceHolderManager(@Nonnull final Config config) {
		this.msg_format = config.getNode("messages", "npc_message_format").getString("%npc_name%&7: %message%");
	}

	@Override
	public String formatCMD(@Nonnull final Player p, @Nonnull final String txt) {
		return txt
				.replace("%player_name%", p.getName())
				.replace("%player_uuid%", p.getUniqueId().toString())
				.replace("%player_world%", p.getWorld().getName());
	}

	@Override
	public Text formatMSG(@Nonnull final Player p, @Nonnull final String txt, @Nonnull final String npc_name) {
		return TextUtils.toText(this.msg_format
				.replace("%message%", txt)
				.replace("%npc_name%", npc_name)
				.replace("%player_name%", p.getName())
				.replace("%player_uuid%", p.getUniqueId().toString())
				.replace("%player_world%", p.getWorld().getName()));
	}
}