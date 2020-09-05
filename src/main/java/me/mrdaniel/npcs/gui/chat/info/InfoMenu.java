package me.mrdaniel.npcs.gui.chat.info;

import com.google.common.collect.Lists;
import me.mrdaniel.npcs.gui.chat.AbstractChatMenu;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.List;

public class InfoMenu extends AbstractChatMenu {

    public InfoMenu(Player player) {
        super.setPlayer(player);
        super.setTitle(Text.of(Text.of(TextColors.YELLOW, "[ ", TextColors.RED, "NPC Info", TextColors.YELLOW, " ]")));
    }

    @Override
    protected List<Text> getContents() {
        return Lists.newArrayList(
                Text.of(TextColors.AQUA, "You currently have not selected an NPC."),
                Text.of(TextColors.AQUA, "You can select an NPC by shift right clicking it."),
                Text.of(TextColors.AQUA, "You can see a list deserialize NPC's by running: ", TextColors.YELLOW, "/npc list"),
                Text.of(TextColors.AQUA, "You can create an NPC by running: ", TextColors.YELLOW, "/npc create ", TextColors.GOLD, "[entitytype]")
        );
    }
}
