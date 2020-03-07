package me.mrdaniel.npcs.ai.pattern;

import com.google.common.collect.Lists;
import me.mrdaniel.npcs.ai.task.AITaskStay;
import me.mrdaniel.npcs.catalogtypes.aitype.AITypes;
import ninja.leaping.configurate.ConfigurationNode;
import org.spongepowered.api.entity.ai.task.AITask;
import org.spongepowered.api.entity.living.Agent;
import org.spongepowered.api.entity.living.Creature;
import org.spongepowered.api.text.Text;

import java.util.List;

public class AIPatternStay extends AbstractAIPattern {

    public AIPatternStay(double speed) {
        super(AITypes.STAY, speed, AITypes.STAY.getDefaultChance());
    }

    public AIPatternStay(ConfigurationNode node) {
        super(AITypes.STAY, node);
    }

    @Override
    public void serializeValue(ConfigurationNode node) {

    }

    @Override
    public AITask<? extends Agent> createAITask(Creature owner) {
        return new AITaskStay(super.speed, super.chance);
    }

    @Override
    public List<Text> getMenuLines() {
        return Lists.newArrayList();
    }
}
