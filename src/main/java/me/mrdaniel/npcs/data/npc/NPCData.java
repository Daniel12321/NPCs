package me.mrdaniel.npcs.data.npc;

import java.util.Optional;
import java.util.function.BinaryOperator;

import javax.annotation.Nonnull;

import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractData;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.value.mutable.Value;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.player.Player;

import com.flowpowered.math.vector.Vector3d;
import com.google.common.base.Preconditions;

import me.mrdaniel.npcs.data.NPCKeys;

public class NPCData extends AbstractData<NPCData, ImmutableNPCData> {

	private int startup;
	private int id;
	private boolean looking;
	private boolean interact;

	public NPCData(final int startup, final int id, final boolean looking, final boolean interact) {
		this.startup = startup;
		this.id = id;
		this.looking = looking;
		this.interact = interact;

		registerGettersAndSetters();
	}

	@Override
	protected void registerGettersAndSetters() {
		registerKeyValue(NPCKeys.STARTUP, this::getStartupValue);
		registerFieldGetter(NPCKeys.STARTUP, this::getStartup);
		registerFieldSetter(NPCKeys.STARTUP, this::setStartup);

		registerKeyValue(NPCKeys.ID, this::getIdValue);
		registerFieldGetter(NPCKeys.ID, this::getId);
		registerFieldSetter(NPCKeys.ID, this::setId);

		registerKeyValue(NPCKeys.LOOKING, this::getLookingValue);
		registerFieldGetter(NPCKeys.LOOKING, this::isLooking);
		registerFieldSetter(NPCKeys.LOOKING, this::setLooking);

		registerKeyValue(NPCKeys.INTERACT, this::getInteractValue);
		registerFieldGetter(NPCKeys.INTERACT, this::canInteract);
		registerFieldSetter(NPCKeys.INTERACT, this::setInteract);
	}

	public Value<Integer> getStartupValue() { return NPCKeys.FACTORY.createValue(NPCKeys.STARTUP, this.startup); }
	public int getStartup() { return this.startup; }
	public void setStartup(final int startup) { this.startup = startup; }

	public Value<Integer> getIdValue() { return NPCKeys.FACTORY.createValue(NPCKeys.ID, this.id); }
	public int getId() { return this.id; }
	public void setId(final int id) { this.id = id; }

	public Value<Boolean> getLookingValue() { return NPCKeys.FACTORY.createValue(NPCKeys.LOOKING, this.looking); }
	public boolean isLooking() { return this.looking; }
	public void setLooking(final boolean looking) { this.looking = looking; }

	public Value<Boolean> getInteractValue() { return NPCKeys.FACTORY.createValue(NPCKeys.INTERACT, this.interact); }
	public boolean canInteract() { return this.interact; }
	public void setInteract(final boolean interact) { this.interact = interact; }

	@Nonnull
	public Optional<NPCData> from(@Nonnull final DataView view) {
		return Optional.of(new NPCData(
				view.getInt(NPCKeys.STARTUP.getQuery()).orElse(0),
				view.getInt(NPCKeys.ID.getQuery()).orElse(0),
				view.getBoolean(NPCKeys.LOOKING.getQuery()).orElse(false),
				view.getBoolean(NPCKeys.INTERACT.getQuery()).orElse(true)));
	}

	@Override public DataContainer toContainer() { return this.asImmutable().toContainer(); }
	@Override public Optional<NPCData> fill(DataHolder holder, MergeFunction overlap) { return Optional.of(Preconditions.checkNotNull(overlap).merge(copy(), from(holder.toContainer()).orElse(null))); }
	@Override public Optional<NPCData> from(DataContainer container) { return from((DataView)container); }
	@Override public NPCData copy() { return new NPCData(this.startup, this.id, this.looking, this.interact); }
	@Override public ImmutableNPCData asImmutable() { return new ImmutableNPCData(this.startup, this.id, this.looking, this.interact); }
	@Override public int getContentVersion() { return 1; }

	public void tick(@Nonnull final Living npc) {
		if (this.looking) {
			Vector3d pos = npc.getLocation().getPosition();
			npc.getNearbyEntities(e -> e instanceof Player && e.getLocation().getPosition().distance(npc.getLocation().getPosition()) <= 20.0).stream().reduce(BinaryOperator.minBy((p1, p2) -> (int) (p1.getLocation().getPosition().distance(pos) - p2.getLocation().getPosition().distance(pos)))).ifPresent(p -> npc.lookAt(p.getLocation().getPosition().add(0.0, 1.62, 0.0)));
		}
	}

	public void ifOld(@Nonnull final int startup, @Nonnull final Runnable run) {
		if (this.startup != startup) { run.run(); }
	}
}