package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.catalogtypes.aitype.AIType;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCTypes;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.mixin.interfaces.NPCAble;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.entity.living.monster.Slime;
import org.spongepowered.api.text.Text;

public class PropertyAIType extends PropertyType<AIType> {

    public PropertyAIType() {
        super("Ai Type", "aitype", new Object[]{"ai", "Type"}, GenericArguments.catalogedElement(Text.of("aitype"), AIType.class));
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
}
