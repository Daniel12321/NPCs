package me.mrdaniel.npcs.listeners;

import javax.annotation.Nonnull;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.InteractEntityEvent;
import org.spongepowered.api.event.filter.cause.Root;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.exceptions.NPCException;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.managers.ActionManager;
import me.mrdaniel.npcs.managers.MenuManager;

public class InteractListener {

	// This doesn't work for villagers and horses
	@Listener
	public void onEntityInteract(final InteractEntityEvent.Secondary.OffHand e, @Root final Player p) {
		if (e.getTargetEntity() instanceof NPCAble) {
			e.setCancelled(this.onEntityInteract((NPCAble) e.getTargetEntity(), p));
		}
	}

//	// Doesn't work yet
//	@Listener
//	public void onHorseMount(final RideEntityEvent.Mount e, @Root final Player p) {
//		e.setCancelled(this.onEntityInteract((NPCAble) e.getTargetEntity(), p));
//	}
//
//	// Doesn't work yet
//	@Listener
//	public void onInventoryOpen(final InteractInventoryEvent.Open e, @Root final Player p, @First final Villager villager) {
//		e.setCancelled(this.onEntityInteract((NPCAble) villager, p));
//	}

	/**
	 * @return whether to cancel the original event
	 */
	private boolean onEntityInteract(@Nonnull final NPCAble npc, @Nonnull final Player p) {
		if (npc.getNPCFile() == null) {
			return false;
		}

		if (p.get(Keys.IS_SNEAKING).orElse(false) && p.hasPermission("npc.edit.select")) {
			MenuManager.getInstance().select(p, npc);
			return true;
		}

		try { ActionManager.getInstance().execute(p.getUniqueId(), npc.getNPCFile()); }
		catch ( final NPCException exc) { NPCs.getInstance().getLogger().error("Failed to execute action for npc " + npc.getNPCFile().getId()); }

		return !npc.canNPCInteract();
	}
}