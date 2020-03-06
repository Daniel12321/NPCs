package me.mrdaniel.npcs.ai.pattern;

import me.mrdaniel.npcs.catalogtypes.aitype.AIType;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.entity.ai.task.AITask;
import org.spongepowered.api.entity.living.Agent;
import org.spongepowered.api.entity.living.Creature;
import org.spongepowered.api.text.Text;

import java.util.List;

public abstract class AbstractAIPattern {

    private final AIType type;

    protected AbstractAIPattern(AIType type) {
        this.type = type;
    }

    public AIType getType() {
        return this.type;
    }

    // TODO: Remove NPCType ???
    public abstract AITask<? extends Agent> createAITask(Creature owner, NPCType npcType);
    public abstract List<Text> getMenuLines();
    public abstract void serializeValue(ConfigurationNode node) throws ObjectMappingException;
}
