package me.mrdaniel.npcs.managers.placeholders;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.io.Config;
import me.mrdaniel.npcs.utils.TextUtils;
import me.rojo8399.placeholderapi.PlaceholderService;

public class PlaceholderAPIManager implements PlaceholderHandler {

	private final PlaceholderService service;
	private final String msg_format;
	private final String choice_format;

	public PlaceholderAPIManager() {
		Config config = NPCs.getInstance().getConfig();

		this.service = NPCs.getInstance().getGame().getServiceManager().provide(PlaceholderService.class).get();
		this.msg_format = config.getNode("messages", "npc_message_format").getString("%npc_name%&7: ");
		this.choice_format = config.getNode("messages", "npc_choice_format").getString("&6&lChoose: ");
	}

	@Override
	public String formatCommand(@Nonnull final Player p, @Nonnull final String txt) {
		return TextUtils.toString(this.format(p, txt));
	}

	@Override
	public Text formatNPCMessage(@Nonnull final Player p, @Nonnull final String message, @Nonnull final String npc_name) {
		return this.format(p, this.msg_format.concat(message).replace("%npc_name%", npc_name));
	}

	@Override
	public Text formatChoiceMessage(@Nonnull final Player p, @Nonnull final Text choices) {
		return Text.builder().append(this.format(p, this.choice_format), choices).build();
	}

	private Text format(@Nonnull final Player p, @Nonnull final String txt) {
		return this.service.replacePlaceholders(txt, p, p);
	}
}