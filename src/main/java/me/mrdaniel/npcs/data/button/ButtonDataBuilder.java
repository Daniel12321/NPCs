package me.mrdaniel.npcs.data.button;

import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataRegistration;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.DataManipulatorBuilder;
import org.spongepowered.api.data.persistence.AbstractDataBuilder;
import org.spongepowered.api.data.persistence.InvalidDataException;

import java.util.Optional;

public class ButtonDataBuilder extends AbstractDataBuilder<ButtonData> implements DataManipulatorBuilder<ButtonData, ImmutableButtonData> {

    public ButtonDataBuilder() {
        super(ButtonData.class, 1);
    }

    @Override
    public ButtonData create() {
        return new ButtonData(-1);
    }

    @Override
    public Optional<ButtonData> createFrom(DataHolder holder) {
        return create().fill(holder);
    }

    @Override
    protected Optional<ButtonData> buildContent(DataView view) throws InvalidDataException {
        return create().from(view);
    }

    public static void register() {
        DataRegistration.builder()
                .id("button")
                .name("Button")
                .dataClass(ButtonData.class)
                .immutableClass(ImmutableButtonData.class)
                .builder(new ButtonDataBuilder())
                .build();
    }
}
