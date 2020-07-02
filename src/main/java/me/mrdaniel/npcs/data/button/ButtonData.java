package me.mrdaniel.npcs.data.button;

import me.mrdaniel.npcs.data.NPCKeys;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractData;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.value.mutable.Value;

import java.util.Optional;

public class ButtonData extends AbstractData<ButtonData, ImmutableButtonData> {

    private int index;

    public ButtonData(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    private Value<Integer> index() {
        return Sponge.getRegistry().getValueFactory().createValue(NPCKeys.BUTTON_INDEX, this.index);
    }

    @Override
    protected void registerGettersAndSetters() {
        registerKeyValue(NPCKeys.BUTTON_INDEX, this::index);
        registerFieldGetter(NPCKeys.BUTTON_INDEX, this::getIndex);
        registerFieldSetter(NPCKeys.BUTTON_INDEX, this::setIndex);
    }

    @Override
    public Optional<ButtonData> fill(DataHolder holder, MergeFunction overlap) {
        return this.from(holder.get(NPCKeys.BUTTON_INDEX));
    }

    public Optional<ButtonData> from(DataView view) {
        return this.from(view.getInt(NPCKeys.BUTTON_INDEX.getQuery()));
    }

    @Override
    public Optional<ButtonData> from(DataContainer container) {
        return from((DataView) container);
    }

    private Optional<ButtonData> from(Optional<Integer> index) {
        if (!index.isPresent()) {
            return Optional.empty();
        }

        this.index = index.get();

        return Optional.of(this);
    }

    @Override
    public ButtonData copy() {
        return new ButtonData(this.index);
    }

    @Override
    public ImmutableButtonData asImmutable() {
        return new ImmutableButtonData(this.index);
    }

    @Override
    public int getContentVersion() {
        return 1;
    }

    @Override
    protected DataContainer fillContainer(DataContainer container) {
        return container.set(NPCKeys.BUTTON_INDEX, this.index);
    }
}
