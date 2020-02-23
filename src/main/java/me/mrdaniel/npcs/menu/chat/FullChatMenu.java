package me.mrdaniel.npcs.menu.chat;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.service.pagination.PaginationList;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.List;

public interface FullChatMenu extends ChatMenu {

    @Override
    default void send(CommandSource src) {
        Text header = this.getHeader();
        List<Text> contents = this.getContents();
        Text footer = this.getFooter();

        // Ensures that every page is full
        int sizeGoal = 18 - (header == null ? 0 : 1) - (footer == null ? 0 : 1);
        while (contents.size() % sizeGoal != 0) {
            contents.add(Text.EMPTY);
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
