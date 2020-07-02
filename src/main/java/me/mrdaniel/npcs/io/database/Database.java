package me.mrdaniel.npcs.io.database;

import com.google.common.collect.Sets;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.exceptions.NPCException;
import me.mrdaniel.npcs.io.INPCData;
import me.mrdaniel.npcs.io.INPCStore;
import me.mrdaniel.npcs.managers.NPCManager;

import java.util.Collection;
import java.util.Optional;

public class Database implements INPCStore {

    public Database(NPCManager manager) {

    }

    @Override
    public void load() {

    }

    @Override
    public INPCData create(NPCType type) throws NPCException {
        throw new NPCException("Database storage type is Unimplemented!");
    }

    @Override
    public void remove(INPCData data) throws NPCException {
        throw new NPCException("Database storage type is Unimplemented!");
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
