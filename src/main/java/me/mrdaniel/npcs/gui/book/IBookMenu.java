package me.mrdaniel.npcs.gui.book;

import me.mrdaniel.npcs.gui.IMenu;
import org.spongepowered.api.text.Text;

public interface IBookMenu extends IMenu {

    void setAuthor(Text author);
}
