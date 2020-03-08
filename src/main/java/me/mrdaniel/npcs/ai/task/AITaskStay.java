package me.mrdaniel.npcs.ai.task;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyTypes;
import me.mrdaniel.npcs.mixin.interfaces.NPCAble;
import me.mrdaniel.npcs.utils.Position;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.ai.task.AITaskType;
import org.spongepowered.api.entity.ai.task.AITaskTypes;
import org.spongepowered.api.entity.living.Agent;

public class AITaskStay extends AbstractNPCAITask {

    private static final double START_DISTANCE_SQUARED = 1.1 * 1.1;

    /**
     * Creates a new {@link AITaskStay} with the provided
     * {@link AITaskType}.
     */
    public AITaskStay(double speed, int chance) {
        super(AITaskTypes.WANDER, speed, chance);

        ((EntityAIBase) (Object) this).setMutexBits(MUTEX_FLAG_MOVE);
    }

    @Override
    public boolean shouldUpdate() {
        Agent owner = this.getOwner().get();
        Position pos = ((NPCAble)owner).getData().getProperty(PropertyTypes.POSITION).get();

        if (owner.getLocation().getPosition().distanceSquared(pos.getX(), pos.getY(), pos.getZ()) < START_DISTANCE_SQUARED) {
            return false;
        }

        this.target = pos;
        return true;
    }

    @Override
    protected boolean moveToTarget(EntityLiving npc) {
        double distanceSquared = this.distanceToTargetSquared(npc);

        if (distanceSquared < ACCEPTABLE_DISTANCE_SQUARED) {
            return true;
        } else if (++this.tries > MAX_TRIES && distanceSquared > 5) {
            this.teleportToTarget(npc);
            return true;
        } else {
            npc.getNavigator().tryMoveToXYZ(this.target.getX(), this.target.getY(), this.target.getZ(), this.speed);
            return false;
        }
    }

    public static void register() {
        Sponge.getRegistry().registerAITaskType(NPCs.getInstance(), "stay", "Stay", AITaskStay.class);
    }
}
