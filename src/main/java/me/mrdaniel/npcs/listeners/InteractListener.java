package me.mrdaniel.npcs.listeners;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyTypes;
import me.mrdaniel.npcs.events.NPCInteractEvent;
import me.mrdaniel.npcs.events.NPCSelectEvent;
import me.mrdaniel.npcs.exceptions.NPCException;
import me.mrdaniel.npcs.io.hocon.config.MainConfig;
import me.mrdaniel.npcs.mixin.interfaces.NPCAble;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.animal.Horse;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.InteractEntityEvent;
import org.spongepowered.api.event.entity.RideEntityEvent;
import org.spongepowered.api.event.filter.cause.Root;

public class InteractListener {

	private boolean enableActionSystem;

	public InteractListener(MainConfig config) {
		this.enableActionSystem = config.enableActionSystem;
	}

	// This doesn't work for villagers (opens inventory) and horses (mounts them)
	@Listener
	public void onEntityInteract(InteractEntityEvent.Secondary.OffHand e, @Root Player p) {
		if (e.getTargetEntity() instanceof NPCAble) {
			e.setCancelled(this.onEntityInteract((NPCAble) e.getTargetEntity(), p));
		}
	}

	// Allows horses to be selected (Does still try to make you mount)
	@Listener
	public void onHorseMount(RideEntityEvent.Mount e, @Root Player p) {
		Entity target = e.getTargetEntity();
		if (target instanceof Horse) {
			e.setCancelled(this.onEntityInteract((NPCAble) e.getTargetEntity(), p));
		}
	}

//	@Listener
//	public void onInventoryOpen(InteractInventoryEvent.Open e) { // @Root Player p, @First Villager villager
//		System.out.println("Context: "+ e.getContext().toString());
//		System.out.println("Cause: "+ e.getCause().toString());
//		System.out.println("Source: " + e.getSource().toString());
//		e.setCancelled(this.onEntityInteract((NPCAble) villager, p));
//	}

	/**
	 * @return whether to cancel the original event
	 */
	private boolean onEntityInteract(NPCAble npc, Player p) {
		if (npc.getData() == null) {
			return false;
		} else if (p.get(Keys.IS_SNEAKING).orElse(false) && p.hasPermission("npc.edit.select")) {
			if (!Sponge.getEventManager().post(new NPCSelectEvent(p, npc.getData(), npc))) {
				NPCs.getInstance().getSelectedManager().select(p, npc.getData());
			}
			return true;
		} else if (Sponge.getEventManager().post(new NPCInteractEvent(p, npc.getData(), npc))) {
			return true;
		}

		if (this.enableActionSystem) {
			try {
				NPCs.getInstance().getActionManager().execute(p.getUniqueId(), npc.getData());
			} catch (NPCException exc) {
				NPCs.getInstance().getLogger().error("Failed to execute action for npc " + npc.getData().getId());
			}
		}

		return !npc.getData().getProperty(PropertyTypes.INTERACT).orElse(true);
	}
}
