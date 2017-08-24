package me.mrdaniel.npcs.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import me.mrdaniel.npcs.interfaces.mixin.IMixinEntityVillager;
import net.minecraft.entity.passive.EntityVillager;

@Mixin(value = EntityVillager.class, priority = 10)
public abstract class MixinEntityVillager implements IMixinEntityVillager {

	@Shadow private int careerId;

	@Override
	public void setCareerId(final int careerId) {
		this.careerId = careerId;
	}
}