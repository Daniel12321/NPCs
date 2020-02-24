package me.mrdaniel.npcs.io.hocon;

import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.actions.ActionSet;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.io.Config;
import me.mrdaniel.npcs.io.INPCData;
import me.mrdaniel.npcs.utils.Position;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import javax.annotation.Nullable;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

public class HoconNPCData extends Config implements INPCData {

    private final int id;
    private final String fileName;
    private final ActionSet actions;

    @Nullable private UUID npcUUID;

	public HoconNPCData(Path path, String fileName, int id) {
        super(path.resolve(fileName));

        this.id = id;
        this.fileName = fileName;
        this.actions = new ActionSet();
        this.npcUUID = null;

        super.getNode("id").setValue(this.id);
    }

    public HoconNPCData(Path path, String fileName) {
        super(path.resolve(fileName));

        this.id = super.getNode("id").getInt();
        this.fileName = fileName;
        this.actions = this.loadActions();
        this.npcUUID = null;
    }

    private ActionSet loadActions() {
        try {
            return super.getNode("actions").getValue(TypeToken.of(ActionSet.class), new ActionSet());
        } catch (ObjectMappingException exc) {
            NPCs.getInstance().getLogger().error("Failed to deserialize npc actions: ", exc);
            return new ActionSet();
        }
    }

    @Override
    public int getNPCId() {
        return this.id;
    }

    @Nullable
    @Override
    public UUID getNPCUUID() {
        return this.npcUUID;
    }

    @Override
    public void setNPCUUID(@Nullable UUID uuid) {
	    this.npcUUID = uuid;
    }

    @Override
    public Position getNPCPosition() {
        return new Position(
                super.getNode("position", "world").getString("world"),
                super.getNode("position", "x").getDouble(0),
                super.getNode("position", "y").getDouble(0),
                super.getNode("position", "z").getDouble(0),
                super.getNode("position", "yaw").getFloat(0),
                super.getNode("position", "pitch").getFloat(0)
        );
    }

    @Override
    public INPCData setNPCPosition(Position value) {
        super.getNode("position", "world").setValue(value.getWorldName());
        super.getNode("position", "x").setValue(value.getX());
        super.getNode("position", "y").setValue(value.getY());
        super.getNode("position", "z").setValue(value.getZ());
        super.getNode("position", "yaw").setValue(value.getYaw());
        super.getNode("position", "pitch").setValue(value.getPitch());
        return this;
    }

    @Override
    public <T> Optional<T> getNPCProperty(PropertyType<T> property) {
        try {
            return Optional.ofNullable(super.getNode(property.getHoconPath()).getValue(property.getTypeToken()));
        } catch (ObjectMappingException exc) {
            return Optional.empty();
        }
    }

    @Override
    public <T> INPCData setNPCProperty(PropertyType<T> property, T value) {
        try {
            super.getNode(property.getHoconPath()).setValue(property.getTypeToken(), value);
        } catch (ObjectMappingException exc) {
            NPCs.getInstance().getLogger().error("Failed to save property to file: ", exc);
            return this;
        }
        return this;
    }

    @Override
    public ActionSet getNPCActions() {
        return this.actions;
    }

    @Override
    public INPCData writeNPCActions() {
        try {
            super.getNode("actions").setValue(TypeToken.of(ActionSet.class), this.actions);
        } catch (ObjectMappingException exc) {
            NPCs.getInstance().getLogger().error("Failed to save ActionSet to hocon file: ", exc);
        }
        return this;
    }

    public String getFileName() {
	    return this.fileName;
    }
}
