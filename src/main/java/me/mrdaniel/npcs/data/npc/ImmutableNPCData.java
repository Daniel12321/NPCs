package me.mrdaniel.npcs.data.npc;

import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableData;

import me.mrdaniel.npcs.data.NPCKeys;

public class ImmutableNPCData extends AbstractImmutableData<ImmutableNPCData, NPCData> {

	private final int startup;
	private final int id;
	private final boolean looking;
	private final boolean interact;

	public ImmutableNPCData(final int startup, final int id, final boolean looking, final boolean interact) {
		this.startup = startup;
		this.id = id;
		this.looking = looking;
		this.interact = interact;

		registerGetters();
	}

	@Override
	protected void registerGetters() {
		registerFieldGetter(NPCKeys.STARTUP, () -> this.startup);
		registerFieldGetter(NPCKeys.ID, () -> this.id);
		registerFieldGetter(NPCKeys.LOOKING, () -> this.looking);
		registerFieldGetter(NPCKeys.INTERACT, () -> this.interact);
	}

	@Override
	public DataContainer toContainer() {
		return super.toContainer()
				.set(NPCKeys.STARTUP.getQuery(), this.startup)
				.set(NPCKeys.ID.getQuery(), this.id)
				.set(NPCKeys.LOOKING.getQuery(), this.looking)
				.set(NPCKeys.INTERACT.getQuery(), this.interact);
	}

	@Override public NPCData asMutable() { return new NPCData(this.startup, this.id, this.looking, this.interact); }
	@Override public int getContentVersion() { return 1; }
}