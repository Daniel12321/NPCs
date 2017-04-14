package me.mrdaniel.npcs.data.npc.actions;

import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.persistence.DataContentUpdater;

public class NPCActionsUpdater implements DataContentUpdater {

	@Override public int getInputVersion() { return 1; }
	@Override public int getOutputVersion() { return 2; }

	@Override
	public DataView update(DataView view) {
		String type = view.contains(DataQuery.of("current")) ? view.getBoolean(DataQuery.of("repeating")).orElse(false) ? "iterate" : "only_once" : "random";

		return view.set(DataQuery.of("type"), type);
	}
}