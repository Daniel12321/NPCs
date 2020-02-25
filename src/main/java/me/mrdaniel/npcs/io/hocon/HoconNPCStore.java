package me.mrdaniel.npcs.io.hocon;

import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.actions.Action;
import me.mrdaniel.npcs.actions.ActionSet;
import me.mrdaniel.npcs.actions.ActionSetTypeSerializer;
import me.mrdaniel.npcs.actions.Condition;
import me.mrdaniel.npcs.actions.actions.ActionTypeSerializer;
import me.mrdaniel.npcs.actions.conditions.ConditionTypeSerializer;
import me.mrdaniel.npcs.catalogtypes.actiontype.ActionType;
import me.mrdaniel.npcs.catalogtypes.career.Career;
import me.mrdaniel.npcs.catalogtypes.cattype.CatType;
import me.mrdaniel.npcs.catalogtypes.color.ColorType;
import me.mrdaniel.npcs.catalogtypes.conditiontype.ConditionType;
import me.mrdaniel.npcs.catalogtypes.horsecolor.HorseColor;
import me.mrdaniel.npcs.catalogtypes.horsepattern.HorsePattern;
import me.mrdaniel.npcs.catalogtypes.llamatype.LlamaType;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.catalogtypes.parrottype.ParrotType;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.catalogtypes.rabbittype.RabbitType;
import me.mrdaniel.npcs.io.INPCData;
import me.mrdaniel.npcs.io.INPCStore;
import me.mrdaniel.npcs.managers.NPCManager;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class HoconNPCStore implements INPCStore {

	private final NPCManager manager;
    private final Path storageDir;

    public HoconNPCStore(NPCManager manager, Path configDir) {
    	this.manager = manager;
        this.storageDir = configDir.resolve("storage");
    }

    @Override
    public void setup() {
		CatalogTypeSerializer.register(NPCType.class);
		CatalogTypeSerializer.register(ColorType.class);
		CatalogTypeSerializer.register(Career.class);
		CatalogTypeSerializer.register(CatType.class);
		CatalogTypeSerializer.register(HorseColor.class);
		CatalogTypeSerializer.register(HorsePattern.class);
		CatalogTypeSerializer.register(LlamaType.class);
		CatalogTypeSerializer.register(ParrotType.class);
		CatalogTypeSerializer.register(RabbitType.class);
		CatalogTypeSerializer.register(ActionType.class);
		CatalogTypeSerializer.register(ConditionType.class);
		CatalogTypeSerializer.register(PropertyType.class);
		TypeSerializers.getDefaultSerializers().registerType(TypeToken.of(Action.class), new ActionTypeSerializer());
		TypeSerializers.getDefaultSerializers().registerType(TypeToken.of(Condition.class), new ConditionTypeSerializer());
		TypeSerializers.getDefaultSerializers().registerType(TypeToken.of(ActionSet.class), new ActionSetTypeSerializer());
	}

    @Override
    public void load(Map<Integer, INPCData> npcs) {
		if (!Files.exists(this.storageDir)) {
			try {
				Files.createDirectories(this.storageDir);
			} catch (final IOException exc) {
				NPCs.getInstance().getLogger().error("Failed to create main NPC storage directory: ",  exc);
			}
		}

		for (String name : this.storageDir.toFile().list()) {
			INPCData data = new HoconNPCData(this.storageDir, name);
		    npcs.put(data.getId(), data);
		}
    }

	@Override
	public INPCData create(NPCType type) {
		int nextId = this.manager.getNextID();
		return new HoconNPCData(this.storageDir, "npc_" + nextId + ".conf", nextId);
	}

	@Override
	public void remove(INPCData data) {
        try {
            Files.deleteIfExists(this.storageDir.resolve(((HoconNPCData)data).getFileName()));
        } catch (final IOException exc) {
            NPCs.getInstance().getLogger().error("Failed to delete npc data for npc {}: {}", data.getId(), exc.getMessage(), exc);
        }
	}
}
