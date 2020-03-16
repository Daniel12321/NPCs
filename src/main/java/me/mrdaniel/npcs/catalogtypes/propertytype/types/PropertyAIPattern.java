package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.ai.pattern.AbstractAIPattern;
import me.mrdaniel.npcs.catalogtypes.aitype.AIType;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCTypes;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.io.database.DatabaseNPCData;
import me.mrdaniel.npcs.io.hocon.HoconNPCData;
import me.mrdaniel.npcs.io.nbt.NBTNPCData;
import me.mrdaniel.npcs.mixin.interfaces.NPCAble;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.entity.living.monster.Slime;
import org.spongepowered.api.text.Text;

import javax.annotation.Nullable;

public class PropertyAIPattern extends PropertyType<AbstractAIPattern> {

    public PropertyAIPattern() {
        super("Ai Pattern", "aipattern", GenericArguments.catalogedElement(Text.of("aitype"), AIType.class));
    }

    @Override
    public TypeToken<AbstractAIPattern> getTypeToken() {
        return TypeToken.of(AbstractAIPattern.class);
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
    public void apply(NPCAble npc, AbstractAIPattern value) {
        npc.refreshAI();
    }

    @Override
    public void setHocon(HoconNPCData data, AbstractAIPattern value) {
        data.ai.pattern = value;
    }

    @Override
    public void setDatabase(DatabaseNPCData data, AbstractAIPattern value) {

    }

    @Override
    public void setNBT(NBTNPCData data, AbstractAIPattern value) {

    }

    @Nullable
    @Override
    public AbstractAIPattern getHocon(HoconNPCData data) {
        return data.ai.pattern;
    }

    @Nullable
    @Override
    public AbstractAIPattern getDatabase(DatabaseNPCData data) {
        return null;
    }

    @Nullable
    @Override
    public AbstractAIPattern getNBT(NBTNPCData data) {
        return null;
    }
}
