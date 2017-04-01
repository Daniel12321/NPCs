//package me.mrdaniel.npcs.data.walking;
//
//import java.util.List;
//
//import javax.annotation.Nonnull;
//
//import org.spongepowered.api.data.DataContainer;
//import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableData;
//
//import com.flowpowered.math.vector.Vector3d;
//
//import me.mrdaniel.npcs.data.MMOKeys;
//
//public class ImmutableWalkingData extends AbstractImmutableData<ImmutableWalkingData, WalkingData> {
//
//	private final List<Vector3d> walk_positions;
//
//	public ImmutableWalkingData(@Nonnull final List<Vector3d> walk_positions) {
//		this.walk_positions = walk_positions;
//
//		registerGetters();
//	}
//
//	@Override
//	protected void registerGetters() {
//		registerFieldGetter(MMOKeys.WALK_POSITIONS, () -> this.walk_positions);
//	}
//
//	@Override public DataContainer toContainer() { return this.asMutable().toContainer(); }
//	@Override public WalkingData asMutable() { return new WalkingData(this.walk_positions); }
//	@Override public int getContentVersion() { return 1; }
//}