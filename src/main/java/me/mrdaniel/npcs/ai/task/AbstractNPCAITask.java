package me.mrdaniel.npcs.ai.task;

import me.mrdaniel.npcs.utils.Position;
import net.minecraft.entity.EntityLiving;
import org.spongepowered.api.entity.ai.task.AITaskType;
import org.spongepowered.api.entity.ai.task.AbstractAITask;
import org.spongepowered.api.entity.living.Agent;

public abstract class AbstractNPCAITask extends AbstractAITask<Agent> {

    protected final double speed;
    protected final int chance;

    /**
     * Creates a new {@link AbstractAITask} with the provided
     * {@link AITaskType}.
     *
     * @param type The type
     */
    public AbstractNPCAITask(AITaskType type, double speed, int chance) {
        super(type);

        this.speed = speed;
        this.chance = chance;
    }

    protected void moveTo(EntityLiving el, Position pos) {
        if (!el.getNavigator().tryMoveToXYZ(pos.getX(), pos.getY(), pos.getZ(), this.speed)) {
            el.setPosition(pos.getX(), pos.getY(), pos.getZ());
        }
    }
}
