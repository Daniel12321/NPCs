package me.mrdaniel.npcs.menu.chatmenu;

import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyTypes;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.menu.ChatMenu;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public abstract class NPCChatMenu extends ChatMenu {

    protected final NPCAble npc;

    public NPCChatMenu(NPCAble npc) {
        this.npc = npc;
    }

    @Override
    protected Text getFooter() {
        Text.Builder b = Text.builder().append(new PropertiesChatMenu(npc).getPropertiesButton());

        if (PropertyTypes.HELMET.isSupported(npc)) {
            b.append(new EquipmentChatMenu(npc).getEquipmentButton());
        }

        b.append(new ActionsChatMenu(npc).getActionsButton());

        Text bar = Text.of(TextColors.YELLOW, getBar((58 - b.build().toPlain().length()) / 2));

        return Text.builder().append(bar, b.build(), bar).build();
    }

    private static String getBar(int times) {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < times; i++) { b.append('-'); }
        return b.toString();
    }

    protected Text getButton(Text button) {
        return Text.builder().append(Text.of(TextColors.YELLOW, "-=[ "), button, Text.of(TextColors.YELLOW, " ]=-")).build();
    }
}
