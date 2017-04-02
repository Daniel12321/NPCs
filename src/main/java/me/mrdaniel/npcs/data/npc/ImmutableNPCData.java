package me.mrdaniel.npcs.data.npc;

import javax.annotation.Nullable;

import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableData;

import me.mrdaniel.npcs.data.MMOKeys;
import me.mrdaniel.npcs.data.action.NPCAction;

public class ImmutableNPCData extends AbstractImmutableData<ImmutableNPCData, NPCData> {

	private final boolean looking;
	private final boolean interact;

	@Nullable private final NPCAction action;

	public ImmutableNPCData(final boolean looking, final boolean interact, @Nullable final NPCAction action) {
		this.looking = looking;
		this.interact = interact;

		this.action = action;

		registerGetters();
	}

	@Override
	protected void registerGetters() {
		registerFieldGetter(MMOKeys.LOOKING, () -> this.looking);
		registerFieldGetter(MMOKeys.INTERACT, () -> this.interact);
		registerFieldGetter(MMOKeys.ACTION, () -> this.action);
	}

	@Override public DataContainer toContainer() { return this.asMutable().toContainer(); }
	@Override public NPCData asMutable() { return new NPCData(this.looking, this.interact, this.action); }
	@Override public int getContentVersion() { return 1; }
}