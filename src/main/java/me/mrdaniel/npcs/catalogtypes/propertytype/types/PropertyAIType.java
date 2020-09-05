package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.catalogtypes.aitype.AIType;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCTypes;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyTypes;
import me.mrdaniel.npcs.io.database.DatabaseNPCData;
import me.mrdaniel.npcs.io.hocon.HoconNPCData;
import me.mrdaniel.npcs.io.nbt.NBTNPCData;
import me.mrdaniel.npcs.mixin.interfaces.NPCAble;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.entity.living.monster.Slime;
import org.spongepowered.api.text.Text;

import javax.annotation.Nullable;

public class PropertyAIType extends PropertyType<AIType> {

    public PropertyAIType() {
        super("AI Type", "aitype", GenericArguments.catalogedElement(Text.of("aitype"), AIType.class));
    }

    @Override
    public TypeToken<AIType> getTypeToken() {
        return TypeToken.of(AIType.class);
    }

    @Override
    public boolean isSupported(NPCAble npc) {
        return !(npc instanceof Slime);
    }

    @Override
    public boolean isSupported(NPCType type) {
        return type != NPCTypes.SLIME && type != NPCTypes.MAGMA_CUBE;
    }

    @Override
    public void apply(NPCAble npc, AIType value) {
        npc.refreshAI();
    }

    @Override
    public void setHocon(HoconNPCData data, AIType value) {
        data.ai.pattern = value.newInstance(data.getProperty(PropertyTypes.TYPE).get());
    }

    @Override
    public void setDatabase(DatabaseNPCData data, AIType value) {

    }

    @Override
    public void setNBT(NBTNPCData data, AIType value) {

    }

    @Nullable
    @Override
    public AIType getHocon(HoconNPCData data) {
        return data.ai.pattern.getType();
    }

    @Nullable
    @Override
    public AIType getDatabase(DatabaseNPCData data) {
        return null;
    }

    @Nullable
    @Override
    public AIType getNBT(NBTNPCData data) {
        return null;
    }
}
