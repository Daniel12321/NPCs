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

public class OnlyOnceRepeatLastActions extends ListActions {

	public OnlyOnceRepeatLastActions(@Nonnull final List<Action> actions, @Nonnull final Map<UUID, Integer> current) {
		super("only_once_repeat_last", actions, current);
	}

	@Override
	public void execute(final NPCs npcs, final Player p, final Living npc) {
		int current = Optional.ofNullable(this.current.get(p.getUniqueId())).orElse(0);

		if (current < super.current.size()) {
			this.actions.get(current).execute(npcs, p, npc);
			this.current.put(p.getUniqueId(), current+1);
		}
		else if (super.actions.size() > 0) { super.actions.get(super.actions.size()-1).execute(npcs, p, npc); }
	}
}