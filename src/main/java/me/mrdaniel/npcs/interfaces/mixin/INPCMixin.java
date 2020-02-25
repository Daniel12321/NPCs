package me.mrdaniel.npcs.interfaces.mixin;

public interface INPCMixin {

    default boolean isNPC() {
        return this instanceof NPCAble && ((NPCAble)this).getData() != null;
    }
}
