package me.mrdaniel.npcs.ai.pattern;

import me.mrdaniel.npcs.catalogtypes.aitype.AIType;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.entity.ai.task.AITask;
import org.spongepowered.api.entity.living.Agent;
import org.spongepowered.api.entity.living.Creature;
import org.spongepowered.api.text.Text;

import java.util.List;

public abstract class AbstractAIPattern {

    protected final AIType type;
    protected double speed;
    protected int chance;

    protected AbstractAIPattern(AIType type, double speed, int chance) {
        this.type = type;
        this.speed = speed;
        this.chance = chance;
    }

    protected AbstractAIPattern(AIType type, ConfigurationNode node) {
        this(type, node.getNode("speed").getDouble(1.0), node.getNode("chance").getInt(type.getDefaultChance()));
    }

    public void serialize(ConfigurationNode node) throws ObjectMappingException {
        node.getNode("speed").setValue(this.speed);
        node.getNode("chance").setValue(this.chance);
        this.serializeValue(node);
    }

    protected abstract void serializeValue(ConfigurationNode node) throws ObjectMappingException;
    public abstract AITask<? extends Agent> createAITask(Creature owner);
    public abstract List<Text> getMenuLines();

    public AIType getType() {
        return this.type;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getChance() {
        return chance;
    }

    public void setChance(int chance) {
        this.chance = chance;
    }
}
