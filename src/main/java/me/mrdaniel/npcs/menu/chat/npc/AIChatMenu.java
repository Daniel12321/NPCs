package me.mrdaniel.npcs.menu.chat.npc;

import com.google.common.collect.Lists;
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

        lines.add(TextUtils.getToggleText("Looking", "/npc looking", data.getProperty(PropertyTypes.LOOKING).orElse(false)));
        lines.add(Text.of(" "));
        lines.add(TextUtils.getOptionsText("AI Type", "/npc aitype <type>", data.getProperty(PropertyTypes.AITYPE).orElse(AITypes.STAY).getName()));



        return lines;
    }

    @Override
    protected Text getMenuButton() {
        return super.getButton(TextUtils.getButton("AI", this::send));
    }
}
