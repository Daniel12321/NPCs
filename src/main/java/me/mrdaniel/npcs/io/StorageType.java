package me.mrdaniel.npcs.io;

import me.mrdaniel.npcs.io.database.Database;
import me.mrdaniel.npcs.io.hocon.HoconNPCStore;
import me.mrdaniel.npcs.io.nbt.NBTNPCStore;

import java.util.Optional;
import java.util.function.Supplier;

public enum StorageType {

    DATABASE("database", Database::new),
    HOCON("hocon", HoconNPCStore::new),
    NBT("nbt", NBTNPCStore::new);

    private String name;
    private Supplier<INPCStore> npcStoreSupplier;

    StorageType(String name, Supplier<INPCStore> npcStoreSupplier) {
        this.name = name;
        this.npcStoreSupplier = npcStoreSupplier;
    }

    public INPCStore createNPCStore() {
        return this.npcStoreSupplier.get();
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
