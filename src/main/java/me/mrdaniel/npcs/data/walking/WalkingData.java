//package me.mrdaniel.npcs.data.walking;
//
//import java.util.List;
//import java.util.Optional;
//
//import javax.annotation.Nonnull;
//
//import org.spongepowered.api.data.DataContainer;
//import org.spongepowered.api.data.DataHolder;
//import org.spongepowered.api.data.DataView;
//import org.spongepowered.api.data.manipulator.mutable.common.AbstractData;
//import org.spongepowered.api.data.merge.MergeFunction;
//import org.spongepowered.api.data.value.mutable.ListValue;
//import org.spongepowered.api.entity.living.Living;
//
//import com.flowpowered.math.vector.Vector3d;
//import com.google.common.base.Preconditions;
//import com.google.common.collect.Lists;
//
//import me.mrdaniel.npcs.data.MMOKeys;
//
//public class WalkingData extends AbstractData<WalkingData, ImmutableWalkingData> {
//
//	private List<Vector3d> walk_positions;
//
//	private int current;
//
//	public WalkingData() { this(Lists.newArrayList()); }
//	public WalkingData(@Nonnull final List<Vector3d> walk_positions) {
//		this.walk_positions = walk_positions;
//
//		this.current = 0;
//
//		registerGettersAndSetters();
//	}
//
//	@Override
//	protected void registerGettersAndSetters() {
//		registerFieldGetter(MMOKeys.WALK_POSITIONS, this::getWalkPositions);
//		registerFieldSetter(MMOKeys.WALK_POSITIONS, this::setWalkPositions);
//		registerKeyValue(MMOKeys.WALK_POSITIONS, this::getWalkPositionsValue);
//	}
//
//	@Nonnull public ListValue<Vector3d> getWalkPositionsValue() { return MMOKeys.FACTORY.createListValue(MMOKeys.WALK_POSITIONS, this.walk_positions); }
//	@Nonnull public List<Vector3d> getWalkPositions() { return this.walk_positions; }
//	public void setWalkPositions(@Nonnull final List<Vector3d> walk_positions) { this.walk_positions = walk_positions; this.current = 0; }
//	public void addWalkPosition(@Nonnull final Vector3d walk_positions) { this.walk_positions.add(walk_positions); }
//
//	@SuppressWarnings("unchecked")
//	public Optional<WalkingData> from(@Nonnull final DataView view) {
//		return Optional.of(new WalkingData(view.getList(MMOKeys.WALK_POSITIONS.getQuery()).map(l -> (List<Vector3d>)l).orElse(Lists.newArrayList())));
//	}
//
//	@Override
//	public DataContainer toContainer() {
//		return super.toContainer().set(MMOKeys.WALK_POSITIONS.getQuery(), this.walk_positions);
//	}
//
//	@Override public Optional<WalkingData> fill(DataHolder holder, MergeFunction overlap) { return Optional.of(Preconditions.checkNotNull(overlap).merge(copy(), from(holder.toContainer()).orElse(null))); }
//	@Override public Optional<WalkingData> from(DataContainer container) { return from((DataView)container); }
//	@Override public WalkingData copy() { return new WalkingData(this.walk_positions); }
//	@Override public ImmutableWalkingData asImmutable() { return new ImmutableWalkingData(this.walk_positions); }
//	@Override public int getContentVersion() { return 1; }
//
//	public void tick(@Nonnull final Living npc) {
//		if (npc.getLocation().getPosition().distance(this.walk_positions.get(this.current)) <= 1.0) { this.moveTarget(npc); }
//
//		npc.lookAt(this.walk_positions.get(this.current).add(0.0, 0.62, 0.0));
//	}
//
//	private void moveTarget(@Nonnull final Living npc) {
//		this.current++;
//		if (this.current >= this.walk_positions.size()) { this.current = 0; }
//	}
//}