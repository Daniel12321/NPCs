package me.mrdaniel.npcs.io;

import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.exceptions.NPCException;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.Set;

public interface INPCStore {

    void load();
    NPCAble spawn(INPCData data) throws NPCException;
    NPCAble create(Player p, NPCType type) throws NPCException;
    void remove(CommandSource src, int id) throws NPCException;
	void remove(CommandSource src, INPCData file) throws NPCException;
	void remove(CommandSource src, INPCData data, @Nullable NPCAble npc) throws NPCException;

    Optional<INPCData> getData(int id);
    Optional<NPCAble> getNPC(INPCData data);
    Set<INPCData> getAllNPCs();

//    void save();
//    void delete();
}
