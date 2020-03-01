package me.mrdaniel.npcs.catalogtypes.aitype.types;

import me.mrdaniel.npcs.ai.AITaskStayInPosition;
import me.mrdaniel.npcs.catalogtypes.aitype.AIType;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import org.spongepowered.api.entity.ai.task.AITask;
import org.spongepowered.api.entity.living.Agent;
import org.spongepowered.api.entity.living.Creature;

public class AITypeStay extends AIType {

    public AITypeStay() {
        super("Stay", "stay");
    }

    @Override
    public AITask<? extends Agent> createAITask(Creature owner, NPCType npcType) {
        return new AITaskStayInPosition(npcType.getMovementSpeed());
    }
}
