package me.mrdaniel.npcs.io.hocon;

import com.google.common.collect.Maps;
import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.io.INPCData;
import me.mrdaniel.npcs.io.INPCStore;
import me.mrdaniel.npcs.io.hocon.config.Config;
import me.mrdaniel.npcs.managers.NPCManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class HoconNPCStore implements INPCStore {

	private final NPCManager manager;
    private final Path storageDir;

	private final Map<Integer, Config<HoconNPCData>> data;

	public HoconNPCStore(NPCManager manager, Path configDir) {
    	this.manager = manager;
        this.storageDir = configDir.resolve("storage");

		this.data = Maps.newHashMap();
    }

    @Override
    public void load() {
		this.data.values().forEach(config -> config.get().config = null); // Prevents memory leaks
		this.data.clear();

		if (!Files.exists(this.storageDir)) {
			try {
				Files.createDirectories(this.storageDir);
			} catch (final IOException exc) {
				NPCs.getInstance().getLogger().error("Failed to create main NPC storage directory: ",  exc);
			}
		}

		for (String name : this.storageDir.toFile().list()) {
			Config<HoconNPCData> data = new Config<>(HoconNPCData.class, this.storageDir, name);
			data.get().config = data;

			this.data.put(data.get().id, data);
		}
    }

	@Override
	public INPCData create(NPCType type) {
		int nextId = this.manager.getNextID();
		String fileName = "npc_" + nextId + ".conf";
		Config<HoconNPCData> data = new Config<>(HoconNPCData.class, this.storageDir, fileName);
		data.get().config = data;
		data.get().id = nextId;
		data.save();

		this.data.put(nextId, data);
		return data.get();
	}

	@Override
	public void remove(INPCData data) {
		HoconNPCData hoconData = (HoconNPCData) data;
		hoconData.config = null;  // Prevents memory leaks

		Config<HoconNPCData> file = this.data.remove(hoconData.id);
		if (file != null) {
			file.delete();
		}
	}

	@Override
	public Optional<INPCData> getData(int id) {
		return Optional.ofNullable(this.data.get(id)).map(Config::get);
	}

	@Override
	public Collection<INPCData> getData() {
		return this.data.values().stream().map(Config::get).collect(Collectors.toSet());
	}
}
