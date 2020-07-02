package me.mrdaniel.npcs.io.hocon;

import com.google.common.collect.Maps;
import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.actions.Action;
import me.mrdaniel.npcs.actions.ActionSet;
import me.mrdaniel.npcs.actions.Condition;
import me.mrdaniel.npcs.ai.pattern.AbstractAIPattern;
import me.mrdaniel.npcs.catalogtypes.actiontype.ActionType;
import me.mrdaniel.npcs.catalogtypes.aitype.AIType;
import me.mrdaniel.npcs.catalogtypes.career.Career;
import me.mrdaniel.npcs.catalogtypes.cattype.CatType;
import me.mrdaniel.npcs.catalogtypes.color.ColorType;
import me.mrdaniel.npcs.catalogtypes.conditiontype.ConditionType;
import me.mrdaniel.npcs.catalogtypes.dyecolor.DyeColorType;
import me.mrdaniel.npcs.catalogtypes.horsearmor.HorseArmorType;
import me.mrdaniel.npcs.catalogtypes.horsecolor.HorseColor;
import me.mrdaniel.npcs.catalogtypes.horsepattern.HorsePattern;
import me.mrdaniel.npcs.catalogtypes.llamatype.LlamaType;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.catalogtypes.parrottype.ParrotType;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.catalogtypes.rabbittype.RabbitType;
import me.mrdaniel.npcs.io.hocon.config.Config;
import me.mrdaniel.npcs.io.INPCData;
import me.mrdaniel.npcs.io.INPCStore;
import me.mrdaniel.npcs.io.hocon.typeserializers.*;
import me.mrdaniel.npcs.managers.NPCManager;
import me.mrdaniel.npcs.utils.Position;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializers;

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
    public void setup() {
		CatalogTypeSerializer.register(AIType.class);
		CatalogTypeSerializer.register(Career.class);
		CatalogTypeSerializer.register(CatType.class);
		CatalogTypeSerializer.register(ColorType.class);
		CatalogTypeSerializer.register(DyeColorType.class);
		CatalogTypeSerializer.register(HorseArmorType.class);
		CatalogTypeSerializer.register(HorseColor.class);
		CatalogTypeSerializer.register(HorsePattern.class);
		CatalogTypeSerializer.register(LlamaType.class);
		CatalogTypeSerializer.register(NPCType.class);
		CatalogTypeSerializer.register(ParrotType.class);
		CatalogTypeSerializer.register(RabbitType.class);

		CatalogTypeSerializer.register(ActionType.class);
		CatalogTypeSerializer.register(ConditionType.class);
		CatalogTypeSerializer.register(PropertyType.class);
		TypeSerializers.getDefaultSerializers().registerType(TypeToken.of(Action.class), new ActionTypeSerializer());
		TypeSerializers.getDefaultSerializers().registerType(TypeToken.of(Condition.class), new ConditionTypeSerializer());
		TypeSerializers.getDefaultSerializers().registerType(TypeToken.of(ActionSet.class), new ActionSetTypeSerializer());
		TypeSerializers.getDefaultSerializers().registerType(TypeToken.of(Position.class), new PositionTypeSerializer());
		TypeSerializers.getDefaultSerializers().registerType(TypeToken.of(AbstractAIPattern.class), new AIPatternTypeSerializer());
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
			Config<HoconNPCData> data = new Config<>(HoconNPCData.class, this.storageDir.resolve(name));
			data.get().fileName = name;
			data.get().config = data;

			this.data.put(data.get().id, data);
		}
    }

	@Override
	public INPCData create(NPCType type) {
		int nextId = this.manager.getNextID();
		String fileName = "npc_" + nextId + ".conf";
		Config<HoconNPCData> data = new Config<>(HoconNPCData.class, this.storageDir.resolve(fileName));
		data.get().fileName = fileName;
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

		this.data.remove(hoconData.id);

        try {
            Files.deleteIfExists(this.storageDir.resolve(hoconData.fileName));
        } catch (final IOException exc) {
            NPCs.getInstance().getLogger().error("Failed to delete npc data for npc {}: {}", hoconData.id, exc.getMessage(), exc);
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
