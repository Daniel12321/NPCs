package me.mrdaniel.npcs.gui.chat;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.service.pagination.PaginationList;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.List;

public interface IFullChatMenu extends IChatMenu {

    @Override
    default void send(CommandSource src) {
        Text header = this.getHeader();
        List<Text> contents = this.getContents();
        Text footer = this.getFooter();

        int sizeGoal = 18 - (header == null ? 0 : 1) - (footer == null ? 0 : 1);
        while (contents.size() % sizeGoal != 0) {
            contents.add(Text.of(" "));
        }

        PaginationList.builder()
                .title(this.getTitle())
                .padding(Text.of(TextColors.YELLOW, "-"))
                .header(header)
                .contents(contents)
                .footer(footer)
                .build()
                .sendTo(src);
    }
}