package me.mrdaniel.npcs.io.hocon;

import me.mrdaniel.npcs.actions.ActionSet;
import me.mrdaniel.npcs.ai.pattern.AbstractAIPattern;
import me.mrdaniel.npcs.catalogtypes.career.Career;
import me.mrdaniel.npcs.catalogtypes.cattype.CatType;
import me.mrdaniel.npcs.catalogtypes.color.ColorType;
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
import me.mrdaniel.npcs.utils.Position;
import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.item.inventory.ItemStack;

import java.util.Optional;
import java.util.UUID;

@ConfigSerializable
public class HoconNPCData implements INPCData {

    public Config<HoconNPCData> config;

    @Setting public int id;
    @Setting public NPCType type;
    @Setting public Position position;
    @Setting public UUID uuid;

    @Setting public Skin skin = new Skin();
    @Setting public String name;
    @Setting public boolean nameAlwaysVisible;
    @Setting public boolean silent;
    @Setting public boolean interact;
    @Setting public boolean burning;
    @Setting public Glow glow = new Glow();

    @Setting public boolean angry;
    @Setting public boolean armored;
    @Setting public boolean baby;
    @Setting public boolean charged;
    @Setting public boolean chest;
    @Setting public boolean eating;
    @Setting public boolean hanging;
    @Setting public boolean peeking;
    @Setting public boolean pumpkin;
    @Setting public boolean screaming;
    @Setting public boolean sheared;
    @Setting public boolean sitting;
    @Setting public boolean saddle;
    @Setting public int size;
    @Setting public BlockType carries;
    @Setting public Career career;
    @Setting public DyeColorType color;
    @Setting public HorseArmorType horsearmor;
    @Setting public HorsePattern horsepattern;
    @Setting public HorseColor horsecolor;
    @Setting public LlamaType llamatype;
    @Setting public CatType cattype;
    @Setting public RabbitType rabbittype;
    @Setting public ParrotType parrottype;

    @Setting public Equipment equipment = new Equipment();
    @Setting public AI ai = new AI();
    @Setting public ActionSet actions = new ActionSet();

    @ConfigSerializable
    public static class Skin {
        @Setting public String name;
        @Setting public UUID uuid;
    }

    @ConfigSerializable
    public static class Glow {
        @Setting public boolean enabled;
        @Setting public ColorType color;
    }

    @ConfigSerializable
    public static class AI {
        @Setting public boolean looking;
        @Setting public AbstractAIPattern pattern;
    }

    @ConfigSerializable
    public static class Equipment {
        @Setting public ItemStack helmet;
        @Setting public ItemStack chestplate;
        @Setting public ItemStack leggings;
        @Setting public ItemStack boots;
        @Setting public ItemStack mainhand;
        @Setting public ItemStack offhand;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public <T> Optional<T> getProperty(PropertyType<T> property) {
        return Optional.ofNullable(property.getHocon(this));
    }

    @Override
    public <T> INPCData setProperty(PropertyType<T> property, T value) {
        property.setHocon(this, value);
        return this;
    }

    @Override
    public void save() {
        this.config.save();
    }
}
