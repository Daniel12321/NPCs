package me.mrdaniel.npcs.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;

@Mixin(value = Entity.class, priority = 10)
public abstract class MixinEntity {

	@Inject(method = "isPushedByWater", at = @At("RETURN"), cancellable = true)
	public void onIsPushedByWater(final CallbackInfoReturnable<Boolean> cir) {
		if (this.isNPC() && cir.getReturnValue() != false) {
			cir.setReturnValue(false);
		}
    }

	@Inject(method = "getPushReaction", at = @At("RETURN"), cancellable = true)
	public void onGetPushReaction(final CallbackInfoReturnable<EnumPushReaction> cir) {
		if (this.isNPC() && cir.getReturnValue() != EnumPushReaction.IGNORE) {
			cir.setReturnValue(EnumPushReaction.IGNORE);
		}
    }

	@Inject(method = "isEntityInvulnerable", at = @At("RETURN"), cancellable = true)
	public void onIsEntityInvulnerable(final DamageSource source, final CallbackInfoReturnable<Boolean> cir) {
		if (this.isNPC() && cir.getReturnValue() != true) {
			cir.setReturnValue(true);
		}
	}

	private boolean isNPC() {
		if (!(this instanceof NPCAble)) { return false; }
		return ((NPCAble)this).getNPCFile() != null;
	}
}