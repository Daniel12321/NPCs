package me.mrdaniel.npcs.gui.book;

import me.mrdaniel.npcs.gui.IBookMenu;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.BookView;
import org.spongepowered.api.text.Text;

import java.util.List;

public abstract class AbstractBookMenu implements IBookMenu {

    private Text title;
    private Text author;

    protected Player player;
    protected BookView book;

    @Override
    public void open() {
        this.book = BookView.builder()
                .title(this.title)
                .author(this.author)
                .addPages(this.getPages())
                .build();

        this.player.sendBookView(this.book);
    }

    @Override
    public void update() {
        this.open();
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
    public void setAuthor(Text author) {
        this.author = author;
    }

    protected abstract List<Text> getPages();
}
