package me.mrdaniel.npcs.data.npc;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.DataManipulatorBuilder;
import org.spongepowered.api.data.persistence.AbstractDataBuilder;
import org.spongepowered.api.data.persistence.InvalidDataException;

public class NPCDataBuilder extends AbstractDataBuilder<NPCData> implements DataManipulatorBuilder<NPCData, ImmutableNPCData> {

	public NPCDataBuilder() {
		super(NPCData.class, 1);
	}

	@Override public NPCData create() { return new NPCData(); }
	@Override public Optional<NPCData> createFrom(@Nonnull DataHolder dataHolder) { return create().fill(dataHolder); }
	@Override protected Optional<NPCData> buildContent(@Nonnull DataView view) throws InvalidDataException { return create().from(view); }
}