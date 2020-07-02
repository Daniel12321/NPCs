package me.mrdaniel.npcs.io.nbt;

import com.google.common.collect.Sets;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.exceptions.NPCException;
import me.mrdaniel.npcs.io.INPCData;
import me.mrdaniel.npcs.io.INPCStore;
import me.mrdaniel.npcs.managers.NPCManager;

import java.util.Collection;
import java.util.Optional;

public class NBTNPCStore implements INPCStore {

    public NBTNPCStore(NPCManager manager) {

    }

    @Override
    public void load() {

    }

    @Override
    public INPCData create(NPCType type) throws NPCException {
        throw new NPCException("NBT storage type is Unimplemented!");
    }

    @Override
    public void remove(INPCData data) throws NPCException {
        throw new NPCException("NBT storage type is Unimplemented!");
    }

    @Override
    public Optional<INPCData> getData(int id) {
        return Optional.empty();
    }

    @Override
    public Collection<INPCData> getData() {
        return Sets.newHashSet();
    }
}
