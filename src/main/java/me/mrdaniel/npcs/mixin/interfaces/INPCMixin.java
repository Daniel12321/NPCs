package me.mrdaniel.npcs.mixin.interfaces;

public interface INPCMixin {

    default boolean isNPC() {
        return this instanceof NPCAble && ((NPCAble)this).getData() != null;
    }
}
