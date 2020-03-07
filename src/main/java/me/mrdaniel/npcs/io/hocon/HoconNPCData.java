package me.mrdaniel.npcs.io.hocon;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyTypes;
import me.mrdaniel.npcs.io.Config;
import me.mrdaniel.npcs.io.INPCData;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import javax.annotation.Nullable;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

public class HoconNPCData extends Config implements INPCData {

    private final int id;
    private final String fileName;

    @Nullable private UUID uuid;

    public HoconNPCData(Path path, String fileName, int id) {
        super(path.resolve(fileName));

        this.id = id;
        this.fileName = fileName;
        this.uuid = null;

        super.getNode("id").setValue(this.id);
    }

    public HoconNPCData(Path path, String fileName) {
        super(path.resolve(fileName));

        this.id = super.getNode("id").getInt();
        this.fileName = fileName;

        String uuid = super.getNode("uuid").getString(null);
        this.uuid = uuid == null ? null : UUID.fromString(uuid);
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Nullable
    @Override
    public UUID getUniqueId() {
	    return this.uuid;
    }

    @Override
    public void setUniqueId(@Nullable UUID uuid) {
	    this.uuid = uuid;
	    super.getNode("uuid").setValue(uuid == null ? null : uuid.toString());
    }

    @Override
    public <T> Optional<T> getProperty(PropertyType<T> property) {
        try {
            return Optional.ofNullable(super.getNode(property.getHoconPath()).getValue(property.getTypeToken()));
        } catch (ObjectMappingException exc) {
            return Optional.empty();
        }
    }

    @Override
    public <T> INPCData setProperty(PropertyType<T> property, T value) {
        if (property == PropertyTypes.AI_TYPE) { // TODO: Find a better way
            super.getNode().removeChild("ai");
        }
        try {
            super.getNode(property.getHoconPath()).setValue(property.getTypeToken(), value);
        } catch (ObjectMappingException exc) {
            NPCs.getInstance().getLogger().error("Failed to save property to file: ", exc);
            return this;
        }
        return this;
    }

    public String getFileName() {
	    return this.fileName;
    }
}
