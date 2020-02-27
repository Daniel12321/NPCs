package me.mrdaniel.npcs.mixin.interfaces;

import net.minecraft.item.EnumDyeColor;

public interface IMixinEntityShulker {

    void setColor(EnumDyeColor color);
    void setPeek(boolean value);
}
