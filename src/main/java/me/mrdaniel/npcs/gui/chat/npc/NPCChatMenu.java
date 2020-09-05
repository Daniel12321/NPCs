package me.mrdaniel.npcs.gui.chat.npc;

import me.mrdaniel.npcs.gui.chat.MultiPageMenu;
import me.mrdaniel.npcs.io.INPCData;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class NPCChatMenu extends MultiPageMenu {

    private INPCData data;

    public NPCChatMenu(Player player, INPCData data) {
        this.data = data;

        this.addPage(new PropertiesPage(data));
        this.addPage(new ArmorPage(data));
        this.addPage(new AIPage(data));
        this.addPage(new ActionsPage(data));

        super.setPlayer(player);
        super.setTitle(Text.of(TextColors.YELLOW, "[ ", TextColors.RED, "NPC Menu", TextColors.YELLOW, " ]"));
    }

    public INPCData getData() {
        return this.data;
    }
}
