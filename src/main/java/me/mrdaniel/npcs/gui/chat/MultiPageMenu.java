package me.mrdaniel.npcs.gui.chat;

import com.google.common.collect.Lists;
import org.spongepowered.api.text.Text;

import java.util.List;

public class MultiPageMenu extends AbstractChatMenu {

    private List<MenuPage> pages;

    public MultiPageMenu() {
        this(Lists.newArrayList());
    }

    public MultiPageMenu(List<MenuPage> pages) {
        this.pages = pages;
    }

    public void addPage(MenuPage page) {
        this.pages.add(page);
    }

    public void setPage(Class<? extends MenuPage> clazz) {
        for (int i = 0; i < this.pages.size(); i++) {
            if (this.pages.get(i).getClass().equals(clazz)) {
                this.setPage(i);
                return;
            }
        }
    }

    @Override
    protected List<Text> getContents() {
        List<Text> contents = Lists.newArrayList();

        for (MenuPage page : this.pages) {
            if (page.shouldShow()) {
                contents.addAll(super.fill(page.getContents()));
            }
        }

        return contents;
    }
}
