package me.mrdaniel.npcs.data.npc;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractData;
import org.spongepowered.api.data.merge.MergeFunction;

import com.google.common.base.Preconditions;

import lombok.Getter;
import me.mrdaniel.npcs.data.NPCKeys;

public class NPCData extends AbstractData<NPCData, ImmutableNPCData> {

	@Getter private int id;

	public NPCData(final int id) {
		this.id = id;

		registerGettersAndSetters();
	}

	@Override protected void registerGettersAndSetters() {}
	@Override public DataContainer toContainer() { return this.asImmutable().toContainer(); }
	@Override public Optional<NPCData> fill(DataHolder holder, MergeFunction overlap) { return Optional.of(Preconditions.checkNotNull(overlap).merge(copy(), from(holder.toContainer()).orElse(null))); }
	@Override public Optional<NPCData> from(DataContainer container) { return from((DataView)container); }
	public Optional<NPCData> from(@Nonnull final DataView view) { return Optional.of(new NPCData(view.getInt(NPCKeys.ID.getQuery()).orElse(0))); }
	@Override public NPCData copy() { return new NPCData(this.id); }
	@Override public ImmutableNPCData asImmutable() { return new ImmutableNPCData(this.id); }
	@Override public int getContentVersion() { return 1; }
}