package me.mrdaniel.npcs.listeners;

import javax.annotation.Nonnull;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.Villager;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.InteractEntityEvent;
import org.spongepowered.api.event.entity.RideEntityEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.event.item.inventory.InteractInventoryEvent;

import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.managers.MenuManager;

public class InteractListener {

	@Listener
	public void onEntityInteract(final InteractEntityEvent.Secondary.OffHand e, @Root final Player p) {
		if (e.getTargetEntity() instanceof NPCAble) {
			NPCAble npc = (NPCAble) e.getTargetEntity();
			if (npc.getNPCFile() != null) {
				e.setCancelled(this.onNPCInteract(npc, p));
			}
		}
	}

	@Listener
	public void onHorseMount(final RideEntityEvent.Mount e, @Root final Player p) {
		NPCAble npc = (NPCAble) e.getTargetEntity();
		if (npc.getNPCFile() != null) {
			e.setCancelled(this.onNPCInteract(npc, p));			
		}
	}

	@Listener
	public void onInventoryOpen(final InteractInventoryEvent.Open e, @Root final Player p, @First final Villager villager) {
		NPCAble npc = (NPCAble) villager;
		if (npc.getNPCFile() != null) {
			e.setCancelled(this.onNPCInteract(npc, p));
		}
	}

	private boolean onNPCInteract(@Nonnull final NPCAble npc, @Nonnull final Player p) {
		if (p.get(Keys.IS_SNEAKING).orElse(false) && p.hasPermission("npc.edit.select")) {
			MenuManager.getInstance().select(p, npc);
			return true;
		}
		else { return !npc.canNPCInteract(); }
	}
}