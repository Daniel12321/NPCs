package me.mrdaniel.npcs.ai.task;

import me.mrdaniel.npcs.utils.Position;
import net.minecraft.entity.EntityLiving;
import org.spongepowered.api.entity.ai.task.AITaskType;
import org.spongepowered.api.entity.ai.task.AbstractAITask;
import org.spongepowered.api.entity.living.Agent;

public abstract class AbstractNPCAITask extends AbstractAITask<Agent> {

    protected static final int MUTEX_FLAG_MOVE = 1;
    protected static final double ACCEPTABLE_DISTANCE_SQUARED = 0.3 * 0.3;

    protected final double speed;
    protected final int chance;

    /**
     * Creates a new {@link AbstractNPCAITask} with the provided
     * {@link AITaskType}.
     *
     * @param type The type
     */
    public AbstractNPCAITask(AITaskType type, double speed, int chance) {
        super(type);

        this.speed = speed;
        this.chance = chance;
    }

    /**
     * Tries to make the NPC move to a position
     * @param npc The NPC
     * @param target The position to move to
     */
    protected void moveTo(Agent npc, Position target) {
        double distanceSquared = this.distanceSquared(npc, target);

        if (distanceSquared < ACCEPTABLE_DISTANCE_SQUARED) {
            return;
        }

        EntityLiving el = (EntityLiving) npc;
        if (el.getNavigator().noPath() &&
                !el.getNavigator().tryMoveToXYZ(target.getX(), target.getY(), target.getZ(), this.speed)
                && distanceSquared > 1.0) {
            el.setPosition(target.getX(), target.getY(), target.getZ());
        }
    }

    protected double distanceSquared(Agent owner, Position target) {
        return owner.getLocation().getPosition().distanceSquared(target.getX(), target.getY(), target.getZ());
    }
}
