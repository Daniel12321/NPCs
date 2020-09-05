package me.mrdaniel.npcs.gui.chat;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.pagination.PaginationList;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import javax.annotation.Nullable;
import java.util.List;

public abstract class AbstractChatMenu implements IChatMenu {

    protected Text title;
    protected Text padding;
    protected Text header;
    protected Text footer;
    protected boolean fill;
    protected int page;

    protected Player player;

    public AbstractChatMenu() {
        this.title = Text.of(" ");
        this.padding = Text.of(TextColors.YELLOW, "-");
        this.page = 1;
    }

    @Override
    public void open() {
        this.update();
    }

    @Override
    public void update() {
        this.player.sendMessage(Text.of(" "));

        PaginationList.builder()
                .title(this.title)
                .padding(this.padding)
                .header(this.header)
                .contents(this.fill ? this.fill(getContents()) : getContents())
                .footer(this.footer)
                .build()
                .sendTo(this.player, this.page);
    }

    @Override
    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public void setTitle(Text title) {
        this.title = title;
    }

    @Override
    public void setPadding(Text padding) {
        this.padding = padding;
    }

    @Override
    public void setHeader(@Nullable Text header) {
        this.header = header;
    }

    @Override
    public void setFooter(@Nullable Text footer) {
        this.footer = footer;
    }

    @Override
    public void setPage(int page) {
        this.page = page;
    }

    @Override
    public void setFilling(boolean fill) {
        this.fill = fill;
    }

    protected List<Text> fill(List<Text> contents) {
        int goal = 18 - (this.header == null ? 0 : 1) - (this.footer == null ? 0 : 1);

        while (contents.size() % goal != 0) {
            contents.add(Text.EMPTY);
        }

        return contents;
    }

    protected abstract List<Text> getContents();
}
