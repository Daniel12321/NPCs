package me.mrdaniel.npcs.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.spongepowered.api.data.type.Career;
import org.spongepowered.api.data.type.HorseColor;
import org.spongepowered.api.data.type.HorseStyle;
import org.spongepowered.api.data.type.LlamaVariant;
import org.spongepowered.api.data.type.OcelotType;
import org.spongepowered.api.data.type.RabbitType;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.world.World;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.reflect.TypeToken;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.actions.Action;
import me.mrdaniel.npcs.exceptions.ActionException;
import me.mrdaniel.npcs.utils.Position;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

public class NPCFile {

	private final int id;
	private final ConfigurationLoader<CommentedConfigurationNode> loader;
	private final CommentedConfigurationNode node;

	private final List<Action> actions;
	private final Map<UUID, Integer> current;
	private final Map<UUID, Long> cooldowns;

	public NPCFile(@Nonnull final Path storage_path, final int id) {
		Path path = storage_path.resolve("npc_" + id + ".conf");
		if (!Files.exists(path)) {
			try { Files.createFile(path); }
			catch (final IOException exc) { NPCs.getInstance().getLogger().error("Failed to create npc data file: {}", exc.getMessage(), exc); }
		}

		this.id = id;
		this.loader = HoconConfigurationLoader.builder().setPath(storage_path.resolve("npc_" + id + ".conf")).build();
		this.node = this.load();

		this.current = Maps.newHashMap();
		this.cooldowns = Maps.newHashMap();
		this.node.getNode("current").getChildrenMap().forEach((uuid, node) -> this.current.put(UUID.fromString((String)uuid), node.getInt(0)));
		this.node.getNode("cooldowns").getChildrenMap().forEach((uuid, node) -> this.cooldowns.put(UUID.fromString((String)uuid), node.getLong(0)));

		Action[] actions = new Action[this.node.getNode("actions").getChildrenMap().size()];
		for (int i = 0; i < actions.length; i++) {
			try { actions[i] = Action.of(this.node.getNode("actions", String.valueOf(i))); }
			catch (final ActionException exc) { NPCs.getInstance().getLogger().error("Failed to read action {} for npc {}!", i, id, exc); }
		}
		this.actions = Lists.newArrayList(actions);
	}

	private CommentedConfigurationNode load() {
		try { return this.loader.load(); }
		catch (final IOException exc) { NPCs.getInstance().getLogger().error("Failed to load npc data file for npc {}: {}", this.id, exc.getMessage(), exc); return this.loader.createEmptyNode(); }
	}

	public void save() {
		try { this.loader.save(this.node); }
		catch (final IOException exc) { NPCs.getInstance().getLogger().error("Failed to save npc data file for npc {}: {}", this.id, exc.getMessage(), exc); }
	}

	public int getId() { return this.id; }

	public NPCFile setWorld(@Nonnull final World value) { this.node.getNode("location", "world").setValue(value.getName()); return this; }
	public Optional<World> getWorld() { return NPCs.getInstance().getGame().getServer().getWorld(this.getWorldName()); }
	public String getWorldName() { return this.node.getNode("location", "world").getString("world"); }

	public NPCFile setPosition(@Nonnull final Position value) { this.setX(value.getX()).setY(value.getY()).setZ(value.getZ()).setYaw(value.getYaw()).setPitch(value.getPitch()); return this; }

	public NPCFile setX(final double value) { this.node.getNode("location", "x").setValue(value); return this; }
	public double getX() { return this.node.getNode("location", "x").getDouble(0.0); }

	public NPCFile setY(final double value) { this.node.getNode("location", "y").setValue(value); return this; }
	public double getY() { return this.node.getNode("location", "y").getDouble(0.0); }

	public NPCFile setZ(final double value) { this.node.getNode("location", "z").setValue(value); return this; }
	public double getZ() { return this.node.getNode("location", "z").getDouble(0.0); }

	public NPCFile setYaw(final float value) { this.node.getNode("location", "yaw").setValue(value); return this; }
	public float getYaw() { return this.node.getNode("location", "yaw").getFloat(0.0F); }

	public NPCFile setPitch(final float value) { this.node.getNode("location", "pitch").setValue(value); return this; }
	public float getPitch() { return this.node.getNode("location", "pitch").getFloat(0.0F); }

	public NPCFile setType(@Nonnull final EntityType value) { this.node.getNode("type").setValue(value.getId()); return this; }
	public Optional<EntityType> getType() { return Optional.ofNullable(this.getTypeName()).map(id -> NPCs.getInstance().getGame().getRegistry().getType(EntityType.class, id).orElse(null)); }
	public String getTypeName() { return this.node.getNode("type").getString(); }

	public NPCFile setName(@Nonnull final String value) { this.node.getNode("name").setValue(value); return this; }
	public Optional<String> getName() { return Optional.ofNullable(this.node.getNode("name").getString(null)); }

	public NPCFile setSkinName(@Nonnull final String value) { this.node.getNode("skin", "name").setValue(value); return this; }
	public Optional<String> getSkinName() { return Optional.ofNullable(this.node.getNode("skin", "name").getString(null)); }

	public NPCFile setSkinUUID(@Nonnull final UUID value) { this.node.getNode("skin", "uuid").setValue(value.toString()); return this; }
	public Optional<UUID> getSkinUUID() { return Optional.ofNullable(this.node.getNode("skin", "uuid").getString(null)).map(id -> UUID.fromString(id)); }

	public NPCFile setLooking(final boolean value) { this.node.getNode("looking").setValue(value); return this; }
	public boolean getLooking() { return this.node.getNode("looking").getBoolean(false); }

	public NPCFile setInteract(final boolean value) { this.node.getNode("interact").setValue(value); return this; }
	public boolean getInteract() { return this.node.getNode("interact").getBoolean(true); }

	public NPCFile setSilent(final boolean value) { this.node.getNode("silent").setValue(value); return this; }
	public boolean getSilent() { return this.node.getNode("silent").getBoolean(false); }

	public NPCFile setGlowing(final boolean value) { this.node.getNode("glowing", "enabled").setValue(value); return this; }
	public boolean getGlowing() { return this.node.getNode("glowing", "enabled").getBoolean(false); }

	public NPCFile setGlowColor(@Nonnull final TextColor value) { this.node.getNode("glowing", "color").setValue(value.getId()); return this; }
	public Optional<TextColor> getGlowColor() { return Optional.ofNullable(this.node.getNode("glowing", "color").getString()).map(id -> NPCs.getInstance().getGame().getRegistry().getType(TextColor.class, id).orElse(null)); }

	public NPCFile setBaby(final boolean value) { this.node.getNode("baby").setValue(value); return this; }
	public boolean getBaby() { return this.node.getNode("baby").getBoolean(false); }

	public NPCFile setCharged(final boolean value) { this.node.getNode("charged").setValue(value); return this; }
	public boolean getCharged() { return this.node.getNode("charged").getBoolean(false); }

	public NPCFile setAngry(final boolean value) { this.node.getNode("angry").setValue(value); return this; }
	public boolean getAngry() { return this.node.getNode("angry").getBoolean(false); }

	public NPCFile setSize(final int value) { this.node.getNode("size").setValue(value); return this; }
	public int getSize() { return this.node.getNode("size").getInt(0); }

	public NPCFile setSitting(final boolean value) { this.node.getNode("sitting").setValue(value); return this; }
	public boolean getSitting() { return this.node.getNode("sitting").getBoolean(false); }

	public NPCFile setSaddle(final boolean value) { this.node.getNode("saddle").setValue(value); return this; }
	public boolean getSaddle() { return this.node.getNode("saddle").getBoolean(false); }

	public NPCFile setCareer(@Nonnull final Career value) { this.node.getNode("career").setValue(value.getId()); return this; }
	public Optional<Career> getCareer() { return Optional.ofNullable(this.node.getNode("career").getString()).map(id -> NPCs.getInstance().getGame().getRegistry().getType(Career.class, id).orElse(null)); }

	public NPCFile setHorseStyle(@Nonnull final HorseStyle value) { this.node.getNode("horse", "style").setValue(value.getId()); return this; }
	public Optional<HorseStyle> getHorseStyle() { return Optional.ofNullable(this.node.getNode("horse", "style").getString()).map(id -> NPCs.getInstance().getGame().getRegistry().getType(HorseStyle.class, id).orElse(null)); }

	public NPCFile setHorseColor(@Nonnull final HorseColor value) { this.node.getNode("horse", "color").setValue(value.getId()); return this; }
	public Optional<HorseColor> getHorseColor() { return Optional.ofNullable(this.node.getNode("horse", "color").getString()).map(id -> NPCs.getInstance().getGame().getRegistry().getType(HorseColor.class, id).orElse(null)); }

	public NPCFile setLlamaVariant(@Nonnull final LlamaVariant value) { this.node.getNode("llamavariant").setValue(value.getId()); return this; }
	public Optional<LlamaVariant> getLlamaVariant() { return Optional.ofNullable(this.node.getNode("llamavariant").getString()).map(id -> NPCs.getInstance().getGame().getRegistry().getType(LlamaVariant.class, id).orElse(null)); }

	public NPCFile setCatType(@Nonnull final OcelotType value) { this.node.getNode("cattype").setValue(value.getId()); return this; }
	public Optional<OcelotType> getCatType() { return Optional.ofNullable(this.node.getNode("cattype").getString()).map(id -> NPCs.getInstance().getGame().getRegistry().getType(OcelotType.class, id).orElse(null)); }

	public NPCFile setRabbitType(@Nonnull final RabbitType value) { this.node.getNode("rabbittype").setValue(value.getId()); return this; }
	public Optional<RabbitType> getRabbitType() { return Optional.ofNullable(this.node.getNode("rabbittype").getString()).map(id -> NPCs.getInstance().getGame().getRegistry().getType(RabbitType.class, id).orElse(null)); }

	public NPCFile setHelmet(@Nullable final ItemStack value) { this.setItemStack(this.node.getNode("equipment", "helmet"), value); return this; }
	public Optional<ItemStack> getHelmet() { return this.getItemStack(this.node.getNode("equipment", "helmet")); }

	public NPCFile setChestplate(@Nullable final ItemStack value) { this.setItemStack(this.node.getNode("equipment", "chestplate"), value); return this; }
	public Optional<ItemStack> getChestplate() { return this.getItemStack(this.node.getNode("equipment", "chestplate")); }

	public NPCFile setLeggings(@Nullable final ItemStack value) { this.setItemStack(this.node.getNode("equipment", "leggings"), value); return this; }
	public Optional<ItemStack> getLeggings() { return this.getItemStack(this.node.getNode("equipment", "leggings")); }

	public NPCFile setBoots(@Nullable final ItemStack value) { this.setItemStack(this.node.getNode("equipment", "boots"), value); return this; }
	public Optional<ItemStack> getBoots() { return this.getItemStack(this.node.getNode("equipment", "boots")); }

	public NPCFile setMainHand(@Nullable final ItemStack value) { this.setItemStack(this.node.getNode("equipment", "mainhand"), value); return this; }
	public Optional<ItemStack> getMainHand() { return this.getItemStack(this.node.getNode("equipment", "mainhand")); }

	public NPCFile setOffHand(@Nullable final ItemStack value) { this.setItemStack(this.node.getNode("equipment", "offhand"), value); return this; }
	public Optional<ItemStack> getOffHand() { return this.getItemStack(this.node.getNode("equipment", "offhand")); }

	public NPCFile setRepeatActions(final boolean value) { this.node.getNode("repeat_actions").setValue(value); return this; }
	public boolean getRepeatActions() { return this.node.getNode("repeat_actions").getBoolean(true); }

	public List<Action> getActions() { return this.actions; }
	public NPCFile writeActions() {
		final boolean repeat = this.getRepeatActions();
		this.node.removeChild("actions");
		this.setRepeatActions(repeat);

		for (int i = 0; i < this.actions.size(); i++) {
			this.actions.get(i).serialize(this.node.getNode("actions", Integer.toString(i)));
		}
		return this;
	}

	public Map<UUID, Integer> getCurrent() { return this.current; }
	public NPCFile writeCurrent() {
		this.current.forEach((uuid, current) -> this.node.getNode("current", uuid.toString()).setValue(current)); return this;
	}

	public Map<UUID, Long> getCooldowns() { return this.cooldowns; }
	public NPCFile writeCooldowns() {
		this.cooldowns.forEach((uuid, end) -> this.node.getNode("cooldowns", uuid.toString()).setValue(end)); return this;
	}

	private Optional<ItemStack> getItemStack(@Nonnull final ConfigurationNode node) {
		try { return Optional.ofNullable(node.getValue(TypeToken.of(ItemStack.class))); }
		catch (final ObjectMappingException exc) { exc.printStackTrace(); return Optional.empty(); }
	}

	private NPCFile setItemStack(@Nonnull final ConfigurationNode node, @Nullable final ItemStack value) {
		try { node.setValue(TypeToken.of(ItemStack.class), value); return this; }
		catch (final ObjectMappingException exc) { exc.printStackTrace(); return this; }
	}
}