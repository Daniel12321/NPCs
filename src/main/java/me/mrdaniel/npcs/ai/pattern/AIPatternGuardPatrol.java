package me.mrdaniel.npcs.ai.pattern;

import me.mrdaniel.npcs.ai.AITaskGuardPatrol;
import me.mrdaniel.npcs.catalogtypes.aitype.AITypes;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.utils.Position;
import org.spongepowered.api.entity.ai.task.AITask;
import org.spongepowered.api.entity.living.Agent;
import org.spongepowered.api.entity.living.Creature;

import java.util.List;

public class AIPatternGuardPatrol extends AbstractAIGuardPattern {

    public AIPatternGuardPatrol(List<Position> positions) {
        super(AITypes.GUARD_PATROL, positions);
    }

    @Override
    public AITask<? extends Agent> createAITask(Creature owner, NPCType npcType) {
        return new AITaskGuardPatrol(this.positions, npcType.getMovementSpeed());
    }
}
