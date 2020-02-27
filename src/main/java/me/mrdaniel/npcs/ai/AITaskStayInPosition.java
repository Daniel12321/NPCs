package me.mrdaniel.npcs.ai;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.mixin.interfaces.NPCAble;
import me.mrdaniel.npcs.utils.Position;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.PathNavigate;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.ai.task.AITask;
import org.spongepowered.api.entity.ai.task.AITaskTypes;
import org.spongepowered.api.entity.ai.task.AbstractAITask;
import org.spongepowered.api.entity.living.Agent;

import javax.annotation.Nullable;

public class AITaskStayInPosition extends AbstractAITask<Agent> {

    private static final int MUTEX_FLAG_MOVE = 1;
    private static final double MOVEMENT_SPEED = 1.5;
    private static final double START_DISTANCE_SQUARED = 1;
    private static final double ACCEPTABLE_DISTANCE_SQUARED = 0.5 * 0.5;

    @Nullable private Position target;

    /**
     * Creates a new {@link AITaskStayInPosition} with the provided
     * {@link AITask}.
     */
    public AITaskStayInPosition() {
        super(AITaskTypes.WANDER);

        ((EntityAIBase) (Object) this).setMutexBits(MUTEX_FLAG_MOVE);
    }

    @Override
    public boolean shouldUpdate() {
        Agent owner = this.getOwner().get();
        Position pos = ((NPCAble)owner).getData().getPosition();

        if (owner.getLocation().getPosition().distanceSquared(pos.getX(), pos.getY(), pos.getZ()) > START_DISTANCE_SQUARED) {
            this.target = pos;
        }

        return this.target != null;
    }

    @Override
    public void start() {
        System.out.println("Start");
        System.out.println("Moving to: " + this.target.getX() + " " + this.target.getY() + " " + this.target.getZ());

        boolean move = ((EntityLiving)this.getOwner().get()).getNavigator().tryMoveToXYZ(this.target.getX(), this.target.getY(), this.target.getZ(), MOVEMENT_SPEED);
        // TODO: Else teleport

        System.out.println("Move succesfull: " + move);
    }

    @Override
    public void update() {
        PathNavigate navigate = ((EntityLiving)this.getOwner().get()).getNavigator();
        if (navigate.noPath()) {
            navigate.tryMoveToXYZ(this.target.getX(), this.target.getY(), this.target.getZ(), MOVEMENT_SPEED);
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
        System.out.println("Reset");

        ((EntityLiving)this.getOwner().get()).getNavigator().clearPathEntity();
        this.target = null;
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
        Sponge.getRegistry().registerAITaskType(NPCs.getInstance(), "stay-in-position", "Stay In Position", AITaskStayInPosition.class);
    }
}
