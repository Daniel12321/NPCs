package me.mrdaniel.npcs.gui;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

public interface IMenu {

    void open();
    void update();

    void setPlayer(Player player);
    void setTitle(Text title);
}
