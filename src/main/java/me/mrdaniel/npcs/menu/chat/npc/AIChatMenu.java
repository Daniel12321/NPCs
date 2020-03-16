package me.mrdaniel.npcs.menu.chat.npc;

import com.google.common.collect.Lists;
import me.mrdaniel.npcs.ai.pattern.AIPatternStay;
import me.mrdaniel.npcs.ai.pattern.AbstractAIPattern;
import me.mrdaniel.npcs.catalogtypes.aitype.AITypes;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyTypes;
import me.mrdaniel.npcs.io.INPCData;
import me.mrdaniel.npcs.utils.TextUtils;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import javax.annotation.Nullable;
import java.util.List;

public class AIChatMenu extends NPCChatMenu {

    public AIChatMenu(INPCData data) {
        super(data);
    }

    @Override
    public Text getTitle() {
        return Text.of(TextColors.YELLOW, "[ ", TextColors.RED, "NPC AI", TextColors.YELLOW, " ]");
    }

    @Nullable
    @Override
    public Text getHeader() {
        return null;
    }

    @Override
    public List<Text> getContents() {
        List<Text> lines = Lists.newArrayList();
        AbstractAIPattern aiPattern = data.getProperty(PropertyTypes.AI_PATTERN).orElse(new AIPatternStay(data.getProperty(PropertyTypes.TYPE).get()));

        lines.add(TextUtils.getToggleText("Looking", "/npc ai looking", data.getProperty(PropertyTypes.LOOKING).orElse(false)));
        lines.add(Text.of(" "));
        lines.add(TextUtils.getOptionsText("AI Type", "/npc ai type <type>", aiPattern.getType().getName()));
        lines.add(TextUtils.getOptionsText("Movement Speed", "/npc ai speed <speed>", Double.toString(aiPattern.getSpeed())));
        if (aiPattern.getType() != AITypes.STAY) {
            lines.add(TextUtils.getOptionsText("Walk Chance", "/npc ai chance <chance>", Integer.toString(aiPattern.getChance())));
            lines.add(Text.of(TextColors.RED, "Walk Chance is the chance of the NPC starting to walk per tick. 0=100%, 10=10%, 100=1%, 1000=0.1%"));
        }
        lines.add(Text.of(" "));
        lines.addAll(aiPattern.getMenuLines());

        return lines;
    }

    @Override
    protected Text getMenuButton() {
        return super.getButton(TextUtils.getButton("AI", this::send));
    }
}
