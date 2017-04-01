package me.mrdaniel.npcs.listeners;

import javax.annotation.Nonnull;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.entity.CollideEntityEvent;
import org.spongepowered.api.event.entity.DamageEntityEvent;
import org.spongepowered.api.event.entity.InteractEntityEvent;
import org.spongepowered.api.event.filter.IsCancelled;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.util.Tristate;

import me.mrdaniel.npcs.NPCObject;
import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.data.npc.NPCData;
import me.mrdaniel.npcs.utils.TextUtils;

public class WorldListener extends NPCObject {

	public WorldListener(@Nonnull final NPCs npcs) {
		super(npcs);
	}

	@IsCancelled(value = Tristate.FALSE)
	@Listener(order = Order.EARLY)
	public void onCollide(final CollideEntityEvent e) {
		e.getEntities().forEach(ent -> ent.get(NPCData.class).ifPresent(data -> e.setCancelled(true)));
	}

	@IsCancelled(value = Tristate.FALSE)
	@Listener(order = Order.EARLY)
	public void onDamage(final DamageEntityEvent e) {
		e.getTargetEntity().get(NPCData.class).ifPresent(data -> e.setCancelled(true));
	}

	@IsCancelled(value = Tristate.FALSE)
	@Listener(order = Order.EARLY)
	public void onClick(final InteractEntityEvent e, @Root final Player p) {
		e.getTargetEntity().get(NPCData.class).ifPresent(data -> {
			e.setCancelled(true);
			if (e instanceof InteractEntityEvent.Secondary.MainHand) {
				if (p.get(Keys.IS_SNEAKING).orElse(false) && p.hasPermission("npc.edit.select")) {
					super.getNPCs().getNPCManager().select(p.getUniqueId(), (Living) e.getTargetEntity());
					p.sendMessage(TextUtils.getMessage("You selected an NPC."));
				}
				else if (data.canInteract()) { e.setCancelled(false); }
			}
		});
	}

	@Listener
	public void onQuit(final ClientConnectionEvent.Disconnect e) {
		super.getNPCs().getNPCManager().deselect(e.getTargetEntity().getUniqueId());
	}
}