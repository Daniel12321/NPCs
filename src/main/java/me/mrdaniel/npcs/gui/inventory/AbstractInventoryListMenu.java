package me.mrdaniel.npcs.gui.inventory;

import com.google.common.collect.Lists;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.List;

public abstract class AbstractInventoryListMenu extends AbstractInventoryMenu {

    private int page;
    private int pages;

    public AbstractInventoryListMenu() {
        this.page = 1;
        this.pages = 1;
    }

    public void changePage(int page) {
        if (page > 0 && page <= this.pages) {
            this.page = page;
            this.update();
        }
    }

    @Override
    protected List<Button> getButtons() {
        List<Button> buttons = Lists.newArrayList();
        List<Button> listButtons = this.getListButtons();
        List<Button> menuButtons = this.getMenuButtons();

        this.pages = listButtons.size() / 45 + 1;

        // First 5 Rows - List Items
        int start = (this.page - 1) * 45;
        int end = this.page * 45;

        for (int i = start; i < end && i < listButtons.size(); i++) {
            buttons.add(listButtons.get(i).index(i % 45));
        }

        // Bottom Row - Buttons
        // Slot 1 - Previous Page
        if (this.page >= 2) {
            buttons.add(new Button()
                    .index(45)
                    .itemstack(ItemStack.builder().itemType(ItemTypes.PAPER).quantity(1).add(Keys.DISPLAY_NAME, Text.of(TextColors.GOLD, "Previous page")).build())
                    .leftAction((p, s) -> this.changePage(this.page - 1)));
        } else {
            buttons.add(Button.spacer(45));
        }

        // Slot 2 - Spacer
        buttons.add(Button.spacer(46));

        // Slots 3-7 - Menu Buttons
        for (int i = 0; i < 5; i++) {
            buttons.add(menuButtons.size() > i ? menuButtons.get(i).index(i + 47) : Button.spacer(i + 47));
        }

        // Slot 8 - Spacer
        buttons.add(Button.spacer(52));

        // Slot 9 - Next Page
        buttons.add(this.page >= this.pages ? Button.spacer(53) : new Button()
                .itemstack(ItemStack.builder().itemType(ItemTypes.PAPER).quantity(1).add(Keys.DISPLAY_NAME, Text.of(TextColors.GOLD, "Next page")).build())
                .index(53)
                .leftAction((p, s) -> this.changePage(this.page + 1))
        );

        return buttons;
    }

    protected abstract List<Button> getListButtons();
    protected abstract List<Button> getMenuButtons();
}

/*
    00 01 02 03 04 05 06 07 08
    09 10 11 12 13 14 15 16 17
    18 19 20 21 22 23 24 25 26
    27 28 29 30 31 32 33 34 35
    36 37 38 39 40 41 42 43 44

    45 46 47 48 49 50 51 52 53
*/