package me.mrdaniel.npcs.ai.task;

import me.mrdaniel.npcs.utils.Position;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import org.spongepowered.api.entity.ai.task.AITask;
import org.spongepowered.api.entity.ai.task.AITaskTypes;
import org.spongepowered.api.entity.ai.task.AbstractAITask;
import org.spongepowered.api.entity.living.Agent;

import javax.annotation.Nullable;
import java.util.List;

public abstract class AbstractAITaskGuard extends AbstractNPCAITask {

    private static final int MUTEX_FLAG_MOVE = 1;
    private static final double ACCEPTABLE_DISTANCE_SQUARED = 0.5 * 0.5;

    protected final List<Position> targets;
    @Nullable protected Position target;

    /**
     * Creates a new {@link AbstractAITask} with the provided
     * {@link AITask}.
     */
    public AbstractAITaskGuard(List<Position> targets, double speed, int chance) {
        super(AITaskTypes.WANDER, speed, chance);

        this.targets = targets;
        this.target = null;

        ((EntityAIBase) (Object) this).setMutexBits(MUTEX_FLAG_MOVE);
    }

    @Override
    public void start() {
        this.moveTo((EntityLiving)this.getOwner().get(), this.target);
    }

    @Override
    public void update() {
        EntityLiving el = (EntityLiving)this.getOwner().get();
        if (el.getNavigator().noPath()) {
            this.moveTo(el, this.target);
        }
    }

    @Override
    public boolean continueUpdating() {
        if (this.target == null) {
            return false;
        }
        return this.getOwner().get().getLocation().getPosition().distanceSquared(this.target.getX(), this.target.getY(), this.target.getZ()) > ACCEPTABLE_DISTANCE_SQUARED;
    }

    @Override
    public void reset() {
        this.target = null;
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
}
