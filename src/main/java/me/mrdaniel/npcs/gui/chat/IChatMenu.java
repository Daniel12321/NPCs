package me.mrdaniel.npcs.gui.chat;

import me.mrdaniel.npcs.gui.IMenu;
import org.spongepowered.api.text.Text;

import javax.annotation.Nullable;

public interface IChatMenu extends IMenu {

    void setPadding(Text padding);
    void setHeader(@Nullable Text header);
    void setFooter(@Nullable Text footer);
    void setPage(int page);
    void setFilling(boolean fill);
}
