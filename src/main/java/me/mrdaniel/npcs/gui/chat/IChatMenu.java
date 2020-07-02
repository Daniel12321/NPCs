package me.mrdaniel.npcs.gui.chat;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.service.pagination.PaginationList;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import javax.annotation.Nullable;
import java.util.List;

public interface ChatMenu {

    default void send(CommandSource src) {
        PaginationList.builder()
                .title(this.getTitle())
                .padding(Text.of(TextColors.YELLOW, "-"))
                .header(this.getHeader())
                .contents(this.getContents())
                .footer(this.getFooter())
                .build()
                .sendTo(src);
    }

    Text getTitle();
    @Nullable Text getHeader();
    List<Text> getContents();
    @Nullable Text getFooter();
}
