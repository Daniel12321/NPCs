package me.mrdaniel.npcs.io;

import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.exceptions.NPCException;

import java.util.Collection;
import java.util.Optional;

public interface INPCStore {

    void setup();
    void load();

    INPCData create(NPCType type) throws NPCException;
    void remove(INPCData data) throws NPCException;

    Optional<INPCData> getData(int id);
    Collection<INPCData> getData();
}
