package me.mrdaniel.npcs.ai.task;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.utils.Position;
import org.spongepowered.api.Sponge;

import java.util.List;
import java.util.Random;

public class AITaskGuardRandom extends AbstractNPCAIGuardTask {

    public AITaskGuardRandom(double speed, int chance, List<Position> targets) {
        super(speed, chance, targets);
    }

    @Override
    public boolean shouldUpdate() {
        Random r = this.getOwner().get().getRandom();
        if (this.targets.isEmpty() || r.nextInt(super.chance) != 0) {
            return false;
        }

        this.target = this.targets.get(r.nextInt(this.targets.size()));
        return true;
    }

    public static void register() {
        Sponge.getRegistry().registerAITaskType(NPCs.getInstance(), "guard-random", "Guard Random", AITaskGuardRandom.class);
    }
}