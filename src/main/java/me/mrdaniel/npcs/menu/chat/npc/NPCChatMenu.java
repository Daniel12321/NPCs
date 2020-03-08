package me.mrdaniel.npcs.menu.chat.npc;

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
        Text bar = Text.of(
                new PropertiesChatMenu(this.data).getMenuButton(),
                new AIChatMenu(this.data).getMenuButton(),
                new ActionsChatMenu(this.data).getMenuButton());

        Text spacing = Text.of(TextColors.YELLOW, getBar((58 - bar.toPlain().length()) / 2));

        return Text.builder().append(spacing, bar, spacing).build();
    }

    protected Text getButton(Text button) {
        return Text.builder().append(Text.of(TextColors.YELLOW, "-=[ "), button, Text.of(TextColors.YELLOW, " ]=-")).build();
    }

    private String getBar(int times) {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < times; i++) { b.append('-'); }
        return b.toString();
    }

    protected abstract Text getMenuButton();
}
