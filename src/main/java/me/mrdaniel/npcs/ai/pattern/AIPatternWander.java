package me.mrdaniel.npcs.ai.pattern;

import com.google.common.collect.Lists;
import me.mrdaniel.npcs.catalogtypes.aitype.AITypes;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.utils.TextUtils;
import org.spongepowered.api.entity.ai.task.AITask;
import org.spongepowered.api.entity.ai.task.builtin.creature.WanderAITask;
import org.spongepowered.api.entity.living.Agent;
import org.spongepowered.api.entity.living.Creature;
import org.spongepowered.api.text.Text;

import java.util.List;

public class AIPatternWander extends AbstractAIPattern {

    private int distance;

    public AIPatternWander(int distance) {
        super(AITypes.WANDER);

        this.distance = distance;
    }

    @Override
    public AITask<? extends Agent> createAITask(Creature owner, NPCType npcType) {
        return WanderAITask.builder().speed(npcType.getMovementSpeed()).executionChance(50).build(owner);
    }

    @Override
    public List<Text> getMenuLines() {
        List<Text> lines = Lists.newArrayList();

        // TODO: Add command
        lines.add(TextUtils.getOptionsText("Wander Distance", "/npc wanderdistance <distance>", Integer.toString(this.distance)));

        return lines;
    }

    public int getDistance() {
        return this.distance;
    }
}
