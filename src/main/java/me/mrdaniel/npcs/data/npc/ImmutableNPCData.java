package me.mrdaniel.npcs.data.npc;

import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableData;

import me.mrdaniel.npcs.data.NPCKeys;

public class ImmutableNPCData extends AbstractImmutableData<ImmutableNPCData, NPCData> {

	private final int id;

	public ImmutableNPCData(final int id) {
		this.id = id;

		registerGetters();
	}

	@Override protected void registerGetters() {}
	@Override public DataContainer toContainer() { return super.toContainer().set(NPCKeys.ID.getQuery(), this.id); }
	@Override public NPCData asMutable() { return new NPCData(this.id); }
	@Override public int getContentVersion() { return 1; }
}