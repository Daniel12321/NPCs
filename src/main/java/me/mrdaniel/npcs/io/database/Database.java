package me.mrdaniel.npcs.io.database;

import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.exceptions.NPCException;
import me.mrdaniel.npcs.io.INPCData;
import me.mrdaniel.npcs.io.INPCStore;
import me.mrdaniel.npcs.managers.NPCManager;

import java.util.Map;

public class Database implements INPCStore {

    public Database(NPCManager manager) {

    }

    @Override
    public void setup() {

    }

    @Override
    public void load(Map<Integer, INPCData> npcs) {

    }

    @Override
    public INPCData create(NPCType type) throws NPCException {
        throw new NPCException("Database storage type is Unimplemented!");
    }

    @Override
    public void remove(INPCData data) throws NPCException {
        throw new NPCException("Database storage type is Unimplemented!");
    }
}
