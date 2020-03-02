package me.mrdaniel.npcs.ai.pattern;

import com.google.common.collect.Lists;
import me.mrdaniel.npcs.catalogtypes.aitype.AIType;
import me.mrdaniel.npcs.utils.Position;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

import java.util.List;

public abstract class AbstractAIGuardPattern extends AbstractAIPattern {

    protected final List<Position> positions;

    protected AbstractAIGuardPattern(AIType type, List<Position> positions) {
        super(type);

        this.positions = positions;
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
                .onClick(TextActions.runCommand("/npc ai addposition")) // TODO: Add command
                .build()
        );

        return lines;
    }

    private Text getPositionText(int index, Position pos) {
        Text deleteButton = Text.builder()
                .append(Text.of(TextColors.RED, "[x]"))
                .onHover(TextActions.showText(Text.of(TextColors.RED, "Delete position")))
                .onClick(TextActions.runCommand("/npc ai deleteposition " + index)) // TODO: Add command
                .build();

        return Text.of(TextColors.BLUE, index, ": ", deleteButton, TextColors.YELLOW, "x=", pos.getX(), ", y=", pos.getY(), "z=", pos.getZ());
    }
}
