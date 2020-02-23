package me.mrdaniel.npcs.io.hocon;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.actions.Action;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.exceptions.ActionException;
import me.mrdaniel.npcs.io.Config;
import me.mrdaniel.npcs.io.INPCData;
import me.mrdaniel.npcs.utils.Position;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class HoconNPCData extends Config implements INPCData {

    private final int id;

    private final List<Action> actions;
    private final Map<UUID, Integer> current;
	private final Map<UUID, Long> cooldowns;

	public HoconNPCData(Path path, int id) {
        super(path);

        this.id = id;
        this.actions = Lists.newArrayList();
        this.current = Maps.newHashMap();
        this.cooldowns = Maps.newHashMap();

        super.getNode("id").setValue(this.id);
        this.load();
    }

    public HoconNPCData(Path path) {
        super(path);

        this.id = super.getNode("id").getInt();
        this.actions = Lists.newArrayList();
        this.current = Maps.newHashMap();
        this.cooldowns = Maps.newHashMap();

        this.load();
    }

    private void load() {
        this.getNode("current").getChildrenMap().forEach((uuid, node) -> this.current.put(UUID.fromString((String)uuid), node.getInt(0)));
        this.getNode("cooldowns").getChildrenMap().forEach((uuid, node) -> this.cooldowns.put(UUID.fromString((String)uuid), node.getLong(0)));

        for (int i = 0; i < this.getNode("actions").getChildrenMap().size(); i++) {
            try {
                actions.add(Action.of(this.getNode("actions", String.valueOf(i))));
            } catch (final ActionException exc) {
                NPCs.getInstance().getLogger().error("Failed to read action {} for npc {}!", i, id, exc);
            }
        }
    }

    @Override
    public int getId() {
        return this.id;
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
        try {
            super.getNode(property.getHoconPath()).setValue(property.getTypeToken(), value);
        } catch (ObjectMappingException exc) {
            NPCs.getInstance().getLogger().error("Failed to save property to file: ", exc);
            return this;
        }
        return this;
    }

    @Override
    public Position getPosition() {
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
    public INPCData setPosition(Position value) {
        super.getNode("position", "world").setValue(value.getWorldName());
        super.getNode("position", "x").setValue(value.getX());
        super.getNode("position", "y").setValue(value.getY());
        super.getNode("position", "z").setValue(value.getZ());
        super.getNode("position", "yaw").setValue(value.getYaw());
        super.getNode("position", "pitch").setValue(value.getPitch());
        return this;
    }

    @Override
    public INPCData setRepeatActions(boolean value) {
        return this;
    }

    @Override
    public boolean getRepeatActions() {
        return false;
    }

    @Override
    public List<Action> getActions() {
        return Lists.newArrayList();
    }

    @Override
    public INPCData writeActions() {
        return this;
    }

    @Override
    public Map<UUID, Integer> getCurrent() {
        return Maps.newHashMap();
    }

    @Override
    public INPCData writeCurrent() {
        return this;
    }

    @Override
    public Map<UUID, Long> getCooldowns() {
        return Maps.newHashMap();
    }

    @Override
    public INPCData writeCooldowns() {
        return this;
    }
}
