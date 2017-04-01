package me.mrdaniel.npcs.manager;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nonnull;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.living.Human;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;

import com.google.common.collect.Maps;

import me.mrdaniel.npcs.NPCObject;
import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.data.npc.NPCData;
import me.mrdaniel.npcs.io.Config;
import me.mrdaniel.npcs.utils.ServerUtils;

public class NPCManager extends NPCObject {

	private final Map<UUID, Living> selected;

	public NPCManager(@Nonnull final NPCs npcs, @Nonnull final Config config) {
		super(npcs);

		this.selected = Maps.newHashMap();

		Task.builder().delayTicks(200).intervalTicks(config.getNode("npc_update_ticks").getInt(1)).execute(() -> super.getServer().getWorlds().forEach(w -> w.getEntities().forEach(ent -> ent.get(NPCData.class).ifPresent(data -> data.tick((Living)ent))))).submit(super.getNPCs());
	}

	public void spawn(@Nonnull final Player p, @Nonnull final EntityType type) {
		Living l = (Living) p.getWorld().createEntity(type, p.getLocation().getPosition());
		l.setRotation(p.getRotation());
		l.setHeadRotation(p.getHeadRotation());
		l.offer(Keys.AI_ENABLED, false);
		if (l instanceof Human) { l.offer(Keys.CUSTOM_NAME_VISIBLE, true); l.offer(Keys.DISPLAY_NAME, Text.of("Steve")); }
		l.offer(new NPCData());

		p.getWorld().spawnEntity(l, ServerUtils.getSpawnCause(l));
		this.selected.put(p.getUniqueId(), l);
	}

	@Nonnull
	public Optional<Living> getSelected(@Nonnull final UUID uuid) {
		return Optional.ofNullable(this.selected.get(uuid));
	}

	public void select(@Nonnull final UUID uuid, @Nonnull final Living npc) {
		this.selected.put(uuid, npc);
	}

	public void deselect(@Nonnull final UUID uuid) {
		this.selected.remove(uuid);
	}

	public void deselect(@Nonnull final Living npc) {
		this.selected.remove(npc);
	}
}