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
import org.spongepowered.api.event.entity.SpawnEntityEvent;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.event.world.LoadWorldEvent;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.World;

import me.mrdaniel.npcs.NPCObject;
import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.data.npc.NPCData;
import me.mrdaniel.npcs.events.NPCEvent;
import me.mrdaniel.npcs.exceptions.NPCException;

public class WorldListener extends NPCObject {

	public WorldListener(@Nonnull final NPCs npcs) {
		super(npcs);
	}

	@Listener(order = Order.LATE)
	public void onLoadWorld(final LoadWorldEvent e) {
		World w = e.getTargetWorld();
		Task.builder().delayTicks(100).execute(() -> super.getNPCs().getNPCManager().load(w)).submit(super.getNPCs());
	}

	@Listener(order = Order.EARLY)
	public void onEntitySpawn(final SpawnEntityEvent e) {
		e.getEntities().forEach(ent -> ent.get(NPCData.class).ifPresent(data -> data.ifOld(super.getNPCs().getStartup(), () -> ent.remove())));
	}

	@Listener(order = Order.EARLY)
	public void onCollide(final CollideEntityEvent e) {
		e.getEntities().forEach(ent -> ent.get(NPCData.class).ifPresent(data -> e.setCancelled(true)));
	}

	@Listener(order = Order.EARLY)
	public void onDamage(final DamageEntityEvent e) {
		e.getTargetEntity().get(NPCData.class).ifPresent(data -> e.setCancelled(true));
	}

	@Listener(order = Order.EARLY)
	public void onClick(final InteractEntityEvent e, @Root final Player p) {
		e.getTargetEntity().get(NPCData.class).ifPresent(data -> {
			Living npc = (Living) e.getTargetEntity();
			e.setCancelled(!data.canInteract());

			if (e instanceof InteractEntityEvent.Secondary.MainHand) {
				super.getNPCs().getNPCManager().getFile(data.getId()).ifPresent(file -> {
					if (p.get(Keys.IS_SNEAKING).orElse(false) && p.hasPermission("npc.edit.select")) {
						super.getNPCs().getMenuManager().select(p, npc, file);
					}
					else {
						if (!super.getGame().getEventManager().post(new NPCEvent.Interact(super.getContainer(), p, npc, file))) {
							try { super.getNPCs().getActionManager().execute(p.getUniqueId(), file); }
							catch (final NPCException exc) { p.sendMessage(Text.of(TextColors.RED, "Failed to perform NPC actions: " + exc.getMessage())); }
						}
					}
				});
			}
		});
	}

	@Listener(order = Order.LATE)
	public void onQuit(final ClientConnectionEvent.Disconnect e) {
		super.getNPCs().getMenuManager().deselect(e.getTargetEntity().getUniqueId());
		super.getNPCs().getActionManager().removeChoosing(e.getTargetEntity().getUniqueId());
	}
}