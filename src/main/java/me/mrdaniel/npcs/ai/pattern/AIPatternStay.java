package me.mrdaniel.npcs.ai.pattern;

import com.google.common.collect.Lists;
import me.mrdaniel.npcs.ai.task.AITaskStay;
import me.mrdaniel.npcs.catalogtypes.aitype.AITypes;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.entity.ai.task.AITask;
import org.spongepowered.api.entity.living.Agent;
import org.spongepowered.api.entity.living.Creature;
import org.spongepowered.api.text.Text;

import java.util.List;

public class AIPatternStay extends AbstractAIPattern {

    public AIPatternStay(ConfigurationNode node) {
        this();
    }

    public AIPatternStay() {
        super(AITypes.STAY);
    }

    @Override
    public AITask<? extends Agent> createAITask(Creature owner, NPCType npcType) {
        return new AITaskStay(npcType.getMovementSpeed());
    }

    @Override
    public List<Text> getMenuLines() {
        return Lists.newArrayList();
    }

    @Override
    public void serializeValue(ConfigurationNode node) throws ObjectMappingException {

    }
}
