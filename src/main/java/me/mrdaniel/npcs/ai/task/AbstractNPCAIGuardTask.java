package me.mrdaniel.npcs.ai.task;

import me.mrdaniel.npcs.utils.Position;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import org.spongepowered.api.entity.ai.task.AITaskType;
import org.spongepowered.api.entity.ai.task.AITaskTypes;

import java.util.List;

public abstract class AbstractNPCAIGuardTask extends AbstractNPCAITask {

    protected final List<Position> targets;

    /**
     * Creates a new {@link AbstractNPCAIGuardTask} with the provided
     * {@link AITaskType}.
     */
    public AbstractNPCAIGuardTask(double speed, int chance, List<Position> targets) {
        super(AITaskTypes.WANDER, speed, chance);

        this.targets = targets;

        ((EntityAIBase) (Object) this).setMutexBits(MUTEX_FLAG_MOVE);
    }
}
