package me.mrdaniel.npcs.ai.pattern;

import com.google.common.collect.Lists;
import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.catalogtypes.aitype.AIType;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.utils.Position;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

import java.util.List;

public abstract class AbstractAIGuardPattern extends AbstractAIPattern {

    protected final List<Position> positions;

    public AbstractAIGuardPattern(AIType type, NPCType npcType) {
        super(type, npcType.getDefaultSpeed(), type.getDefaultChance());

        this.positions = Lists.newArrayList();
    }

    protected AbstractAIGuardPattern(AIType type, ConfigurationNode node) {
        super(type, node);

        this.positions = Lists.newArrayList();

        if (node.getNode("positions").hasListChildren()) {
            try {
                this.positions.addAll(node.getNode("positions").getList(TypeToken.of(Position.class)));
            } catch (ObjectMappingException exc) {
                NPCs.getInstance().getLogger().error("Failed to get ai position list from file: ", exc);
            }
        }
    }

    @Override
    public void serializeValue(ConfigurationNode node) throws ObjectMappingException {
        node.getNode("positions").setValue(new TypeToken<List<Position>>(){}, this.positions);
    }

    @Override
    public List<Text> getMenuLines() {
        List<Text> lines = Lists.newArrayList();

        for (int i = 0; i < this.positions.size(); i++) {
            lines.add(this.getPositionText(i, this.positions.get(i)));
        }

        lines.add(Text.of(" "));
        lines.add(Text.builder()
                .append(Text.of(TextColors.GREEN, "[Add Position]"))
                .onHover(TextActions.showText(Text.of(TextColors.GREEN, "Add Position")))
                .onClick(TextActions.runCommand("/npc ai position add"))
                .build()
        );

        return lines;
    }

    public List<Position> getPositions() {
        return this.positions;
    }

    private Text getPositionText(int index, Position pos) {
        Text remove = Text.builder()
                .append(Text.of(TextColors.RED, "[x]"))
                .onHover(TextActions.showText(Text.of(TextColors.RED, "Delete position")))
                .onClick(TextActions.runCommand("/npc ai position remove " + index))
                .build();

        Text up = Text.builder()
                .append(Text.of(TextColors.YELLOW, "[˄]"))
                .onHover(TextActions.showText(Text.of(TextColors.YELLOW, "Move Position Up")))
                .onClick(TextActions.runCommand("/npc ai position swap " + (index - 1) + " " + index))
                .build();

        Text down = Text.builder()
                .append(Text.of(TextColors.YELLOW, "[˅]"))
                .onHover(TextActions.showText(Text.of(TextColors.YELLOW, "Move Position Down")))
                .onClick(TextActions.runCommand("/npc ai position swap " + index + " " + (index + 1)))
                .build();

        return Text.of(
                remove, " ", up, " ", down, " ",
                TextColors.BLUE, index, ": ",
                TextColors.YELLOW, " x=", pos.getXRounded(), ", y=", pos.getYRounded(), "z=", pos.getZRounded());
    }
}
