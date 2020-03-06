package me.mrdaniel.npcs.ai.task;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyTypes;
import me.mrdaniel.npcs.mixin.interfaces.NPCAble;
import me.mrdaniel.npcs.utils.Position;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.ai.task.AITask;
import org.spongepowered.api.entity.ai.task.AITaskTypes;
import org.spongepowered.api.entity.living.Agent;

import javax.annotation.Nullable;

public class AITaskStay extends AbstractNPCAITask {

    private static final int MUTEX_FLAG_MOVE = 1;
    private static final double START_DISTANCE_SQUARED = 0.8 * 0.8;
    private static final double ACCEPTABLE_DISTANCE_SQUARED = 0.5 * 0.5;

    private final double movementSpeed;

    @Nullable private Position target;

    /**
     * Creates a new {@link AITaskStay} with the provided
     * {@link AITask}.
     */
    public AITaskStay(double movementSpeed) {
        super(AITaskTypes.WANDER);

        this.movementSpeed = movementSpeed;
        this.target = null;

        ((EntityAIBase) (Object) this).setMutexBits(MUTEX_FLAG_MOVE);
    }

    @Override
    public boolean shouldUpdate() {
        Agent owner = this.getOwner().get();
        Position pos = ((NPCAble)owner).getData().getProperty(PropertyTypes.POSITION).get();

        if (owner.getLocation().getPosition().distanceSquared(pos.getX(), pos.getY(), pos.getZ()) > START_DISTANCE_SQUARED) {
            this.target = pos;
        }

        return this.target != null;
    }

    @Override
    public void start() {
        this.moveTo((EntityLiving)this.getOwner().get(), this.target, this.movementSpeed);
    }

    @Override
    public void update() {
        EntityLiving el = (EntityLiving)this.getOwner().get();
        if (el.getNavigator().noPath()) {
            this.moveTo(el, this.target, this.movementSpeed);
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

    public static void register() {
        Sponge.getRegistry().registerAITaskType(NPCs.getInstance(), "stay", "Stay", AITaskStay.class);
    }
}
