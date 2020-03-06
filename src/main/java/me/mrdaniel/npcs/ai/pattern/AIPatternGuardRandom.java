package me.mrdaniel.npcs.ai.pattern;

import me.mrdaniel.npcs.ai.task.AITaskGuardRandom;
import me.mrdaniel.npcs.catalogtypes.aitype.AITypes;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.utils.Position;
import ninja.leaping.configurate.ConfigurationNode;
import org.spongepowered.api.entity.ai.task.AITask;
import org.spongepowered.api.entity.living.Agent;
import org.spongepowered.api.entity.living.Creature;

import java.util.List;

public class AIPatternGuardRandom extends AbstractAIGuardPattern {

    public AIPatternGuardRandom(ConfigurationNode node) {
        super(AITypes.GUARD_RANDOM, node);
    }

    public AIPatternGuardRandom(List<Position> positions) {
        super(AITypes.GUARD_RANDOM, positions);
    }

    @Override
    public AITask<? extends Agent> createAITask(Creature owner, NPCType npcType) {
        return new AITaskGuardRandom(this.positions, npcType.getMovementSpeed());
    }
}
