package me.mrdaniel.npcs.ai.pattern;

import me.mrdaniel.npcs.ai.task.AITaskGuardPatrol;
import me.mrdaniel.npcs.catalogtypes.aitype.AITypes;
import ninja.leaping.configurate.ConfigurationNode;
import org.spongepowered.api.entity.ai.task.AITask;
import org.spongepowered.api.entity.living.Agent;
import org.spongepowered.api.entity.living.Creature;

public class AIPatternGuardPatrol extends AbstractAIGuardPattern {

    public AIPatternGuardPatrol(ConfigurationNode node) {
        super(AITypes.GUARD_PATROL, node);
    }

    @Override
    public AITask<? extends Agent> createAITask(Creature owner) {
        return new AITaskGuardPatrol(this.positions, super.speed, super.chance);
    }
}
