package me.mrdaniel.npcs.gui.chat.info;

import com.google.common.collect.Lists;
import me.mrdaniel.npcs.gui.chat.IChatMenu;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import javax.annotation.Nullable;
import java.util.List;

public class InfoMenu implements IChatMenu {

    @Override
    public Text getTitle() {
        return Text.of(Text.of(TextColors.YELLOW, "[ ", TextColors.RED, "NPC Info", TextColors.YELLOW, " ]"));
    }

    @Nullable
    @Override
    public Text getHeader() {
        return null;
    }

    @Override
    public List<Text> getContents() {
        List<Text> lines = Lists.newArrayList();

        lines.add(Text.EMPTY);
        lines.add(Text.of(TextColors.YELLOW, "---------------=====[ ", TextColors.RED, "NPC Info", TextColors.YELLOW, " ]=====---------------"));
        lines.add(Text.of(TextColors.AQUA, "You currently have not selected an NPC."));
        lines.add(Text.of(TextColors.AQUA, "You can select an NPC by shift right clicking it."));
        lines.add(Text.of(TextColors.AQUA, "You can see a list deserialize NPC's by running: ", TextColors.YELLOW, "/npc list"));
        lines.add(Text.of(TextColors.AQUA, "You can create an NPC by running: ", TextColors.YELLOW, "/npc create ", TextColors.GOLD, "[entitytype]"));
        lines.add(Text.of(TextColors.YELLOW, "--------------------------------------------------"));

        return lines;
    }

    @Nullable
    @Override
    public Text getFooter() {
        return null;
    }
}
