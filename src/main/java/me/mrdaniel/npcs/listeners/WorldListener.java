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
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.Tristate;

import me.mrdaniel.npcs.NPCObject;
import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.data.npc.NPCData;
import me.mrdaniel.npcs.event.NPCEvent;
import me.mrdaniel.npcs.exception.NPCException;

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
			Living npc = (Living) e.getTargetEntity();
			e.setCancelled(true);
			if (e instanceof InteractEntityEvent.Secondary.MainHand) {
				if (p.get(Keys.IS_SNEAKING).orElse(false) && p.hasPermission("npc.edit.select")) {
					try {  super.getNPCs().getNPCManager().select(p, npc); p.sendMessage(Text.of(TextColors.DARK_GRAY, "[", TextColors.GOLD, "NPCs", TextColors.DARK_GRAY, "] ", TextColors.YELLOW, "You selected an NPC.")); }
					catch (final NPCException exc) { p.sendMessage(Text.of(TextColors.RED, exc.getMessage())); }
				}
				else if (data.canInteract()) {
					if (!super.getGame().getEventManager().post(new NPCEvent.Interact(super.getContainer(), p, npc))) {
						e.setCancelled(false);
						data.getActions().execute(super.getNPCs(), p);
					}
				}
			}
		});
	}

	@Listener
	public void onQuit(final ClientConnectionEvent.Disconnect e) {
		super.getNPCs().getNPCManager().deselect(e.getTargetEntity().getUniqueId());
	}
}