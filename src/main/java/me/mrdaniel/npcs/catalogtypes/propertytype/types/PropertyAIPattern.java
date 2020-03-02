package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.ai.pattern.AbstractAIPattern;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCTypes;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.mixin.interfaces.NPCAble;
import org.spongepowered.api.entity.living.monster.Slime;

public class PropertyAIPattern extends PropertyType<AbstractAIPattern> {

    public PropertyAIPattern() {
        super("Ai Pattern", "aipattern", new Object[]{"ai"});
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
}
