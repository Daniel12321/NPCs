package me.mrdaniel.npcs.io.nbt;

import com.google.common.collect.Sets;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.exceptions.NPCException;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.io.INPCData;
import me.mrdaniel.npcs.io.INPCStore;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.Set;

public class NBTNPCStore implements INPCStore {

    @Override
    public void load() {
    }

    @Override
    public NPCAble spawn(INPCData data) throws NPCException {
        throw new NPCException("NBT storage type is Unimplemented!");
    }

    @Override
    public NPCAble create(Player p, NPCType type) throws NPCException {
        throw new NPCException("NBT storage type is Unimplemented!");
    }

    @Override
    public void remove(CommandSource src, int id) throws NPCException {
    }

    @Override
    public void remove(CommandSource src, INPCData file) throws NPCException {
    }

    @Override
    public void remove(CommandSource src, INPCData data, @Nullable NPCAble npc) throws NPCException {
    }

    @Override
    public Optional<INPCData> getData(int id) {
        return Optional.empty();
    }

    @Override
    public Optional<NPCAble> getNPC(INPCData data) {
        return Optional.empty();
    }

    @Override
    public Set<INPCData> getAllNPCs() {
        return Sets.newHashSet();
    }
}
