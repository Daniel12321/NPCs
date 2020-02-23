package me.mrdaniel.npcs.menu;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.service.pagination.PaginationList;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import javax.annotation.Nullable;
import java.util.List;

public abstract class ChatMenu {

    public void send(CommandSource src) {
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

    protected abstract Text getTitle();
    @Nullable protected abstract Text getHeader();
    protected abstract List<Text> getContents();
    @Nullable protected abstract Text getFooter();
}
