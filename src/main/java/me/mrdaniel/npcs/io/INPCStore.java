package me.mrdaniel.npcs.io;

import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.exceptions.NPCException;

import java.util.Map;

public interface INPCStore {

    void setup();
    void load(Map<Integer, INPCData> npcs);

    INPCData create(NPCType type) throws NPCException;
    void remove(INPCData data) throws NPCException;
}
