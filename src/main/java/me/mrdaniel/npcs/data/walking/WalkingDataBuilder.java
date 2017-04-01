//package me.mrdaniel.npcs.data.walking;
//
//import java.util.Optional;
//
//import javax.annotation.Nonnull;
//
//import org.spongepowered.api.data.DataHolder;
//import org.spongepowered.api.data.DataView;
//import org.spongepowered.api.data.manipulator.DataManipulatorBuilder;
//import org.spongepowered.api.data.persistence.AbstractDataBuilder;
//import org.spongepowered.api.data.persistence.InvalidDataException;
//
//public class WalkingDataBuilder extends AbstractDataBuilder<WalkingData> implements DataManipulatorBuilder<WalkingData, ImmutableWalkingData> {
//
//	public WalkingDataBuilder() {
//		super(WalkingData.class, 1);
//	}
//
//	@Override public WalkingData create() { return new WalkingData(); }
//	@Override public Optional<WalkingData> createFrom(@Nonnull DataHolder dataHolder) { return create().fill(dataHolder); }
//	@Override protected Optional<WalkingData> buildContent(@Nonnull DataView view) throws InvalidDataException { return create().from(view); }
//}