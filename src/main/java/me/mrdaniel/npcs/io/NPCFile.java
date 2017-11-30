package me.mrdaniel.npcs.io;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3d;
import com.flowpowered.math.vector.Vector3i;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.reflect.TypeToken;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.actions.Action;
import me.mrdaniel.npcs.catalogtypes.career.Career;
import me.mrdaniel.npcs.catalogtypes.cattype.CatType;
import me.mrdaniel.npcs.catalogtypes.glowcolor.GlowColor;
import me.mrdaniel.npcs.catalogtypes.horsecolor.HorseColor;
import me.mrdaniel.npcs.catalogtypes.horsepattern.HorsePattern;
import me.mrdaniel.npcs.catalogtypes.llamatype.LlamaType;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.catalogtypes.parrottype.ParrotType;
import me.mrdaniel.npcs.catalogtypes.rabbittype.RabbitType;
import me.mrdaniel.npcs.exceptions.ActionException;
import me.mrdaniel.npcs.utils.Position;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

public class NPCFile extends Config {

	private final int id;

	private final List<Action> actions;
	private final Map<UUID, Integer> current;
	private final Map<UUID, Long> cooldowns;

	public NPCFile(@Nonnull final Path storage_path, final int id) {
		super(storage_path.resolve("npc_" + id + ".conf"));

		this.id = id;

		this.current = Maps.newHashMap();
		this.cooldowns = Maps.newHashMap();
		this.getNode("current").getChildrenMap().forEach((uuid, node) -> this.current.put(UUID.fromString((String)uuid), node.getInt(0)));
		this.getNode("cooldowns").getChildrenMap().forEach((uuid, node) -> this.cooldowns.put(UUID.fromString((String)uuid), node.getLong(0)));

		Action[] actions = new Action[this.getNode("actions").getChildrenMap().size()];
		for (int i = 0; i < actions.length; i++) {
			try { actions[i] = Action.of(this.getNode("actions", String.valueOf(i))); }
			catch (final ActionException exc) { NPCs.getInstance().getLogger().error("Failed to read action {} for npc {}!", i, id, exc); }
		}
		this.actions = Lists.newArrayList(actions);
	}

	public int getId() { return this.id; }

	public NPCFile setWorld(@Nonnull final World value) { this.getNode("location", "world").setValue(value.getName()); return this; }
	public Optional<World> getWorld() { return NPCs.getInstance().getGame().getServer().getWorld(this.getWorldName()); }
	public String getWorldName() { return this.getNode("location", "world").getString("world"); }

	public NPCFile setPosition(@Nonnull final Position value) { this.setX(value.getX()).setY(value.getY()).setZ(value.getZ()).setYaw(value.getYaw()).setPitch(value.getPitch()); return this; }
	public Position getPosition() { return new Position(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch()); }
	public Vector3i getChunkPosition() { return new Vector3d(this.getX(), 0, this.getZ()).toInt().div(16); }

	public NPCFile setX(final double value) { this.getNode("location", "x").setValue(value); return this; }
	public double getX() { return this.getNode("location", "x").getDouble(0.0); }

	public NPCFile setY(final double value) { this.getNode("location", "y").setValue(value); return this; }
	public double getY() { return this.getNode("location", "y").getDouble(0.0); }

	public NPCFile setZ(final double value) { this.getNode("location", "z").setValue(value); return this; }
	public double getZ() { return this.getNode("location", "z").getDouble(0.0); }

	public NPCFile setYaw(final float value) { this.getNode("location", "yaw").setValue(value); return this; }
	public float getYaw() { return this.getNode("location", "yaw").getFloat(0.0F); }

	public NPCFile setPitch(final float value) { this.getNode("location", "pitch").setValue(value); return this; }
	public float getPitch() { return this.getNode("location", "pitch").getFloat(0.0F); }

	public NPCFile setType(@Nonnull final NPCType value) { this.getNode("type").setValue(value.getId()); return this; }
	public Optional<NPCType> getType() { return NPCs.getInstance().getGame().getRegistry().getType(NPCType.class, this.getTypeName()); }
	public String getTypeName() { return this.getNode("type").getString(); }

	public NPCFile setName(@Nonnull final String value) { this.getNode("name").setValue(value); return this; }
	public Optional<String> getName() { return Optional.ofNullable(this.getNode("name").getString(null)); }

	public NPCFile setSkinName(@Nonnull final String value) { this.getNode("skin", "name").setValue(value); return this; }
	public Optional<String> getSkinName() { return Optional.ofNullable(this.getNode("skin", "name").getString(null)); }

	public NPCFile setSkinUUID(@Nonnull final UUID value) { this.getNode("skin", "uuid").setValue(value.toString()); return this; }
	public Optional<UUID> getSkinUUID() { return Optional.ofNullable(this.getNode("skin", "uuid").getString(null)).map(id -> UUID.fromString(id)); }

	public NPCFile setLooking(final boolean value) { this.getNode("looking").setValue(value); return this; }
	public boolean getLooking() { return this.getNode("looking").getBoolean(false); }

	public NPCFile setInteract(final boolean value) { this.getNode("interact").setValue(value); return this; }
	public boolean getInteract() { return this.getNode("interact").getBoolean(true); }

	public NPCFile setSilent(final boolean value) { this.getNode("silent").setValue(value); return this; }
	public boolean getSilent() { return this.getNode("silent").getBoolean(false); }

	public NPCFile setGlowing(final boolean value) { this.getNode("glowing", "enabled").setValue(value); return this; }
	public boolean getGlowing() { return this.getNode("glowing", "enabled").getBoolean(false); }

	public NPCFile setGlowColor(@Nonnull final GlowColor value) { this.getNode("glow", "color").setValue(value.getId()); return this; }
	public Optional<GlowColor> getGlowColor() { return NPCs.getInstance().getGame().getRegistry().getType(GlowColor.class, this.getNode("glow", "color").getString("")); }

	public NPCFile setBaby(final boolean value) { this.getNode("baby").setValue(value); return this; }
	public boolean getBaby() { return this.getNode("baby").getBoolean(false); }

	public NPCFile setCharged(final boolean value) { this.getNode("charged").setValue(value); return this; }
	public boolean getCharged() { return this.getNode("charged").getBoolean(false); }

	public NPCFile setAngry(final boolean value) { this.getNode("angry").setValue(value); return this; }
	public boolean getAngry() { return this.getNode("angry").getBoolean(false); }

	public NPCFile setSize(final int value) { this.getNode("size").setValue(value); return this; }
	public int getSize() { return this.getNode("size").getInt(0); }

	public NPCFile setSitting(final boolean value) { this.getNode("sitting").setValue(value); return this; }
	public boolean getSitting() { return this.getNode("sitting").getBoolean(false); }

	public NPCFile setSaddle(final boolean value) { this.getNode("saddle").setValue(value); return this; }
	public boolean getSaddle() { return this.getNode("saddle").getBoolean(false); }

	public NPCFile setHanging(final boolean value) { this.getNode("hanging").setValue(value); return this; }
	public boolean getHanging() { return this.getNode("hanging").getBoolean(false); }

	public NPCFile setPumpkin(final boolean value) { this.getNode("pumpkin").setValue(value); return this; }
	public boolean getPumpkin() { return this.getNode("pumpkin").getBoolean(false); }

	public NPCFile setCareer(@Nonnull final Career value) { this.getNode("career").setValue(value.getId()); return this; }
	public Optional<Career> getCareer() { return NPCs.getInstance().getGame().getRegistry().getType(Career.class, this.getNode("career").getString("")); }

	public NPCFile setHorsePattern(@Nonnull final HorsePattern value) { this.getNode("horse", "pattern").setValue(value.getId()); return this; }
	public Optional<HorsePattern> getHorsePattern() { return NPCs.getInstance().getGame().getRegistry().getType(HorsePattern.class, this.getNode("horse", "pattern").getString("")); }

	public NPCFile setHorseColor(@Nonnull final HorseColor value) { this.getNode("horse", "color").setValue(value.getId()); return this; }
	public Optional<HorseColor> getHorseColor() { return NPCs.getInstance().getGame().getRegistry().getType(HorseColor.class, this.getNode("horse", "color").getString("")); }

	public NPCFile setLlamaType(@Nonnull final LlamaType value) { this.getNode("llamatype").setValue(value.getId()); return this; }
	public Optional<LlamaType> getLlamaType() { return NPCs.getInstance().getGame().getRegistry().getType(LlamaType.class, this.getNode("llamatype").getString("")); }

	public NPCFile setCatType(@Nonnull final CatType value) { this.getNode("cattype").setValue(value.getId()); return this; }
	public Optional<CatType> getCatType() { return NPCs.getInstance().getGame().getRegistry().getType(CatType.class, this.getNode("cattype").getString("")); }

	public NPCFile setRabbitType(@Nonnull final RabbitType value) { this.getNode("rabbittype").setValue(value.getId()); return this; }
	public Optional<RabbitType> getRabbitType() { return NPCs.getInstance().getGame().getRegistry().getType(RabbitType.class, this.getNode("rabbittype").getString("")); }

	public NPCFile setParrotType(@Nonnull final ParrotType value) { this.getNode("parrottype").setValue(value.getId()); return this; }
	public Optional<ParrotType> getParrotType() { return NPCs.getInstance().getGame().getRegistry().getType(ParrotType.class, this.getNode("parrottype").getString("")); }

	public NPCFile setHelmet(@Nullable final ItemStack value) { this.setItemStack(this.getNode("equipment", "helmet"), value); return this; }
	public Optional<ItemStack> getHelmet() { return this.getItemStack(this.getNode("equipment", "helmet")); }

	public NPCFile setChestplate(@Nullable final ItemStack value) { this.setItemStack(this.getNode("equipment", "chestplate"), value); return this; }
	public Optional<ItemStack> getChestplate() { return this.getItemStack(this.getNode("equipment", "chestplate")); }

	public NPCFile setLeggings(@Nullable final ItemStack value) { this.setItemStack(this.getNode("equipment", "leggings"), value); return this; }
	public Optional<ItemStack> getLeggings() { return this.getItemStack(this.getNode("equipment", "leggings")); }

	public NPCFile setBoots(@Nullable final ItemStack value) { this.setItemStack(this.getNode("equipment", "boots"), value); return this; }
	public Optional<ItemStack> getBoots() { return this.getItemStack(this.getNode("equipment", "boots")); }

	public NPCFile setMainHand(@Nullable final ItemStack value) { this.setItemStack(this.getNode("equipment", "mainhand"), value); return this; }
	public Optional<ItemStack> getMainHand() { return this.getItemStack(this.getNode("equipment", "mainhand")); }

	public NPCFile setOffHand(@Nullable final ItemStack value) { this.setItemStack(this.getNode("equipment", "offhand"), value); return this; }
	public Optional<ItemStack> getOffHand() { return this.getItemStack(this.getNode("equipment", "offhand")); }

	public NPCFile setRepeatActions(final boolean value) { this.getNode("repeat_actions").setValue(value); return this; }
	public boolean getRepeatActions() { return this.getNode("repeat_actions").getBoolean(true); }

	public List<Action> getActions() { return this.actions; }
	public NPCFile writeActions() {
		final boolean repeat = this.getRepeatActions();
		this.getNode().removeChild("actions");
		this.setRepeatActions(repeat);

		for (int i = 0; i < this.actions.size(); i++) {
			this.actions.get(i).serialize(this.getNode("actions", Integer.toString(i)));
		}
		return this;
	}

	public Map<UUID, Integer> getCurrent() { return this.current; }
	public NPCFile writeCurrent() {
		this.current.forEach((uuid, current) -> this.getNode("current", uuid.toString()).setValue(current)); return this;
	}

	public Map<UUID, Long> getCooldowns() { return this.cooldowns; }
	public NPCFile writeCooldowns() {
		this.cooldowns.forEach((uuid, end) -> this.getNode("cooldowns", uuid.toString()).setValue(end)); return this;
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