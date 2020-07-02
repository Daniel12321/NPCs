package me.mrdaniel.npcs.ai.pattern;

import me.mrdaniel.npcs.ai.task.AITaskGuardRandom;
import me.mrdaniel.npcs.catalogtypes.aitype.AITypes;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import ninja.leaping.configurate.ConfigurationNode;
import org.spongepowered.api.entity.ai.task.AITask;
import org.spongepowered.api.entity.living.Agent;
import org.spongepowered.api.entity.living.Creature;

public class AIPatternGuardRandom extends AbstractAIGuardPattern {

    public AIPatternGuardRandom(NPCType npcType) {
        super(AITypes.GUARD_RANDOM, npcType);
    }

    public AIPatternGuardRandom(ConfigurationNode node) {
        super(AITypes.GUARD_RANDOM, node);
    }

    @Override
    public AITask<? extends Agent> createAITask(Creature owner) {
        return new AITaskGuardRandom(this.speed, this.chance, this.positions);
    }
}
