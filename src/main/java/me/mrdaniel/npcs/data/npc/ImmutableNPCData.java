package me.mrdaniel.npcs.data.npc;

import javax.annotation.Nonnull;

import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableData;

import me.mrdaniel.npcs.data.MMOKeys;
import me.mrdaniel.npcs.data.action.NPCActions;

public class ImmutableNPCData extends AbstractImmutableData<ImmutableNPCData, NPCData> {

	private final String skin;
	private final boolean looking;
	private final boolean interact;

	private final NPCActions actions;

	public ImmutableNPCData(@Nonnull final String skin, final boolean looking, final boolean interact, @Nonnull final NPCActions actions) {
		this.skin = skin;
		this.looking = looking;
		this.interact = interact;

		this.actions = actions;

		registerGetters();
	}

	@Override
	protected void registerGetters() {
		registerFieldGetter(MMOKeys.SKIN, () -> this.skin);
		registerFieldGetter(MMOKeys.LOOKING, () -> this.looking);
		registerFieldGetter(MMOKeys.INTERACT, () -> this.interact);
		registerFieldGetter(MMOKeys.ACTIONS, () -> this.actions);
	}

	@Override public DataContainer toContainer() { return this.asMutable().toContainer(); }
	@Override public NPCData asMutable() { return new NPCData(this.skin, this.looking, this.interact, this.actions); }
	@Override public int getContentVersion() { return 1; }
}