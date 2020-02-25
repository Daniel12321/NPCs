package me.mrdaniel.npcs.io;

import me.mrdaniel.npcs.actions.ActionSet;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.utils.Position;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;

public interface INPCData {

    int getId();

    @Nullable UUID getUniqueId();
    void setUniqueId(@Nullable UUID uuid);

    Position getPosition();
    INPCData setPosition(Position value);

    <T> Optional<T> getProperty(PropertyType<T> property);
    <T> INPCData setProperty(PropertyType<T> property, T value);

    ActionSet getActions();
    INPCData writeActions();

    void save();
}
























/*
    int getId();
    
    INPCStore setType(NPCType value);
    Optional<NPCType> getType();
    String getTypeName();

    INPCStore setWorld(World value);
    Optional<World> getWorld();
    String getWorldName();

    INPCStore setPosition(Position value);
    Position getPosition();
    Vector3i getChunkPosition();

    INPCStore setX(final double value);
    double getX();

    INPCStore setY(final double value);
    double getY();

    INPCStore setZ(final double value);
    double getZ();

    INPCStore setYaw(final float value);
    float getYaw();

    INPCStore setPitch(final float value);
    float getPitch();

    INPCStore setName(String value);
    Optional<String> getName();

    INPCStore setSkinName(String value);
    Optional<String> getSkinName();

    INPCStore setSkinUUID(UUID value);
    Optional<UUID> getSkinUUID();

    INPCStore setLooking(boolean value);
    boolean getLooking();

    INPCStore setInteract(boolean value);
    boolean getInteract();

    INPCStore setSilent(boolean value);
    boolean getSilent();

    INPCStore setGlowing(boolean value);
    boolean getGlowing();

    INPCStore setGlowColor(GlowColor value);
    Optional<GlowColor> getGlowColor();

    INPCStore setBaby(boolean value);
    boolean getBaby();

    INPCStore setCharged(boolean value);
    boolean getCharged();

    INPCStore setAngry(boolean value);
    boolean getAngry();

    INPCStore setSize(int value);
    int getSize();

    INPCStore setSitting(boolean value);
    boolean getSitting();

    INPCStore setSaddle(boolean value);
    boolean getSaddle();

    INPCStore setHanging(boolean value);
    boolean getHanging();

    INPCStore setPumpkin(boolean value);
    boolean getPumpkin();

    INPCStore setCareer(Career value);
    Optional<Career> getCareer();

    INPCStore setHorsePattern(HorsePattern value);
    Optional<HorsePattern> getHorsePattern();

    INPCStore setHorseColor(HorseColor value);
    Optional<HorseColor> getHorseColor();

    INPCStore setLlamaType(LlamaType value);
    Optional<LlamaType> getLlamaType();

    INPCStore setCatType(CatType value);
    Optional<CatType> getCatType();

    INPCStore setRabbitType(RabbitType value);
    Optional<RabbitType> getRabbitType();

    INPCStore setParrotType(ParrotType value);
    Optional<ParrotType> getParrotType();

    INPCStore setHelmet(@Nullable ItemStack value);
    Optional<ItemStack> getHelmet();

    INPCStore setChestplate(@Nullable ItemStack value);
    Optional<ItemStack> getChestplate();

    INPCStore setLeggings(@Nullable ItemStack value);
    Optional<ItemStack> getLeggings();

    INPCStore setBoots(@Nullable ItemStack value);
    Optional<ItemStack> getBoots();

    INPCStore setMainHand(@Nullable ItemStack value);
    Optional<ItemStack> getMainHand();

    INPCStore setOffHand(@Nullable ItemStack value);
    Optional<ItemStack> getOffHand();

    INPCStore setRepeatActions(boolean value);
    boolean getRepeatActions();

    List<Action> getActions();
    INPCStore writeActions();

    Map<UUID, Integer> getCurrent();
    INPCStore writeCurrent();

    Map<UUID, Long> getCooldowns();
    INPCStore writeCooldowns();
 */
