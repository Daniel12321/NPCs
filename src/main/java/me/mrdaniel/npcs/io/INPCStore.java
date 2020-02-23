package me.mrdaniel.npcs.io;

import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.exceptions.NPCException;

import java.util.Map;

public interface INPCStore {

    void setup();
    void load(Map<Integer, INPCData> npcs);

    INPCData create(NPCType type) throws NPCException;
    void remove(INPCData data) throws NPCException;
//    NPCAble spawn(INPCData data) throws NPCException;
//    void remove(CommandSource src, int id) throws NPCException;
//    void remove(CommandSource src, INPCData data, @Nullable NPCAble npc) throws NPCException;
//
//    Optional<INPCData> getData(int id);
//    Optional<NPCAble> getNPC(INPCData data);
//    List<INPCData> getNPCs(String worldName);

//    void save();
//    void delete();
}
