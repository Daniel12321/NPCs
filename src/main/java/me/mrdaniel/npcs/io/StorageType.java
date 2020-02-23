package me.mrdaniel.npcs.io;

import me.mrdaniel.npcs.io.database.Database;
import me.mrdaniel.npcs.io.hocon.HoconNPCStore;
import me.mrdaniel.npcs.io.nbt.NBTNPCStore;
import me.mrdaniel.npcs.managers.NPCManager;

import java.nio.file.Path;
import java.util.Optional;
import java.util.function.BiFunction;

public enum StorageType {

    DATABASE("database", (manager, configDir) -> new Database(manager)),
    HOCON("hocon", HoconNPCStore::new),
    NBT("nbt", (manager, configDir) -> new NBTNPCStore(manager));

    private String name;
    private BiFunction<NPCManager, Path, INPCStore> npcStoreSupplier;

    StorageType(String name, BiFunction<NPCManager, Path, INPCStore> npcStoreSupplier) {
        this.name = name;
        this.npcStoreSupplier = npcStoreSupplier;
    }

    public INPCStore createNPCStore(NPCManager manager, Path configDir) {
        return this.npcStoreSupplier.apply(manager, configDir);
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
