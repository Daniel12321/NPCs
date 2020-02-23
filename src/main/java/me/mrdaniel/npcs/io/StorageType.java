package me.mrdaniel.npcs.io;

import me.mrdaniel.npcs.io.database.Database;
import me.mrdaniel.npcs.io.hocon.HoconNPCStore;
import me.mrdaniel.npcs.io.nbt.NBTNPCStore;
import me.mrdaniel.npcs.managers.NPCManager;

import java.util.Optional;
import java.util.function.Function;

public enum StorageType {

    DATABASE("database", Database::new),
    HOCON("hocon", HoconNPCStore::new),
    NBT("nbt", NBTNPCStore::new);

    private String name;
    private Function<NPCManager, INPCStore> npcStoreSupplier;

    StorageType(String name, Function<NPCManager, INPCStore> npcStoreSupplier) {
        this.name = name;
        this.npcStoreSupplier = npcStoreSupplier;
    }

    public INPCStore createNPCStore(NPCManager manager) {
        return this.npcStoreSupplier.apply(manager);
    }

    public static Optional<StorageType> of(String name) {
        for (StorageType type : values()) {
            if (type.name.equals(name)) {
                return Optional.of(type);
            }
        }
        return Optional.empty();
    }
}
