package me.mrdaniel.npcs.menu.chat.npc;

import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyTypes;
import me.mrdaniel.npcs.io.INPCData;
import me.mrdaniel.npcs.menu.chat.FullChatMenu;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public abstract class NPCChatMenu implements FullChatMenu {

    protected final INPCData data;

    public NPCChatMenu(INPCData data) {
        this.data = data;
    }

    @Override
    public Text getFooter() {
        Text.Builder b = Text.builder().append(new PropertiesChatMenu(this.data).getPropertiesButton());

        NPCType type = this.data.getNPCProperty(PropertyTypes.TYPE).get();
        if (PropertyTypes.HELMET.isSupported(type)) {
            b.append(new EquipmentChatMenu(this.data).getEquipmentButton());
        }

        b.append(new ActionsChatMenu(this.data).getActionsButton());

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
