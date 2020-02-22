package me.mrdaniel.npcs.io.hocon;

import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.actions.Action;
import me.mrdaniel.npcs.actions.ActionTypeSerializer;
import me.mrdaniel.npcs.actions.conditions.Condition;
import me.mrdaniel.npcs.actions.conditions.ConditionTypeSerializer;
import me.mrdaniel.npcs.catalogtypes.actiontype.ActionType;
import me.mrdaniel.npcs.catalogtypes.career.Career;
import me.mrdaniel.npcs.catalogtypes.cattype.CatType;
import me.mrdaniel.npcs.catalogtypes.conditiontype.ConditionType;
import me.mrdaniel.npcs.catalogtypes.glowcolor.GlowColor;
import me.mrdaniel.npcs.catalogtypes.horsecolor.HorseColor;
import me.mrdaniel.npcs.catalogtypes.horsepattern.HorsePattern;
import me.mrdaniel.npcs.catalogtypes.llamatype.LlamaType;
import me.mrdaniel.npcs.catalogtypes.menupagetype.PageType;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.catalogtypes.parrottype.ParrotType;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.catalogtypes.rabbittype.RabbitType;
import me.mrdaniel.npcs.exceptions.NPCException;
import me.mrdaniel.npcs.io.INPCData;
import me.mrdaniel.npcs.io.INPCStore;
import me.mrdaniel.npcs.utils.Position;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializers;
import org.spongepowered.api.world.World;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class HoconNPCStore implements INPCStore {

    private final Path storageDir;

    public HoconNPCStore() {
        this.storageDir = NPCs.getInstance().getConfigDir().resolve("storage");
    }

    @Override
    public void setup() {
		CatalogTypeSerializer.register(NPCType.class);
		CatalogTypeSerializer.register(GlowColor.class);
		CatalogTypeSerializer.register(Career.class);
		CatalogTypeSerializer.register(CatType.class);
		CatalogTypeSerializer.register(HorseColor.class);
		CatalogTypeSerializer.register(HorsePattern.class);
		CatalogTypeSerializer.register(LlamaType.class);
		CatalogTypeSerializer.register(ParrotType.class);
		CatalogTypeSerializer.register(RabbitType.class);
		CatalogTypeSerializer.register(PageType.class);
		CatalogTypeSerializer.register(ActionType.class);
		CatalogTypeSerializer.register(ConditionType.class);
		CatalogTypeSerializer.register(PropertyType.class);
		TypeSerializers.getDefaultSerializers().registerType(TypeToken.of(Position.class), new PositionTypeSerializer());
		TypeSerializers.getDefaultSerializers().registerType(TypeToken.of(World.class), new WorldTypeSerializer());
		TypeSerializers.getDefaultSerializers().registerType(TypeToken.of(Action.class), new ActionTypeSerializer());
		TypeSerializers.getDefaultSerializers().registerType(TypeToken.of(Condition.class), new ConditionTypeSerializer());
	}

    @Override
    public void load(Map<Integer, INPCData> npcs) {
		if (!Files.exists(this.storageDir)) {
			try {
				Files.createDirectory(this.storageDir);
			} catch (final IOException exc) {
				NPCs.getInstance().getLogger().error("Failed to create main NPC storage directory: ",  exc);
			}
		}

		for (String name : this.storageDir.toFile().list()) {
			INPCData data = new HoconNPCData(this.storageDir.resolve(name));
		    npcs.put(data.getId(), data);
		}
    }

	@Override
	public INPCData create(NPCType type) throws NPCException {
		int nextId = this.getNextID();
		return new HoconNPCData(this.storageDir.resolve("npc_" + nextId + ".conf"), nextId);
	}

	@Override
	public void remove(INPCData data) throws NPCException {
        try {
            //TODO: Fix so it doesnt use directory
            Files.deleteIfExists(this.storageDir.resolve("npc_" + data.getId() + ".conf"));
        } catch (final IOException exc) {
            NPCs.getInstance().getLogger().error("Failed to delete npc data for npc {}: {}", data.getId(), exc.getMessage(), exc);
        }
	}

    // TODO: Change so it doesnt use IDs in directory
	private int getNextID() {
		int highest = 1;
		while (Files.exists(this.storageDir.resolve("npc_" + highest + ".conf"))) {
			++highest;
		}
		return highest;
	}
}
