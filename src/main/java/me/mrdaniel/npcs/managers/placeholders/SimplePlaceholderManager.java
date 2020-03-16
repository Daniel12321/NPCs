package me.mrdaniel.npcs.managers.placeholders;

import me.mrdaniel.npcs.io.hocon.config.MainConfig;
import me.mrdaniel.npcs.utils.TextUtils;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

public class SimplePlaceholderManager implements PlaceholderHandler {

	private final String msg_format;
	private final String choice_format;

	public SimplePlaceholderManager(MainConfig config) {
		this.msg_format = config.getMessages().getNpcMessageFormat();
		this.choice_format = config.getMessages().getNpcChoiceFormat();
	}

	@Override
	public String formatCommand(Player p, String txt) {
		return this.format(p, txt);
	}

	@Override
	public Text formatNPCMessage(Player p, String message, String npc_name) {
		return TextUtils.toText(this.format(p, (this.msg_format + message).replace("%npc_name%", npc_name)));
	}

	@Override
	public Text formatChoiceMessage(Player p, Text choices) {
		return Text.builder().append(TextUtils.toText(this.format(p, this.choice_format)), choices).build();
	}

	private String format(Player p, String message) {
		return message
				.replace("%player_name%", p.getName())
				.replace("%player_uuid%", p.getUniqueId().toString())
				.replace("%player_world%", p.getWorld().getName());
	}
}
