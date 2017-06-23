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
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3d;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.reflect.TypeToken;

import me.mrdaniel.npcs.NPCObject;
import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.data.npc.actions.Action;
import me.mrdaniel.npcs.exceptions.ActionException;
import me.mrdaniel.npcs.utils.TextUtils;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

public class NPCFile extends NPCObject {

	private final int id;
	private final ConfigurationLoader<CommentedConfigurationNode> loader;
	private final CommentedConfigurationNode node;

	private final List<Action> actions;
	private final Map<UUID, Integer> current;

	public NPCFile(@Nonnull final NPCs npcs, @Nonnull final Path storage_path, final int id) {
		super(npcs);

		Path path = storage_path.resolve("npc_" + id + ".conf");
		if (!Files.exists(path)) {
			try { Files.createFile(path); }
			catch (final IOException exc) { npcs.getLogger().error("Failed to create npc data file: {}", exc.getMessage()); }
		}

		this.id = id;
		this.loader = HoconConfigurationLoader.builder().setPath(storage_path.resolve("npc_" + id + ".conf")).build();
		this.node = this.load();
		this.current = Maps.newHashMap();
		this.node.getNode("current").getChildrenMap().forEach((uuid, current) -> this.current.put(UUID.fromString((String)uuid), current.getInt(0)));

		Action[] actions = new Action[this.node.getNode("actions").getChildrenMap().size()];
		for (int i = 0; i < actions.length; i++) {
			try { actions[i] = Action.of(this.node.getNode("actions", String.valueOf(i))); }
			catch (final ActionException exc) { npcs.getLogger().error("Failed to read action {} for npc {}!", i, id); }
		}
		this.actions = Lists.newArrayList(actions);
	}

	private CommentedConfigurationNode load() {
		try { return this.loader.load(); }
		catch (final IOException exc) { super.getLogger().error("Failed to load npc data file for npc {}: {}", this.id, exc.getMessage()); return this.loader.createEmptyNode(); }
	}

	public void save() {
		try { this.loader.save(this.node); }
		catch (final IOException exc) { super.getLogger().error("Failed to save npc data file for npc {}: {}", this.id, exc.getMessage()); }
	}

	public void delete(@Nonnull final Path storage_path) {
		try { Files.delete(storage_path.resolve("npc_" + this.id + ".conf")); }
		catch (final IOException exc) { super.getLogger().error("Failed to delete npc data file for npc {}: {}", this.id, exc.getMessage()); }
	}

	public int getId() { return this.id; }

	@Nonnull public Optional<Location<World>> getLocation() { return this.getWorld().map(w -> w.getLocation(this.getPosition())); }
	@Nonnull public Optional<World> getWorld() { return super.getServer().getWorld(this.getWorldName()); }
	@Nonnull public String getWorldName() { return this.node.getNode("location", "world").getString("world"); }
	@Nonnull public Vector3d getPosition() { return new Vector3d(this.node.getNode("location", "x").getDouble(), this.node.getNode("location", "y").getDouble(), this.node.getNode("location", "z").getDouble()); }

	public void setLocation(@Nonnull final Location<World> loc) { this.setWorld(loc.getExtent()); this.setPosition(loc.getPosition()); }
	public void setWorld(@Nonnull final World world) { this.node.getNode("location", "world").setValue(world.getName()); }
	public void setPosition(@Nonnull final Vector3d pos) { this.node.getNode("location", "x").setValue(pos.getX()); this.node.getNode("location", "y").setValue(pos.getY()); this.node.getNode("location", "z").setValue(pos.getZ()); }

	@Nonnull public Vector3d getRotation() { return new Vector3d(this.node.getNode("rotation", "x").getDouble(), this.node.getNode("rotation", "y").getDouble(), this.node.getNode("rotation", "z").getDouble()); }
	public void setRotation(@Nonnull final Vector3d rot) { this.node.getNode("rotation", "x").setValue(rot.getX()); this.node.getNode("rotation", "y").setValue(rot.getY()); this.node.getNode("rotation", "z").setValue(rot.getZ()); }

	@Nonnull public Vector3d getHead() { return new Vector3d(this.node.getNode("head", "x").getDouble(), this.node.getNode("head", "y").getDouble(), this.node.getNode("head", "z").getDouble()); }
	public void setHead(@Nonnull final Vector3d rot) { this.node.getNode("head", "x").setValue(rot.getX()); this.node.getNode("head", "y").setValue(rot.getY()); this.node.getNode("head", "z").setValue(rot.getZ()); }

	@Nonnull public Optional<EntityType> getType() { return Optional.ofNullable(this.node.getNode("type").getString()).map(id -> super.getGame().getRegistry().getType(EntityType.class, id).orElse(null)); }
	public void setType(@Nonnull final EntityType type) { this.node.getNode("type").setValue(type.getId()); }

	@Nonnull public Optional<Text> getName() { return Optional.ofNullable(this.node.getNode("name").getString(null)).map(name -> TextUtils.toText(name)); }
	public void setName(@Nonnull final Text name) { this.node.getNode("name").setValue(TextUtils.toString(name)); }

	@Nonnull public Optional<String> getSkinName() { return Optional.ofNullable(this.node.getNode("skin", "name").getString(null)); }
	public void setSkinName(@Nonnull final String name) { this.node.getNode("skin", "name").setValue(name); }

	@Nonnull public Optional<UUID> getSkinUUID() { return Optional.ofNullable(this.node.getNode("skin", "uuid").getString(null)).map(id -> UUID.fromString(id)); }
	public void setSkinUUID(@Nonnull final UUID uuid) { this.node.getNode("skin", "uuid").setValue(uuid.toString()); }

	@Nonnull public Optional<TextColor> getGlowColor() { return Optional.ofNullable(this.node.getNode("glow", "color").getString()).map(id -> super.getGame().getRegistry().getType(TextColor.class, id).orElse(null)); }
	public void setGlowColor(@Nonnull final TextColor color) { this.node.getNode("glow", "color").setValue(color.getId()); }

	public boolean getGlow() { return this.node.getNode("glow", "enabled").getBoolean(false); }
	public void setGlow(final boolean enabled) { this.node.getNode("glow", "enabled").setValue(enabled); }

	public boolean getInteract() { return this.node.getNode("interact").getBoolean(true); }
	public void setInteract(final boolean interact) { this.node.getNode("interact").setValue(interact); }

	public boolean getLooking() { return this.node.getNode("looking").getBoolean(false); }
	public void setLooking(final boolean looking) { this.node.getNode("looking").setValue(looking); }

	public boolean getCharged() { return this.node.getNode("charged").getBoolean(false); }
	public void setCharged(final boolean charged) { this.node.getNode("charged").setValue(charged); }

	public boolean getAngry() { return this.node.getNode("angry").getBoolean(false); }
	public void setAngry(final boolean angry) { this.node.getNode("angry").setValue(angry); }

	public int getSize() { return this.node.getNode("size").getInt(0); }
	public void setSize(final int size) { this.node.getNode("size").setValue(size); }

	public boolean getSitting() { return this.node.getNode("sitting").getBoolean(false); }
	public void setSitting(final boolean sitting) { this.node.getNode("sitting").setValue(sitting); }

	@Nonnull public Optional<Career> getCareer() { return Optional.ofNullable(this.node.getNode("career").getString()).map(id -> super.getGame().getRegistry().getType(Career.class, id).orElse(null)); }
	public void setCareer(@Nonnull final Career career) { this.node.getNode("career").setValue(career.getId()); }

	@Nonnull public Optional<HorseStyle> getHorseStyle() { return Optional.ofNullable(this.node.getNode("horse", "style").getString()).map(id -> super.getGame().getRegistry().getType(HorseStyle.class, id).orElse(null)); }
	public void setHorseStyle(@Nonnull final HorseStyle style) { this.node.getNode("horse", "style").setValue(style.getId()); }

	@Nonnull public Optional<HorseColor> getHorseColor() { return Optional.ofNullable(this.node.getNode("horse", "color").getString()).map(id -> super.getGame().getRegistry().getType(HorseColor.class, id).orElse(null)); }
	public void setHorseColor(@Nonnull final HorseColor color) { this.node.getNode("horse", "color").setValue(color.getId()); }

	@Nonnull public Optional<LlamaVariant> getVariant() { return Optional.ofNullable(this.node.getNode("variant").getString()).map(id -> super.getGame().getRegistry().getType(LlamaVariant.class, id).orElse(null)); }
	public void setVariant(@Nonnull final LlamaVariant variant) { this.node.getNode("variant").setValue(variant.getId()); }

	@Nonnull public Optional<OcelotType> getCat() { return Optional.ofNullable(this.node.getNode("cat").getString()).map(id -> super.getGame().getRegistry().getType(OcelotType.class, id).orElse(null)); }
	public void setCat(@Nonnull final OcelotType cat) { this.node.getNode("cat").setValue(cat.getId()); }

	@Nonnull public Optional<ItemStack> getHelmet() { return this.getItemStack(this.node.getNode("equipment", "helmet")); }
	public void setHelmet(@Nullable final ItemStack stack) { this.setItemStack(this.node.getNode("equipment", "helmet"), stack); }

	@Nonnull public Optional<ItemStack> getChestplate() { return this.getItemStack(this.node.getNode("equipment", "chestplate")); }
	public void setChestplate(@Nullable final ItemStack stack) { this.setItemStack(this.node.getNode("equipment", "chestplate"), stack); }

	@Nonnull public Optional<ItemStack> getLeggings() { return this.getItemStack(this.node.getNode("equipment", "leggings")); }
	public void setLeggings(@Nullable final ItemStack stack) { this.setItemStack(this.node.getNode("equipment", "leggings"), stack); }

	@Nonnull public Optional<ItemStack> getBoots() { return this.getItemStack(this.node.getNode("equipment", "boots")); }
	public void setBoots(@Nullable final ItemStack stack) { this.setItemStack(this.node.getNode("equipment", "boots"), stack); }

	@Nonnull public Optional<ItemStack> getMainHand() { return this.getItemStack(this.node.getNode("equipment", "mainhand")); }
	public void setMainHand(@Nullable final ItemStack stack) { this.setItemStack(this.node.getNode("equipment", "mainhand"), stack); }

	@Nonnull public Optional<ItemStack> getOffHand() { return this.getItemStack(this.node.getNode("equipment", "offhand")); }
	public void setOffHand(@Nullable final ItemStack stack) { this.setItemStack(this.node.getNode("equipment", "offhand"), stack); }

	public boolean getRepeatActions() { return this.node.getNode("repeat_actions").getBoolean(true); }
	public void setRepeatActions(final boolean repeat_actions) { this.node.getNode("repeat_actions").setValue(repeat_actions); }

	@Nonnull public List<Action> getActions() { return this.actions; }
	public void writeActions() {
		final boolean repeat = this.getRepeatActions();
		this.node.removeChild("actions");

		this.setRepeatActions(repeat);
		for (int i = 0; i < this.actions.size(); i++) {
			this.actions.get(i).serialize(this.node.getNode("actions", String.valueOf(i)));
		}
	}

	@Nonnull public Map<UUID, Integer> getCurrent() { return this.current; }
	public void writeCurrent() {
		this.current.forEach((uuid, current) -> this.node.getNode("current", uuid.toString()).setValue(current));
	}

	@Nonnull
	private Optional<ItemStack> getItemStack(@Nonnull final ConfigurationNode node) {
		try { return Optional.ofNullable(node.getValue(TypeToken.of(ItemStack.class))); }
		catch (final ObjectMappingException e) { e.printStackTrace(); return Optional.empty(); }
	}

	@Nonnull
	private void setItemStack(@Nonnull final ConfigurationNode node, @Nullable final ItemStack stack) {
		try { node.setValue(TypeToken.of(ItemStack.class), stack); }
		catch (final ObjectMappingException e) { e.printStackTrace(); }
	}
}