package me.mrdaniel.npcs.manager.placeholder;

import java.util.NoSuchElementException;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.io.Config;
import me.mrdaniel.npcs.manager.PlaceHolderManager;
import me.mrdaniel.npcs.utils.TextUtils;
import me.rojo8399.placeholderapi.PlaceholderService;

public class PlaceHolderAPIManager implements PlaceHolderManager {

	private final PlaceholderService service;
	private final String msg_format;

	public PlaceHolderAPIManager(@Nonnull final NPCs npcs, @Nonnull final Config config) throws NoClassDefFoundError, ClassNotFoundException, NoSuchElementException {
		this.service = npcs.getGame().getServiceManager().provide(PlaceholderService.class).get();

		this.msg_format = config.getNode("messages", "npc_message_format").getString("%npc_name%&7: %message%");
	}

	@Override
	@Nonnull
	public String formatCMD(@Nonnull final Player p, @Nonnull final String txt) {
		return TextUtils.toString(this.service.replacePlaceholders(p, txt));
	}

	@Override
	public Text formatMSG(@Nonnull final Player p, @Nonnull final String txt, @Nonnull final String npc_name) {
		return this.service.replacePlaceholders(p, this.msg_format.replace("%message%", txt).replace("%npc_name%", npc_name));
	}
}