package me.mrdaniel.npcs.data.npc.actions;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.player.Player;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.data.npc.actions.actions.Action;

public class IterateActions extends ListActions {

	public IterateActions(@Nonnull final List<Action> actions, @Nonnull final Map<UUID, Integer> current) {
		super("iterate", actions, current);
	}

	@Override
	public void execute(final NPCs npcs, final Player p, final Living npc) {
		int current = Optional.ofNullable(this.current.get(p.getUniqueId())).orElse(0);

		if (current >= this.current.size()) { current = 0; }

		this.actions.get(current).execute(npcs, p, npc);
		this.current.put(p.getUniqueId(), current+1);
	}
}