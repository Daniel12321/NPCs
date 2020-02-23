package me.mrdaniel.npcs.data.npc;

import me.mrdaniel.npcs.data.NPCKeys;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractData;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.value.mutable.Value;

import java.util.Optional;

public class NPCData extends AbstractData<NPCData, ImmutableNPCData> {

	private int id;

	public NPCData(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	private Value<Integer> id() {
		return Sponge.getRegistry().getValueFactory().createValue(NPCKeys.NPC_ID, this.id);
	}

	@Override
	protected void registerGettersAndSetters() {
		registerKeyValue(NPCKeys.NPC_ID, this::id);
		registerFieldGetter(NPCKeys.NPC_ID, this::getId);
		registerFieldSetter(NPCKeys.NPC_ID, this::setId);
	}

	@Override
	public Optional<NPCData> fill(DataHolder holder, MergeFunction overlap) {
		return this.from(holder.get(NPCKeys.NPC_ID));
	}

	public Optional<NPCData> from(DataView view) {
		return this.from(view.getInt(NPCKeys.NPC_ID.getQuery()));
	}

	@Override
	public Optional<NPCData> from(DataContainer container) {
		return from((DataView) container);
	}

	private Optional<NPCData> from(Optional<Integer> id) {
		if (!id.isPresent()) {
			return Optional.empty();
		}
		this.id = id.get();

		return Optional.of(this);
	}

	@Override
	public NPCData copy() {
		return new NPCData(this.id);
	}

	@Override
	public ImmutableNPCData asImmutable() {
		return new ImmutableNPCData(this.id);
	}

	@Override
	public int getContentVersion() {
		return 1;
	}

	@Override
	protected DataContainer fillContainer(DataContainer container) {
		return container.set(NPCKeys.NPC_ID, this.id);
	}
}
