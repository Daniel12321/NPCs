package me.mrdaniel.npcs.data.npc;

import me.mrdaniel.npcs.data.NPCKeys;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableData;
import org.spongepowered.api.data.value.immutable.ImmutableValue;

public class ImmutableNPCData extends AbstractImmutableData<ImmutableNPCData, NPCData> {

    private final int id;

    public ImmutableNPCData(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    private ImmutableValue<Integer> id() {
        return Sponge.getRegistry().getValueFactory().createValue(NPCKeys.NPC_ID, this.id).asImmutable();
    }

    @Override
    protected void registerGetters() {
        registerKeyValue(NPCKeys.NPC_ID, this::id);
        registerFieldGetter(NPCKeys.NPC_ID, this::getId);
    }


    @Override
    public NPCData asMutable() {
        return new NPCData(this.id);
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
