package me.mrdaniel.npcs.ai.task;

import me.mrdaniel.npcs.utils.Position;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.Path;
import org.spongepowered.api.entity.ai.task.AITask;
import org.spongepowered.api.entity.ai.task.AITaskType;
import org.spongepowered.api.entity.ai.task.AbstractAITask;
import org.spongepowered.api.entity.living.Agent;

import javax.annotation.Nullable;

public abstract class AbstractNPCAITask extends AbstractAITask<Agent> {

    protected static final int MUTEX_FLAG_MOVE = 1;
    protected static final double ACCEPTABLE_DISTANCE_SQUARED = 0.8 * 0.8;
    protected static final int MAX_TRIES = 10;

    protected final double speed;
    protected final int chance;

    @Nullable protected Position target;
    protected int tries;

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

        this.target = null;
        this.tries = 0;
    }

    @Override
    public void start() {
        this.moveToTarget((EntityLiving) this.getOwner().get());
    }

    @Override
    public void update() {

    }

    @Override
    public boolean continueUpdating() {
        if (this.target == null) {
            return false;
        }

        EntityLiving npc = ((EntityLiving) this.getOwner().get());
        Path path = npc.getNavigator().getPath();

        if (path == null) {
            return !this.moveToTarget(npc);
        } else if (path.isFinished()) {
            if (this.distanceToTargetSquared(npc) > ACCEPTABLE_DISTANCE_SQUARED) {
                return !this.moveToTarget(npc);
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    @Override
    public void reset() {
        this.target = null;
        this.tries = 0;

        ((EntityLiving)this.getOwner().get()).getNavigator().clearPathEntity();
    }

    @Override
    public boolean canRunConcurrentWith(AITask<Agent> other) {
        return (((EntityAIBase) (Object) this).getMutexBits() & ((EntityAIBase)other).getMutexBits()) == 0;
    }

    @Override
    public boolean canBeInterrupted() {
        return true;
    }

    /**
     * Tries to make the NPC move to its target
     * @param npc The NPC
     * @return Whether the NPC has reached its target
     */
    protected boolean moveToTarget(EntityLiving npc) {
        if (++this.tries > MAX_TRIES) {
            this.teleportToTarget(npc);
            return true;
        } else {
            npc.getNavigator().tryMoveToXYZ(this.target.getX(), this.target.getY(), this.target.getZ(), this.speed);
            return false;
        }
    }

    /**
     * Teleports the NPC to its target
     * @param npc The NPC
     */
    protected void teleportToTarget(EntityLiving npc) {
        npc.setPosition(this.target.getX(), this.target.getY(), this.target.getZ());
    }

    /**
     * Calculates the distance (squared) from the NPC to its target
     * @param npc The NPC
     * @return The distance between the NPC and its target
     */
    protected double distanceToTargetSquared(EntityLiving npc) {
        return npc.getPositionVector().squareDistanceTo(this.target.getX(), this.target.getY(), this.target.getZ());
    }
}
