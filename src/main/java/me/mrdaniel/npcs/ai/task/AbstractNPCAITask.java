package me.mrdaniel.npcs.ai.task;

import me.mrdaniel.npcs.utils.Position;
import net.minecraft.entity.EntityLiving;
import org.spongepowered.api.entity.ai.task.AITaskType;
import org.spongepowered.api.entity.ai.task.AbstractAITask;
import org.spongepowered.api.entity.living.Agent;

public abstract class AbstractNPCAITask extends AbstractAITask<Agent> {

    /**
     * Creates a new {@link AbstractAITask} with the provided
     * {@link AITaskType}.
     *
     * @param type The type
     */
    public AbstractNPCAITask(AITaskType type) {
        super(type);
    }

    protected void moveTo(EntityLiving el, Position pos, double movementSpeed) {
        if (!el.getNavigator().tryMoveToXYZ(pos.getX(), pos.getY(), pos.getZ(), movementSpeed)) {
            el.setPosition(pos.getX(), pos.getY(), pos.getZ());
        }
    }
}
