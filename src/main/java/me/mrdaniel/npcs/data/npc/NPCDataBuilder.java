package me.mrdaniel.npcs.data.npc;

import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataRegistration;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.DataManipulatorBuilder;
import org.spongepowered.api.data.persistence.AbstractDataBuilder;
import org.spongepowered.api.data.persistence.InvalidDataException;

import java.util.Optional;

public class NPCDataBuilder extends AbstractDataBuilder<NPCData> implements DataManipulatorBuilder<NPCData, ImmutableNPCData> {

	public NPCDataBuilder() {
		super(NPCData.class, 1);
	}

	@Override
	public NPCData create() {
		return new NPCData(0);
	}

	@Override
	public Optional<NPCData> createFrom(DataHolder holder) {
		return create().fill(holder);
	}

	@Override
	protected Optional<NPCData> buildContent(DataView view) throws InvalidDataException {
		return create().from(view);
	}

	public static void register() {
		DataRegistration.builder()
				.id("npc-data")
				.name("NPC Data")
				.dataClass(NPCData.class)
				.immutableClass(ImmutableNPCData.class)
				.builder(new NPCDataBuilder())
				.build();
	}
}
