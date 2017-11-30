package me.mrdaniel.npcs.data.npc;

import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableData;

public class ImmutableNPCData extends AbstractImmutableData<ImmutableNPCData, NPCData> {

	public ImmutableNPCData() {
		registerGetters();
	}

	@Override protected void registerGetters() {}
	@Override public DataContainer toContainer() { return DataContainer.createNew(); }
	@Override public NPCData asMutable() { return new NPCData(); }
	@Override public int getContentVersion() { return 1; }
}