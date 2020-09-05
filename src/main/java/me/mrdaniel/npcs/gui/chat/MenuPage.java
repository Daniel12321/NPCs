package me.mrdaniel.npcs.gui.chat;

import org.spongepowered.api.text.Text;

import java.util.List;

public interface MenuPage {

    boolean shouldShow();
    List<Text> getContents();
}
