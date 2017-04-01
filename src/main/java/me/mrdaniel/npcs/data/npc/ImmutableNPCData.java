package me.mrdaniel.npcs.data.npc;

import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableData;

import me.mrdaniel.npcs.data.MMOKeys;

public class ImmutableNPCData extends AbstractImmutableData<ImmutableNPCData, NPCData> {

	private final boolean looking;
	private final boolean interact;

	public ImmutableNPCData(final boolean looking, final boolean interact) {
		this.looking = looking;
		this.interact = interact;

		registerGetters();
	}

	@Override
	protected void registerGetters() {
		registerFieldGetter(MMOKeys.LOOKING, () -> this.looking);
		registerFieldGetter(MMOKeys.INTERACT, () -> this.interact);
	}

	@Override public DataContainer toContainer() { return this.asMutable().toContainer(); }
	@Override public NPCData asMutable() { return new NPCData(this.looking, this.interact); }
	@Override public int getContentVersion() { return 1; }
}