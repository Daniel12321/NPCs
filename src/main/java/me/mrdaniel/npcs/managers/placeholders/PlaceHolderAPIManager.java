package me.mrdaniel.npcs.managers.placeholders;

import java.util.NoSuchElementException;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.io.Config;
import me.mrdaniel.npcs.managers.PlaceHolderManager;
import me.mrdaniel.npcs.utils.TextUtils;
import me.rojo8399.placeholderapi.PlaceholderService;

public class PlaceHolderAPIManager implements PlaceHolderManager {

	private final PlaceholderService service;
	private final String msg_format;
	private final String choice_format;

	public PlaceHolderAPIManager(@Nonnull final NPCs npcs, @Nonnull final Config config) throws NoClassDefFoundError, ClassNotFoundException, NoSuchElementException {
		this.service = npcs.getGame().getServiceManager().provide(PlaceholderService.class).get();

		this.msg_format = config.getNode("messages", "npc_message_format").getString("%npc_name%&7: %message%");
		this.choice_format = config.getNode("messages", "npc_choice_format").getString("&6&lChoose: &c&%choices%");
	}

	@Override
	@Nonnull
	public String formatCommand(@Nonnull final Player p, @Nonnull final String txt) {
		return TextUtils.toString(this.format(p, txt));
	}

	@Nonnull
	@Override
	public Text formatNPCMessage(@Nonnull final Player p, @Nonnull final String message, @Nonnull final String npc_name) {
		return this.format(p, this.msg_format.replace("%message%", message).replace("%npc_name%", npc_name));
	}

	@Nonnull
	@Override
	public Text formatChoiceMessage(@Nonnull final Player p, @Nonnull final Text choices) {
		String[] formatted = TextUtils.toString(this.format(p, this.choice_format)).split("%choices%");
		if (formatted.length < 2) { return Text.builder().append(TextUtils.toText(formatted[0])).append(choices).build(); }
		return Text.builder().append(TextUtils.toText(formatted[0])).append(choices).append(TextUtils.toText(formatted[1])).build();
	}

	@Nonnull
	private Text format(@Nonnull final Player p, @Nonnull final String txt) {
		return this.service.replacePlaceholders(p, txt);
	}
}