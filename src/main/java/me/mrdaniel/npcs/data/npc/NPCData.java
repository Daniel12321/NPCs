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

import me.mrdaniel.npcs.data.MMOKeys;

public class NPCData extends AbstractData<NPCData, ImmutableNPCData> {

	private boolean looking;
	private boolean interact;

	public NPCData() { this(false, false); }
	public NPCData(final boolean looking, final boolean interact) {
		this.looking = looking;
		this.interact = interact;

		registerGettersAndSetters();
	}

	@Override
	protected void registerGettersAndSetters() {
		registerFieldGetter(MMOKeys.LOOKING, this::isLooking);
		registerFieldSetter(MMOKeys.LOOKING, this::setLooking);
		registerKeyValue(MMOKeys.LOOKING, this::getLookingValue);

		registerFieldGetter(MMOKeys.INTERACT, this::canInteract);
		registerFieldSetter(MMOKeys.INTERACT, this::setInteract);
		registerKeyValue(MMOKeys.INTERACT, this::getInteractValue);
	}

	@Nonnull public Value<Boolean> getLookingValue() { return MMOKeys.FACTORY.createValue(MMOKeys.LOOKING, this.looking); }
	public boolean isLooking() { return this.looking; }
	public void setLooking(final boolean looking) { this.looking = looking; }

	@Nonnull public Value<Boolean> getInteractValue() { return MMOKeys.FACTORY.createValue(MMOKeys.INTERACT, this.interact); }
	public boolean canInteract() { return this.interact; }
	public void setInteract(final boolean interact) { this.interact = interact; }

	public Optional<NPCData> from(@Nonnull final DataView view) {
		return Optional.of(new NPCData(
				view.getBoolean(MMOKeys.LOOKING.getQuery()).orElse(false),
				view.getBoolean(MMOKeys.INTERACT.getQuery()).orElse(false)));
	}

	@Override
	public DataContainer toContainer() {
		return super.toContainer()
				.set(MMOKeys.LOOKING.getQuery(), this.looking)
				.set(MMOKeys.INTERACT.getQuery(), this.interact);
	}

	@Override public Optional<NPCData> fill(DataHolder holder, MergeFunction overlap) { return Optional.of(Preconditions.checkNotNull(overlap).merge(copy(), from(holder.toContainer()).orElse(null))); }
	@Override public Optional<NPCData> from(DataContainer container) { return from((DataView)container); }
	@Override public NPCData copy() { return new NPCData(this.looking, this.interact); }
	@Override public ImmutableNPCData asImmutable() { return new ImmutableNPCData(this.looking, this.interact); }
	@Override public int getContentVersion() { return 1; }

	public void tick(@Nonnull final Living npc) {
		if (this.looking) {
			Vector3d pos = npc.getLocation().getPosition();
			npc.getNearbyEntities(10.0).stream().filter(e -> e instanceof Player).reduce(BinaryOperator.minBy((p1, p2) -> (int) (p1.getLocation().getPosition().distance(pos) - p2.getLocation().getPosition().distance(pos)))).ifPresent(p -> npc.lookAt(p.getLocation().getPosition().add(0.0, 1.62, 0.0)));
		}
	}
}