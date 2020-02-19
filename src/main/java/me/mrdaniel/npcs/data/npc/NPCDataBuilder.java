package me.mrdaniel.npcs.data.npc;

import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.DataManipulatorBuilder;
import org.spongepowered.api.data.persistence.AbstractDataBuilder;
import org.spongepowered.api.data.persistence.InvalidDataException;

import javax.annotation.Nonnull;
import java.util.Optional;

public class NPCDataBuilder extends AbstractDataBuilder<NPCData> implements DataManipulatorBuilder<NPCData, ImmutableNPCData> {

	public NPCDataBuilder() {
		super(NPCData.class, 1);
	}

	@Override public NPCData create() { return new NPCData(); }
	@Override public Optional<NPCData> createFrom(DataHolder dataHolder) { return Optional.empty(); }
	@Override protected Optional<NPCData> buildContent(DataView view) throws InvalidDataException { return Optional.empty(); }
}
