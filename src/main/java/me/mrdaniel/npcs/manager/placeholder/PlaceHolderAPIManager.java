package me.mrdaniel.npcs.manager.placeholder;

import java.util.NoSuchElementException;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.player.Player;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.manager.PlaceHolderManager;
import me.mrdaniel.npcs.utils.TextUtils;
import me.rojo8399.placeholderapi.PlaceholderService;

public class PlaceHolderAPIManager implements PlaceHolderManager {

	private final PlaceholderService service;

	public PlaceHolderAPIManager(@Nonnull final NPCs npcs) throws NoClassDefFoundError, ClassNotFoundException, NoSuchElementException {
		this.service = npcs.getGame().getServiceManager().provide(PlaceholderService.class).get();
	}

	@Override
	@Nonnull
	public String format(@Nonnull final Player p, @Nonnull final String txt) {
		return TextUtils.toString(this.service.replacePlaceholders(p, txt));
	}
}