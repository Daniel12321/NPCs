package me.mrdaniel.npcs.interfaces.mixin;

import net.minecraft.item.EnumDyeColor;

public interface IMixinEntityShulker {

    void setColor(EnumDyeColor color);
    void setPeek(boolean value);
}
