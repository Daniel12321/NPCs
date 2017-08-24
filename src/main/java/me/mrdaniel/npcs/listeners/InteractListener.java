package me.mrdaniel.npcs.listeners;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.InteractEntityEvent;
import org.spongepowered.api.event.filter.cause.Root;

import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.managers.MenuManager;

public class InteractListener {

	@Listener
	public void onNPCInteract(final InteractEntityEvent.Secondary.OffHand e, @Root final Player p) {
		if (e.getTargetEntity() instanceof NPCAble) {
			NPCAble npc = (NPCAble) e.getTargetEntity();
			if (npc.getNPCFile() != null) {
				if (p.get(Keys.IS_SNEAKING).orElse(false) && p.hasPermission("npc.edit.select")) {
					MenuManager.getInstance().select(p, npc);
				}
				else { e.setCancelled(!npc.getNPCFile().getInteract()); }
			}
		}
	}
}