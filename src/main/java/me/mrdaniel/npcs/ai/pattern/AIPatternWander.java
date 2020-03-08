package me.mrdaniel.npcs.ai.pattern;

import com.google.common.collect.Lists;
import me.mrdaniel.npcs.catalogtypes.aitype.AITypes;
import me.mrdaniel.npcs.utils.TextUtils;
import ninja.leaping.configurate.ConfigurationNode;
import org.spongepowered.api.entity.ai.task.AITask;
import org.spongepowered.api.entity.ai.task.builtin.creature.WanderAITask;
import org.spongepowered.api.entity.living.Agent;
import org.spongepowered.api.entity.living.Creature;
import org.spongepowered.api.text.Text;

import java.util.List;

public class AIPatternWander extends AbstractAIPattern {

    private int distance;

    public AIPatternWander(ConfigurationNode node) {
        super(AITypes.WANDER, node);

        this.distance = node.getNode("distance").getInt(5);
    }

    @Override
    public void serializeValue(ConfigurationNode node) {
        node.getNode("distance").setValue(this.distance);
    }

    @Override
    public AITask<? extends Agent> createAITask(Creature owner) {
        return WanderAITask.builder().speed(super.speed).executionChance(super.chance).build(owner);
    }

    @Override
    public List<Text> getMenuLines() {
        List<Text> lines = Lists.newArrayList();

        lines.add(TextUtils.getOptionsText("Wander Distance", "/npc ai wanderdistance <distance>", Integer.toString(this.distance)));

        return lines;
    }

    public int getDistance() {
        return this.distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
