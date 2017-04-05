package me.mrdaniel.npcs.manager.placeholder;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.player.Player;

import me.mrdaniel.npcs.manager.PlaceHolderManager;

public class SimplePlaceHolderManager implements PlaceHolderManager {

	@Override
	public String format(@Nonnull final Player p, @Nonnull final String txt) {
		return txt
				.replace("%player_name%", p.getName())
				.replace("%player_uuid%", p.getUniqueId().toString())
				.replace("%player_world%", p.getWorld().getName());
	}
}