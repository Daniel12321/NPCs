package me.mrdaniel.npcs.catalogtypes.aitype.types;

import me.mrdaniel.npcs.catalogtypes.aitype.AIType;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import org.spongepowered.api.entity.ai.task.AITask;
import org.spongepowered.api.entity.ai.task.builtin.creature.WanderAITask;
import org.spongepowered.api.entity.living.Agent;
import org.spongepowered.api.entity.living.Creature;

public class AITypeWander extends AIType {

    public AITypeWander() {
        super("Wander", "wander");
    }

    @Override
    public AITask<? extends Agent> createAITask(Creature owner, NPCType npcType) {
        return WanderAITask.builder().speed(npcType.getMovementSpeed()).executionChance(50).build(owner);
    }
}
