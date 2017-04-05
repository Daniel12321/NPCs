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
import com.google.common.collect.Lists;

import me.mrdaniel.npcs.data.MMOKeys;
import me.mrdaniel.npcs.data.action.NPCActions;
import me.mrdaniel.npcs.data.action.NPCRandomActions;

public class NPCData extends AbstractData<NPCData, ImmutableNPCData> {

	private String skin;
	private boolean looking;
	private boolean interact;

	private NPCActions actions;

	public NPCData() { this("Steve", false, true, new NPCRandomActions(Lists.newArrayList())); }
	public NPCData(@Nonnull final String skin, final boolean looking, final boolean interact, @Nonnull final NPCActions actions) {
		this.skin = skin;
		this.looking = looking;
		this.interact = interact;

		this.actions = actions;

		registerGettersAndSetters();
	}

	@Override
	protected void registerGettersAndSetters() {
		registerKeyValue(MMOKeys.SKIN, this::getSkinValue);
		registerFieldGetter(MMOKeys.SKIN, this::getSkin);
		registerFieldSetter(MMOKeys.SKIN, this::setSkin);

		registerKeyValue(MMOKeys.LOOKING, this::getLookingValue);
		registerFieldGetter(MMOKeys.LOOKING, this::isLooking);
		registerFieldSetter(MMOKeys.LOOKING, this::setLooking);

		registerKeyValue(MMOKeys.INTERACT, this::getInteractValue);
		registerFieldGetter(MMOKeys.INTERACT, this::canInteract);
		registerFieldSetter(MMOKeys.INTERACT, this::setInteract);

		registerKeyValue(MMOKeys.ACTIONS, this::getActionsValue);
		registerFieldGetter(MMOKeys.ACTIONS, this::getActions);
		registerFieldSetter(MMOKeys.ACTIONS, this::setActions);
	}

	public Value<String> getSkinValue() { return MMOKeys.FACTORY.createValue(MMOKeys.SKIN, this.skin); }
	public String getSkin() { return this.skin; }
	public void setSkin(final String skin) { this.skin = skin; }

	public Value<Boolean> getLookingValue() { return MMOKeys.FACTORY.createValue(MMOKeys.LOOKING, this.looking); }
	public boolean isLooking() { return this.looking; }
	public void setLooking(final boolean looking) { this.looking = looking; }

	public Value<Boolean> getInteractValue() { return MMOKeys.FACTORY.createValue(MMOKeys.INTERACT, this.interact); }
	public boolean canInteract() { return this.interact; }
	public void setInteract(final boolean interact) { this.interact = interact; }

	public Value<NPCActions> getActionsValue() { return MMOKeys.FACTORY.createValue(MMOKeys.ACTIONS, this.actions); }
	public NPCActions getActions() { return this.actions; }
	public void setActions(final NPCActions actions) { this.actions = actions; }

	public Optional<NPCData> from(@Nonnull final DataView view) {
		return Optional.of(new NPCData(
				view.getString(MMOKeys.SKIN.getQuery()).orElse("Steve"),
				view.getBoolean(MMOKeys.LOOKING.getQuery()).orElse(false),
				view.getBoolean(MMOKeys.INTERACT.getQuery()).orElse(true),
				view.getSerializable(MMOKeys.ACTIONS.getQuery(), NPCActions.class).orElse(new NPCRandomActions(Lists.newArrayList()))));
	}

	@Override
	public DataContainer toContainer() {
		return super.toContainer()
				.set(MMOKeys.SKIN.getQuery(), this.skin)
				.set(MMOKeys.LOOKING.getQuery(), this.looking)
				.set(MMOKeys.INTERACT.getQuery(), this.interact)
				.set(MMOKeys.ACTIONS.getQuery(), this.actions);
	}

	@Override public Optional<NPCData> fill(DataHolder holder, MergeFunction overlap) { return Optional.of(Preconditions.checkNotNull(overlap).merge(copy(), from(holder.toContainer()).orElse(null))); }
	@Override public Optional<NPCData> from(DataContainer container) { return from((DataView)container); }
	@Override public NPCData copy() { return new NPCData(this.skin, this.looking, this.interact, this.actions); }
	@Override public ImmutableNPCData asImmutable() { return new ImmutableNPCData(this.skin, this.looking, this.interact, this.actions); }
	@Override public int getContentVersion() { return 1; }

	public void tick(@Nonnull final Living npc) {
		if (this.looking) {
			Vector3d pos = npc.getLocation().getPosition();
			npc.getNearbyEntities(e -> e instanceof Player && e.getLocation().getPosition().distance(npc.getLocation().getPosition()) <= 20.0).stream().reduce(BinaryOperator.minBy((p1, p2) -> (int) (p1.getLocation().getPosition().distance(pos) - p2.getLocation().getPosition().distance(pos)))).ifPresent(p -> npc.lookAt(p.getLocation().getPosition().add(0.0, 1.62, 0.0)));
		}
	}
}