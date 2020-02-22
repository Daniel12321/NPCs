package me.mrdaniel.npcs.io.nbt;

import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.exceptions.NPCException;
import me.mrdaniel.npcs.io.INPCData;
import me.mrdaniel.npcs.io.INPCStore;

import java.util.Map;

public class NBTNPCStore implements INPCStore {

    @Override
    public void setup() {
    }

    @Override
    public void load(Map<Integer, INPCData> npcs) {
    }

    @Override
    public INPCData create(NPCType type) throws NPCException {
        throw new NPCException("NBT storage type is Unimplemented!");
    }

    @Override
    public void remove(INPCData data) throws NPCException {
        throw new NPCException("NBT storage type is Unimplemented!");
    }
}
