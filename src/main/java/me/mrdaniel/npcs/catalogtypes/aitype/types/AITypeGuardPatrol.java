package me.mrdaniel.npcs.catalogtypes.aitype.types;

import com.google.common.collect.Lists;
import me.mrdaniel.npcs.ai.AITaskGuardPatrol;
import me.mrdaniel.npcs.catalogtypes.aitype.AIType;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.utils.Position;
import org.spongepowered.api.entity.ai.task.AITask;
import org.spongepowered.api.entity.living.Agent;
import org.spongepowered.api.entity.living.Creature;

import java.util.List;

public class AITypeGuardPatrol extends AIType {

    private static final List<Position> POSITIONS = Lists.newArrayList(
            new Position("world", -40.5, 63.0, 330.5, 0, 0),
            new Position("world", -30.5, 63.0, 330.5, 0, 0),
            new Position("world", -30.5, 63.0, 320.5, 0, 0),
            new Position("world", -40.5, 63.0, 320.5, 0, 0)
    );

    public AITypeGuardPatrol() {
        super("Guard Patrol", "guard_patrol");
    }

    @Override
    public AITask<? extends Agent> createAITask(Creature owner, NPCType npcType) {
        return new AITaskGuardPatrol(POSITIONS, npcType.getMovementSpeed());
    }
}
