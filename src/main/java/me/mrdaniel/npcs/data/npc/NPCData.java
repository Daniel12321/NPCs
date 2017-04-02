package me.mrdaniel.npcs.data.npc;

import java.util.Optional;
import java.util.function.BinaryOperator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractData;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.value.mutable.OptionalValue;
import org.spongepowered.api.data.value.mutable.Value;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.player.Player;

import com.flowpowered.math.vector.Vector3d;
import com.google.common.base.Preconditions;

import me.mrdaniel.npcs.data.MMOKeys;
import me.mrdaniel.npcs.data.action.NPCAction;

public class NPCData extends AbstractData<NPCData, ImmutableNPCData> {

	private boolean looking;
	private boolean interact;

	@Nullable private NPCAction action;

	public NPCData() { this(false, false, null); }
	public NPCData(final boolean looking, final boolean interact, @Nullable final NPCAction action) {
		this.looking = looking;
		this.interact = interact;

		this.action = action;

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

		registerFieldGetter(MMOKeys.ACTION, this::getAction);
		registerFieldSetter(MMOKeys.ACTION, this::setAction);
		registerKeyValue(MMOKeys.ACTION, this::getActionValue);
	}

	@Nonnull public Value<Boolean> getLookingValue() { return MMOKeys.FACTORY.createValue(MMOKeys.LOOKING, this.looking); }
	public boolean isLooking() { return this.looking; }
	public void setLooking(final boolean looking) { this.looking = looking; }

	@Nonnull public Value<Boolean> getInteractValue() { return MMOKeys.FACTORY.createValue(MMOKeys.INTERACT, this.interact); }
	public boolean canInteract() { return this.interact; }
	public void setInteract(final boolean interact) { this.interact = interact; }

	@Nonnull public OptionalValue<NPCAction> getActionValue() { return MMOKeys.FACTORY.createOptionalValue(MMOKeys.ACTION, this.action); }
	@Nonnull public Optional<NPCAction> getAction() { return Optional.ofNullable(this.action); }
	public void setAction(@Nonnull final Optional<NPCAction> action) { this.action = action.orElse(null); }

	public Optional<NPCData> from(@Nonnull final DataView view) {
		return Optional.of(new NPCData(
				view.getBoolean(MMOKeys.LOOKING.getQuery()).orElse(false),
				view.getBoolean(MMOKeys.INTERACT.getQuery()).orElse(false),
				view.getSerializable(MMOKeys.ACTION.getQuery(), NPCAction.class).orElse(null)));
	}

	@Override
	public DataContainer toContainer() {
		DataContainer container = super.toContainer()
				.set(MMOKeys.LOOKING.getQuery(), this.looking)
				.set(MMOKeys.INTERACT.getQuery(), this.interact);

		if (this.action != null) { container.set(MMOKeys.ACTION.getQuery(), this.action); }
		return container;
	}

	@Override public Optional<NPCData> fill(DataHolder holder, MergeFunction overlap) { return Optional.of(Preconditions.checkNotNull(overlap).merge(copy(), from(holder.toContainer()).orElse(null))); }
	@Override public Optional<NPCData> from(DataContainer container) { return from((DataView)container); }
	@Override public NPCData copy() { return new NPCData(this.looking, this.interact, this.action); }
	@Override public ImmutableNPCData asImmutable() { return new ImmutableNPCData(this.looking, this.interact, this.action); }
	@Override public int getContentVersion() { return 1; }

	public void tick(@Nonnull final Living npc) {
		if (this.looking) {
			Vector3d pos = npc.getLocation().getPosition();
			npc.getNearbyEntities(10.0).stream().filter(e -> e instanceof Player).reduce(BinaryOperator.minBy((p1, p2) -> (int) (p1.getLocation().getPosition().distance(pos) - p2.getLocation().getPosition().distance(pos)))).ifPresent(p -> npc.lookAt(p.getLocation().getPosition().add(0.0, 1.62, 0.0)));
		}
	}
}