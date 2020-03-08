package me.mrdaniel.npcs.ai.task;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.utils.Position;
import org.spongepowered.api.Sponge;

import java.util.List;

public class AITaskGuardPatrol extends AbstractNPCAIGuardTask {

    private int index;
    private int increment;

    public AITaskGuardPatrol(List<Position> targets, double speed, int chance) {
        super(targets, speed, chance);

        this.index = 0;
        this.increment = 1;
    }

    @Override
    public boolean shouldUpdate() {
        if (this.targets.isEmpty() || this.getOwner().get().getRandom().nextInt(super.chance) != 0) {
            return false;
        }

        int nextIndex = index + increment;
        if (nextIndex < 0 || nextIndex >= targets.size()) {
            increment *= -1;
            nextIndex = index + increment;
        }

        this.index = nextIndex;
        this.target = this.targets.get(this.index);

        return true;
    }

    public static void register() {
        Sponge.getRegistry().registerAITaskType(NPCs.getInstance(), "guard-patrol", "Guard Patrol", AITaskGuardPatrol.class);
    }
}
