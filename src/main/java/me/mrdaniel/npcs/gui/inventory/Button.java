package me.mrdaniel.npcs.gui.inventory;

import me.mrdaniel.npcs.data.button.ButtonData;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.DyeColors;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.Slot;
import org.spongepowered.api.text.Text;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Button {

    private int index;
    private ItemStack itemStack;
    private BiConsumer<Player, Slot> leftAction;
    private BiConsumer<Player, Slot> rightAction;
    private Consumer<ItemStack> modifier;

    public Button() {
        this.index = -1;
        this.itemStack = null;
        this.leftAction = null;
        this.rightAction = null;
        this.modifier = null;
    }

    public int getIndex() {
        return this.index;
    }

    public Button setIndex(int index) {
        this.index = index;
        return this;
    }

    public ItemStack getItemStack() {
        return this.itemStack;
    }

    public Button setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
        return this;
    }

    public BiConsumer<Player, Slot> getLeftAction() {
        return this.leftAction;
    }

    public Button setLeftAction(BiConsumer<Player, Slot> leftAction) {
        this.leftAction = leftAction;
        return this;
    }

    public BiConsumer<Player, Slot> getRightAction() {
        return this.rightAction;
    }

    public Button setRightAction(BiConsumer<Player, Slot> rightAction) {
        this.rightAction = rightAction;
        return this;
    }

    public Consumer<ItemStack> getModifier() {
        return this.modifier;
    }

    public Button setModifier(Consumer<ItemStack> modifier) {
        this.modifier = modifier;
        return this;
    }

    public Button addButtonData() {
        this.itemStack.offer(new ButtonData(this.index));
        return this;
    }

    public static Button spacer(int index) {
        return spacer().setIndex(index);
    }

    public static Button spacer() {
        return new Button().setItemStack(ItemStack.builder()
                .itemType(ItemTypes.STAINED_GLASS_PANE)
                .quantity(1)
                .add(Keys.DYE_COLOR, DyeColors.BLACK)
                .add(Keys.DISPLAY_NAME, Text.of(" "))
                .build());
    }

//    public static Button back() {
//        return new Button()
//                .itemstack(ItemStack.builder()
//                        .itemType(ItemTypes.ARROW)
//                        .add(Keys.DISPLAY_NAME, Text.of(TextColors.RED, "Back"))
//                        .build());
//    }
//    public static MenuButton add(String title) {
//        return new MenuButton()
//                .itemstack(ItemStack.builder()
//                        .itemType(ItemTypes.DYE)
//                        .add(Keys.DYE_COLOR, DyeColors.LIME)
//                        .add(Keys.DISPLAY_NAME, Text.of(TextColors.GREEN, title))
//                        .build());
//    }
}
