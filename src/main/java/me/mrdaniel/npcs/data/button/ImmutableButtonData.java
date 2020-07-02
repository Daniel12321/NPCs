package me.mrdaniel.npcs.data.button;

import me.mrdaniel.npcs.data.NPCKeys;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableData;
import org.spongepowered.api.data.value.immutable.ImmutableValue;

public class ImmutableButtonData extends AbstractImmutableData<ImmutableButtonData, ButtonData> {

    private final int index;

    public ImmutableButtonData(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    private ImmutableValue<Integer> index() {
        return Sponge.getRegistry().getValueFactory().createValue(NPCKeys.BUTTON_INDEX, this.index).asImmutable();
    }

    @Override
    protected void registerGetters() {
        registerKeyValue(NPCKeys.BUTTON_INDEX, this::index);
        registerFieldGetter(NPCKeys.BUTTON_INDEX, this::getIndex);
    }

    @Override
    public ButtonData asMutable() {
        return new ButtonData(this.index);
    }

    @Override
    public int getContentVersion() {
        return 1;
    }

    @Override
    protected DataContainer fillContainer(DataContainer container) {
        return this.asMutable().fillContainer(container);
    }
}
