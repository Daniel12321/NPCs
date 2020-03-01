package me.mrdaniel.npcs.ai;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.utils.Position;
import org.spongepowered.api.Sponge;

import java.util.List;

public class AITaskGuardPatrol extends AbstractAITaskGuard {

    private static final int MOVE_CHANCE = 5; // 0.2f

    private int index;
    private int increment;

    public AITaskGuardPatrol(List<Position> targets, double movementSpeed) {
        super(targets, movementSpeed);

        this.index = 0;
        this.increment = 1;
    }

    @Override
    public boolean shouldUpdate() {
        if (this.targets.isEmpty() || this.getOwner().get().getRandom().nextInt(MOVE_CHANCE) != 0) {
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
