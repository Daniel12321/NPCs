package me.mrdaniel.npcs.data.npc;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractData;
import org.spongepowered.api.data.merge.MergeFunction;

public class NPCData extends AbstractData<NPCData, ImmutableNPCData> {

	public NPCData() {
		registerGettersAndSetters();
	}

	@Override protected void registerGettersAndSetters() {}
	@Override public DataContainer toContainer() { return DataContainer.createNew(); }
	@Override public Optional<NPCData> fill(DataHolder holder, MergeFunction overlap) { return Optional.empty(); }
	@Override public Optional<NPCData> from(DataContainer container) { return Optional.empty(); }
	public Optional<NPCData> from(DataView view) { return Optional.empty(); }
	@Override public NPCData copy() { return new NPCData(); }
	@Override public ImmutableNPCData asImmutable() { return new ImmutableNPCData(); }
	@Override public int getContentVersion() { return 1; }
}
